import jscip.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Tsp {

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
            outputImage = "tsp-iteration-%d.ign.png";
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
                Edge edge = new Edge(EdgeKey.ofNodes(left.idx, right.idx), edgeVar);
                edgesGroupedByNodes.computeIfAbsent(i, ign -> new ArrayList<>()).add(edge);
                edgesGroupedByNodes.computeIfAbsent(j, ign -> new ArrayList<>()).add(edge);
                edges.add(edge);
            }
        }
        // each node must be connected to exactly 2 edges
        List<Constraint> constraints = new ArrayList<>();
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
            constraints.add(constraint);
        }
        TourFinder tourFinder = new TourFinder();
        SolutionRecorder solutionRecorder = new SolutionRecorder();
        scip.addConstraintHandler(new SubtourConstraintGenerator(nodeCount, tourFinder, edges, solutionRecorder));
        scip.setRealParam("limits/time", 3600.0);
        scip.setRealParam("limits/memory", 10000.0);
        scip.setLongintParam("limits/totalnodes", 1000);
        scip.solve();
        SCIP_Status status = scip.getStatus();
        System.out.println("Scip status: " + status);
        List<List<TourPart>> bestSolution = tourFinder.findTours(edges, scip, scip.getBestSol());
        if (bestSolution != null) {
            solutionRecorder.encounteredSolutions.add(
                    bestSolution
                            .stream()
                            .map(
                                    tour -> tour.stream()
                                            .map(p -> p.node)
                                            .collect(Collectors.toList())
                            )
                            .collect(Collectors.toList())
            );
        } else {
            System.err.println("!!! No best solution.");
        }

        List<List<List<Integer>>> solutions = solutionRecorder.encounteredSolutions;
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
        constraints.forEach(scip::releaseCons);
        edges.forEach(e -> scip.releaseVar(e.variable));
        scip.free();
    }

    private static void printImage(
            List<Node> nodes,
            List<List<Integer>> tours,
            String outputImage,
            String formatName
    ) {
        BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, 500, 500);
        graphics.setColor(Color.WHITE);
        for (List<Integer> tour : tours) {
            Node previousNode = null;
            Integer firstNodeIndex = tour.isEmpty() ? null : tour.get(0);
            for (Integer nodeIndex : tour) {
                Node currentNode = nodes.get(nodeIndex);
                if (previousNode != null) {
                    graphics.drawLine(
                            currentNode.x + 50, currentNode.y + 50,
                            previousNode.x + 50, previousNode.y + 50
                    );
                }
                previousNode = currentNode;
            }
            if (firstNodeIndex != null && previousNode != null) {
                Node firstNode = nodes.get(firstNodeIndex);
                graphics.drawLine(
                        firstNode.x + 50, firstNode.y + 50,
                        previousNode.x + 50, previousNode.y + 50
                );
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

    private static class EdgeKey {

        public static EdgeKey ofNodes(int a, int b) {
            if (a == b) {
                throw new IllegalArgumentException("Edges cannot connect nodes to themselves.");
            }
            if (a < b) {
                return new EdgeKey(a, b);
            }
            return new EdgeKey(b, a);
        }

        private final int lesser;
        private final int higher;

        private EdgeKey(int lesser, int higher) {
            this.lesser = lesser;
            this.higher = higher;
        }

        public int hashCode() {
            return lesser * 31 + higher;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof EdgeKey)) {
                return false;
            }
            EdgeKey otherEdgeKey = (EdgeKey) other;
            return lesser == otherEdgeKey.lesser && higher == otherEdgeKey.higher;
        }

        public String toString() {
            return "EdgeKey[" + lesser + ":" + higher + "]";
        }

        public boolean connects(int currentNode) {
            return lesser == currentNode || higher == currentNode;
        }

    }

    private static class Edge {
        private final EdgeKey key;
        private final Variable variable;

        private Edge(EdgeKey key, Variable variable) {
            this.key = key;
            this.variable = variable;
        }

        public String toString() {
            return "Edge: " + key;
        }
    }

    private static class TourPart {
        private final int node;
        private final Variable edgeVar;

        private TourPart(int node, Variable edgeVar) {
            this.node = node;
            this.edgeVar = edgeVar;
        }

        public String toString() {
            return "TourPart: " + node;
        }
    }

    private static class SubtourConstraintGenerator extends ConstraintHandler {

        private final int nodeCount;
        private final TourFinder tourFinder;
        private final List<Edge> edges;
        private final SolutionRecorder solutionRecorder;

        public SubtourConstraintGenerator(
                int nodeCount,
                TourFinder tourFinder,
                List<Edge> edges,
                SolutionRecorder solutionRecorder
        ) {
            super(
                    "SubtourExclusion",
                    "Creates constraints to exclude subtours",
                    new ConstraintHandlerOptions()
            );
            this.nodeCount = nodeCount;
            this.tourFinder = tourFinder;
            this.edges = edges;
            this.solutionRecorder = solutionRecorder;
        }

        private void log(String message) {
            System.out.println("[ExampleLog]: " + message);
        }

        @Override
        protected SCIP_Retcode check(Scip scip, Solution solution, ResultHolder resultHolder) {
            log("check");
            List<List<TourPart>> tours = tourFinder.findTours(edges, scip, solution);
            resultHolder.setResult(checkFeasibility(tours));
            return SCIP_Retcode.SCIP_OKAY;
        }

        @Override
        protected SCIP_Retcode sepalp(Scip scip, ResultHolder resultHolder) {
            log("sepalp");
            List<List<TourPart>> tours = tourFinder.findTours(edges, scip, null);
            resultHolder.setResult(separate(scip, tours));
            recordSolution(tours);
            return SCIP_Retcode.SCIP_OKAY;
        }

        @Override
        protected SCIP_Retcode sepasol(Scip scip, Solution solution, ResultHolder resultHolder) {
            log("scipExec, stage: " + scip.getStage());
            List<List<TourPart>> subtours = tourFinder.findTours(edges, scip, solution);
            resultHolder.setResult(separate(scip, subtours));
            recordSolution(subtours);
            return SCIP_Retcode.SCIP_OKAY;
        }

        private void addSubtourConstraints(Scip scip, List<List<TourPart>> subtours) {
            log("Adding <" + subtours.size() + "> subtour constraints.");
            for (List<TourPart> subtour : subtours) {
                String constraintName = "subtourElimination_" + subtour.stream()
                        .map(e -> String.valueOf(e.node))
                        .collect(Collectors.joining("_"));
                Constraint subtourConstraint = scip.createConsLinear(
                        constraintName,
                        subtour.stream()
                                .map(e -> e.edgeVar)
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

        private SCIP_Result separate(Scip scip, List<List<TourPart>> subtours) {
            if (subtours == null || subtours.isEmpty()) {
                return SCIP_Result.SCIP_DIDNOTFIND;
            }
            if (subtours.size() == 1 && subtours.get(0).size() == nodeCount) {
                return SCIP_Result.SCIP_DIDNOTFIND;
            }
            addSubtourConstraints(scip, subtours);
            return SCIP_Result.SCIP_CONSADDED;
        }

        private SCIP_Result checkFeasibility(List<List<TourPart>> subtours) {
            if (subtours == null || subtours.size() != 1 || subtours.get(0).size() != nodeCount) {
                return SCIP_Result.SCIP_INFEASIBLE;
            }
            return SCIP_Result.SCIP_FEASIBLE;
        }

        private void recordSolution(List<List<TourPart>> subtours) {
            if (subtours == null) {
                return;
            }
            solutionRecorder.encounteredSolutions.add(
                    subtours.stream()
                            .map(
                                    l -> l.stream()
                                            .map(p -> p.node)
                                            .collect(Collectors.toList())
                            )
                            .collect(Collectors.toList())
            );
        }

        @Override
        protected SCIP_Retcode free(Scip scip) {
            log("free");
            return super.free(scip);
        }

        @Override
        protected SCIP_Retcode init(Scip scip) {
            log("init");
            return super.init(scip);
        }

        @Override
        protected SCIP_Retcode exit(Scip scip) {
            log("exit");
            return super.exit(scip);
        }

        @Override
        protected SCIP_Retcode initpre(Scip scip) {
            log("initpre");
            return super.initpre(scip);
        }

        @Override
        protected SCIP_Retcode exitpre(Scip scip) {
            log("exitpre");
            return super.exitpre(scip);
        }

        @Override
        protected SCIP_Retcode initsol(Scip scip) {
            log("initsol");
            return super.initsol(scip);
        }

        @Override
        protected SCIP_Retcode exitsol(Scip scip) {
            log("exitsol");
            return super.exitsol(scip);
        }

        @Override
        protected SCIP_Retcode delete(Scip scip) {
            log("delete");
            return super.delete(scip);
        }

        @Override
        protected SCIP_Retcode trans(Scip scip) {
            log("trans");
            return super.trans(scip);
        }

        @Override
        protected SCIP_Retcode initlp(Scip scip) {
            log("initlp");
            return super.initlp(scip);
        }

        @Override
        protected SCIP_Retcode enfolp(Scip scip, ResultHolder resultHolder) {
            log("enfolp");
            List<List<TourPart>> tours = tourFinder.findTours(edges, scip, null);
            resultHolder.setResult(separate(scip, tours));
            recordSolution(tours);
            return SCIP_Retcode.SCIP_OKAY;
        }

        @Override
        protected SCIP_Retcode enforelax(Scip scip) {
            log("enforelax");
            return super.enforelax(scip);
        }

        @Override
        protected SCIP_Retcode enfops(Scip scip) {
            log("enfops");
            return super.enfops(scip);
        }

        @Override
        protected SCIP_Retcode prop(Scip scip) {
            log("prop");
            return super.prop(scip);
        }

        @Override
        protected SCIP_Retcode presol(Scip scip) {
            log("presol");
            return super.presol(scip);
        }

        @Override
        protected SCIP_Retcode resprop(Scip scip) {
            log("resprop");
            return super.resprop(scip);
        }

        @Override
        protected SCIP_Retcode lock(Scip scip) {
            log("lock");
            return super.lock(scip);
        }

        @Override
        protected SCIP_Retcode active(Scip scip) {
            log("active");
            return super.active(scip);
        }

        @Override
        protected SCIP_Retcode deactive(Scip scip) {
            log("deactive");
            return super.deactive(scip);
        }

        @Override
        protected SCIP_Retcode enable(Scip scip) {
            log("enable");
            return super.enable(scip);
        }

        @Override
        protected SCIP_Retcode disable(Scip scip) {
            log("disable");
            return super.disable(scip);
        }

        @Override
        protected SCIP_Retcode delvars(Scip scip) {
            log("delvars");
            return super.delvars(scip);
        }

        @Override
        protected SCIP_Retcode print(Scip scip) {
            log("print");
            return super.print(scip);
        }

        @Override
        protected SCIP_Retcode copy(Scip scip) {
            log("copy");
            return super.copy(scip);
        }

        @Override
        protected SCIP_Retcode parse(Scip scip) {
            log("parse");
            return super.parse(scip);
        }

        @Override
        protected SCIP_Retcode getvars(Scip scip) {
            log("getvars");
            return super.getvars(scip);
        }

        @Override
        protected SCIP_Retcode getnvars(Scip scip) {
            log("getnvars");
            return super.getnvars(scip);
        }

        @Override
        protected SCIP_Retcode getdivebdchgs(Scip scip) {
            log("getdivebdchgs");
            return super.getdivebdchgs(scip);
        }

        @Override
        protected SCIP_Retcode getpermsymgraph(Scip scip) {
            log("getpermsymgraph");
            return super.getpermsymgraph(scip);
        }

        @Override
        protected SCIP_Retcode getsignedpermsymgraph(Scip scip) {
            log("getsignedpermsymgraph");
            return super.getsignedpermsymgraph(scip);
        }
    }

    private static class TourFinder {
        private List<List<TourPart>> findTours(List<Edge> edges, Scip scip, Solution solution) {
            List<Edge> activeEdges = new ArrayList<>();
            for (Edge edge : edges) {
                if (scip.getSolVal(solution, edge.variable) > 0.5) {
                    activeEdges.add(edge);
                }
            }
            List<List<TourPart>> tours = new ArrayList<>();
            while (!activeEdges.isEmpty()) { // If there are no more active edges to process we've found all subtours
                List<TourPart> currentTour = new ArrayList<>();
                Edge currentEdge = activeEdges.remove(activeEdges.size() - 1);
                int firstNode = currentEdge.key.lesser;
                int currentNode = currentEdge.key.higher;
                while (currentNode != firstNode) { // otherwise we looped around and the subtour is complete
                    currentTour.add(new TourPart(currentNode, currentEdge.variable));
                    currentEdge = findAndRemoveEdgeForNode(activeEdges, currentNode);
                    if (currentEdge == null) {
                        // we don't have a complete loop, so the solution is not feasible.
                        // could we return any other complete subtour instead?
                        return null;
                    }
                    if (currentNode == currentEdge.key.lesser) {
                        currentNode = currentEdge.key.higher;
                    } else {
                        currentNode = currentEdge.key.lesser;
                    }
                }
                currentTour.add(new TourPart(currentNode, currentEdge.variable));
                tours.add(currentTour);
            }
            return tours;
        }

        private Edge findAndRemoveEdgeForNode(List<Edge> activeEdges, int currentNode) {
            Iterator<Edge> iterator = activeEdges.iterator();
            while(iterator.hasNext()) {
                Edge edge = iterator.next();
                if (edge.key.connects(currentNode)) {
                    iterator.remove();
                    return edge;
                }
            }
            return null;
        }
    }

    private static class SolutionRecorder {
        private final List<List<List<Integer>>> encounteredSolutions = new ArrayList<>();
    }

}
