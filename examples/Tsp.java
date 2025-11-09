import jscip.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tsp {

    private static final double TWO_PI = 2 * StrictMath.PI;
    private static final long DEFAULT_SEED = 4L;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--help")) {
            System.out.println("Usage: Tsp [nodeCount] [outputFile.png] [seed]");
            return;
        }
        int nodeCount;
        if (args.length >= 1) {
            nodeCount = Integer.parseInt(args[0]);
        } else {
            nodeCount = 10;
        }
        String outputImage;
        if (args.length >= 2) {
            outputImage = args[1];
        } else {
            outputImage = "tsp-solution-%d.ign.png";
        }
        long seed;
        if (args.length >= 3) {
            seed = args[2].hashCode();
        } else {
            seed = DEFAULT_SEED;
        }
        Random rng = new Random(seed);

        // first create some random nodes in a 400 x 400 rectangle
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i != nodeCount; ++i) {
            nodes.add(
                    new Node(
                            i,
                            rng.nextInt(400),
                            rng.nextInt(400)
                    )
            );
        }

        System.loadLibrary("jscip");
        Scip scip = new Scip();
        scip.create("tsp");
        // contains the list of edges connected to the ith node
        Map<Integer, List<Edge>> edgesGroupedByNodes = new HashMap<>();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i != nodes.size(); ++i) {
            Node left = nodes.get(i);
            for (int j = 0; j != i; ++j) {
                Node right = nodes.get(j);
                Variable edgeVar = scip.createVar(
                        "edge_" + i + "_" + j,
                        0.0,
                        1.0,
                        left.dist(right),
                        SCIP_Vartype.SCIP_VARTYPE_BINARY
                );
                Edge edge = new Edge(left.idx, right.idx, edgeVar);
                edgesGroupedByNodes.computeIfAbsent(i, ign -> new ArrayList<>()).add(edge);
                edgesGroupedByNodes.computeIfAbsent(j, ign -> new ArrayList<>()).add(edge);
                edges.add(edge);
            }
        }
        // each node must be connected to exactly 2 edges
        for (int i = 0; i != edgesGroupedByNodes.size(); ++i) {
            List<Edge> edgesForNode = edgesGroupedByNodes.get(i);
            double[] values = new double[edgesForNode.size()];
            Arrays.fill(values, 1.0);
            Constraint constraint = scip.createConsLinear(
                    "maxEdges_" + i,
                    edgesForNode.stream()
                            .map(e -> e.variable)
                            .toArray(Variable[]::new),
                    values,
                    2.0,
                    2.0
            );
            scip.addCons(constraint);
            scip.releaseCons(constraint);
        }
        TourFinder tourFinder = new TourFinder();
        SolutionRecorder solutionRecorder = new SolutionRecorder();
        scip.addEventHandler(new SubtourConstraintGenerator(tourFinder, edges, solutionRecorder));
        scip.setRealParam("limits/time", 3600.0);
        scip.setRealParam("limits/memory", 10000.0);
        scip.setLongintParam("limits/totalnodes", 1000);
        scip.solve();
        SCIP_Status status = scip.getStatus();
        System.out.println("Scip status: " + status);

        List<List<List<DirectionalEdgeKey>>> solutions = solutionRecorder.encounteredSolutions;
        String formatName = outputImage.substring(outputImage.lastIndexOf('.') + 1);
        if (outputImage.contains("%d")) {
            for (int i = 0; i != solutions.size(); ++i) {
                printImage(nodes, solutions.get(i), String.format(outputImage, i), formatName);
            }
        } else {
            // only print final solution
            printImage(nodes, solutions.get(solutions.size() - 1), outputImage, formatName);
        }
        scip.writeTransProblem("tsp.ign.lp");
        edges.forEach(e -> scip.releaseVar(e.variable));
        scip.free();
    }

    private static void printImage(
            List<Node> nodes,
            List<List<DirectionalEdgeKey>> tours,
            String outputImage,
            String formatName
    ) {
        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 500, 500);
        graphics.setColor(Color.WHITE);
        for (List<DirectionalEdgeKey> tour : tours) {
            for (DirectionalEdgeKey edge : tour) {
                Node left = nodes.get(edge.incoming);
                Node right = nodes.get(edge.outgoing);
                graphics.drawLine(left.x + 50, left.y + 50, right.x + 50, right.y + 50);
            }
        }
        graphics.setColor(Color.RED);
        for (Node node : nodes) {
            graphics.fillArc(node.x - 2 + 50, node.y - 2 + 50, 4, 4, 0, 360);
            graphics.drawString(String.valueOf(node.idx), node.x + 60, node.y + 50);
        }
        try {
            ImageIO.write(image, formatName, new File(outputImage));
        } catch (IOException e) {
            System.out.println("Could not write image: " + e.getMessage());
        }
    }

    private static class Node {
        private final int idx;
        private final int x;
        private final int y;

        private Node(int idx, int x, int y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }

        private double dist(Node other) {
            return StrictMath.sqrt((other.x - x) * (other.x - x) + (other.y - y) * (other.y - y));
        }

        public String toString() {
            return "Node: " + idx + ": (" + x + ";" + y + ")";
        }
    }

    private static class Edge {
        private final int left;
        private final int right;
        private final Variable variable;

        private Edge(int left, int right, Variable variable) {
            this.left = left;
            this.right = right;
            this.variable = variable;
        }

        public String toString() {
            return "Edge: " + left + ";" + right;
        }

    }

    private static class SubtourConstraintGenerator extends EventHandler {

        private final TourFinder tourFinder;
        private final List<Edge> edges;
        private final SolutionRecorder solutionRecorder;

        public SubtourConstraintGenerator(
                TourFinder tourFinder,
                List<Edge> edges,
                SolutionRecorder solutionRecorder
        ) {
            super(
                    "SubtourExclusion",
                    "Creates constraints to exclude subtours",
                    EventType.BESTSOLFOUND
            );
            this.tourFinder = tourFinder;
            this.edges = edges;
            this.solutionRecorder = solutionRecorder;
        }

        private void log(String message) {
            System.out.println("[ExampleLog]: " + message);
        }

        @Override
        protected SCIP_Retcode scipFree(Scip scip) {
            log("scipFree, stage: " + scip.getStage());
            return super.scipFree(scip);
        }

        @Override
        protected SCIP_Retcode scipInit(Scip scip) {
            log("scipInit, stage: " + scip.getStage());
            return super.scipInit(scip);
        }

        @Override
        protected SCIP_Retcode scipExit(Scip scip) {
            log("scipExit, stage: " + scip.getStage());
            return super.scipExit(scip);
        }

        @Override
        protected SCIP_Retcode scipInitsol(Scip scip) {
            log("scipInitSol, stage: " + scip.getStage());
            return super.scipInitsol(scip);
        }

        @Override
        protected SCIP_Retcode scipExitsol(Scip scip) {
            log("scipExitSol, stage: " + scip.getStage());
            return super.scipExitsol(scip);
        }

        @Override
        protected SCIP_Retcode scipDelete(Scip scip) {
            log("scipDelete, stage: " + scip.getStage());
            return super.scipDelete(scip);
        }

        @Override
        protected SCIP_Retcode scipExec(Scip scip) {
            log("scipExec, stage: " + scip.getStage());
            Solution solution = scip.getBestSol();
            List<List<DirectionalEdge>> subtours = tourFinder.findTours(edges, scip, solution);
            if (subtours.size() > 1) { // else we're done
                log("Adding " + subtours.size() + " subtour constraints.");
                // creating subtour constraints
                for (List<DirectionalEdge> subtour : subtours) {
                    Constraint subtourConstraint = scip.createConsLinear(
                            "subtourElimination_" + subtour.stream()
                                    .map(e -> String.valueOf(e.incoming))
                                    .collect(Collectors.joining("_")),
                            subtour.stream()
                                    .map(e -> e.edge.variable)
                                    .toArray(Variable[]::new),
                            subtour.stream()
                                    .mapToDouble(ign -> 1.0)
                                    .toArray(),
                            - scip.infinity(),
                            subtour.size() - 1.0
                    );
                    scip.addCons(subtourConstraint);
                    scip.releaseCons(subtourConstraint);
                }
            }
            solutionRecorder.encounteredSolutions.add(
                    subtours.stream()
                            .map(
                                    l -> l.stream()
                                            .map(DirectionalEdge::toKey)
                                            .collect(Collectors.toList())
                            )
                            .collect(Collectors.toList())
            );
            return super.scipExec(scip);
        }
    }

    private static class DirectionalEdge {
        private final Edge edge;
        private final int incoming;
        private final int outgoing;

        private DirectionalEdge(Edge edge, int incoming, int outgoing) {
            this.edge = edge;
            this.incoming = incoming;
            this.outgoing = outgoing;
        }

        private DirectionalEdgeKey toKey() {
            return new DirectionalEdgeKey(incoming, outgoing);
        }

        public String toString() {
            return "DirectionalEdge: " + incoming + " -> " + outgoing;
        }
    }

    /**
     * Like Directional Edge, but without the contained scip objects.
     */
    private static class DirectionalEdgeKey {
        private final int incoming;
        private final int outgoing;

        private DirectionalEdgeKey(int incoming, int outgoing) {
            this.incoming = incoming;
            this.outgoing = outgoing;
        }
    }

    private static class DirectionalEdgeIndex {

        public static DirectionalEdgeIndex ofActiveEdges(List<Edge> activeEdges) {
            Map<Integer, List<DirectionalEdge>> edgeResultIndex = activeEdges.stream()
                    .flatMap(
                            e -> Stream.of(
                                    new DirectionalEdge(e, e.left, e.right),
                                    new DirectionalEdge(e, e.right, e.left)
                            )
                    )
                    .collect(Collectors.groupingBy(e -> e.incoming));
            return new DirectionalEdgeIndex(edgeResultIndex);
        }

        private DirectionalEdgeIndex(Map<Integer, List<DirectionalEdge>> edgesByIncomingNode) {
            this.edgesByIncomingNode = edgesByIncomingNode;
        }

        private final Map<Integer, List<DirectionalEdge>> edgesByIncomingNode;

        private DirectionalEdge findNextEdge(DirectionalEdge edge) {
            return edgesByIncomingNode.get(edge.outgoing).stream()
                    .filter(er -> er.outgoing != edge.incoming)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Cannot find other connecting edge."));
        }
    }

    private static class TourFinder {
        private List<List<DirectionalEdge>> findTours(List<Edge> edges, Scip scip, Solution solution) {
            List<Edge> activeEdges = edges.stream()
                    .filter(e -> scip.getSolVal(solution, e.variable) > 0.5)
                    .collect(Collectors.toList());
            DirectionalEdgeIndex directionalEdgeIndex = DirectionalEdgeIndex.ofActiveEdges(activeEdges);
            List<List<DirectionalEdge>> tours = new ArrayList<>();
            while(!activeEdges.isEmpty()) { // If there are no more active edges to process we've found all subtours
                List<DirectionalEdge> currentTour = new ArrayList<>();
                Edge currentEdge = activeEdges.remove(activeEdges.size() - 1);
                int firstNode = currentEdge.left;
                int currentNode = currentEdge.right;
                DirectionalEdge currentDirectionalEdge = new DirectionalEdge(
                        currentEdge,
                        currentEdge.left,
                        currentEdge.right
                );
                while(currentNode != firstNode) { // otherwise we looped around and the subtour is complete
                    currentTour.add(currentDirectionalEdge);
                    currentDirectionalEdge = directionalEdgeIndex.findNextEdge(currentDirectionalEdge);
                    currentNode = currentDirectionalEdge.outgoing;
                    currentEdge = currentDirectionalEdge.edge;
                    activeEdges.remove(currentEdge);
                }
                currentTour.add(currentDirectionalEdge);
                tours.add(currentTour);
            }
            return tours;
        }
    }

    private static class SolutionRecorder {
        private final List<List<List<DirectionalEdgeKey>>> encounteredSolutions = new ArrayList<>();
    }

}
