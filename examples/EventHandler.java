import jscip.*;

import java.util.*;

/**
 * Illustrating the use of ObjEventHandler to implement a custom stopping criterion to get a decent solution in a short
 * period of time.
 */
public class EventHandler {

    public static void main(String[] args) {
        System.loadLibrary("jscip");
        if (args.length == 1 && args[0].equals("--help")) {
            System.out.println("Usage: EventHandler <assetCount> <maxPositionCount> <minPositionWeight> <maxPositionWeight> <riskAffinity> <rngSeed>");
            return;
        }
        int assetCount = 35;
        if (args.length >= 1) {
            assetCount = Integer.parseInt(args[0]);
        }
        int maxPositionCount = 12;
        if (args.length >= 2) {
            maxPositionCount = Integer.parseInt(args[1]);
        }
        double minPositionWeight = 0.01;
        if (args.length >= 3) {
            minPositionWeight = Double.parseDouble(args[2]);
        }
        double maxPositionWeight = 0.2;
        if (args.length >= 4) {
            maxPositionWeight = Double.parseDouble(args[3]);
        }
        double riskAffinity = 0.1;
        if (args.length >= 5) {
            riskAffinity = Double.parseDouble(args[4]);
        }
        long rngSeed = 0;
        if (args.length >= 6) {
            rngSeed = args[5].hashCode();
        }

        // random generate input data, however assets with higher returns should have generally higher risk
        Random rng = new Random(rngSeed);
        double oneOverAssetCount = 1.0 / (assetCount - 1);

        double[] returns = new double[assetCount];
        for (int i = 0; i != assetCount; ++i) {
            returns[i] = 0.005 + 0.1 * oneOverAssetCount * (i - 0.2 + rng.nextDouble() * 0.4);
        }

        // create covariance matrix
        double[][] covarianceMatrix = new double[assetCount][];
        double[] assetVariances = new double[assetCount];
        for (int i = 0; i != assetCount; ++i) {
            assetVariances[i] = returns[i] * returns[i] * 20;
        }
        for (int i = 0; i != assetCount; ++i) {
            double[] row = covarianceMatrix[i] = new double[assetCount];
            double assetVarianceI = assetVariances[i];
            for (int j = 0; j <= i; ++j) {
                double covariance;
                if (i == j) {
                    covariance = assetVarianceI * assetVariances[j] // correlation is 1 on the diagonal by definition
                            + (0.003 + rng.nextDouble() * 0.0015); // some intrinsic risk on the diagonal
                } else {
                    // random correlation
                    covariance = rng.nextDouble() * assetVarianceI * assetVariances[j];
                }
                row[j] = covariance;
            }
        }
        // copying lower triangle of the matrix into the upper triangle
        for (int i = 0; i != assetCount; ++i) {
            for (int j = 0; j < i; ++j) {
                covarianceMatrix[j][i] = covarianceMatrix[i][j];
            }
        }
        double[][] unalteredCovMat = covarianceMatrix;
        //*
        // using a PD matrix as covariance speeds up solving by 100 to 1000 times, but that is not what this test is
        // after

        // forcing covariance matrix to be psd
        covarianceMatrix = gemm(covarianceMatrix, covarianceMatrix);
        // forcing pd
        double jiggle = trace(covarianceMatrix) * 1.0e-6 / assetCount;
        for (int i = 0; i != assetCount; ++i) {
            covarianceMatrix[i][i] += rng.nextDouble() * jiggle;
        }
        //*/

        Scip scip = new Scip();
        scip.create("Portfolio Optimization");
        // The debugger crashes the program because it tries to access Constraint::getName, if the constraint is
        // released too early. So I add them all into a list and only release everything at the end.
        List<Variable> variables = new ArrayList<>();
        List<Constraint> constraints = new ArrayList<>();

        // create portfolio weight variables
        Variable[] positionVariables = new Variable[assetCount];
        for (int i = 0; i != assetCount; ++i) {
            variables.add(
                    positionVariables[i] = scip.createVar(
                            "position_" + i,
                            0.0,
                            maxPositionWeight,
                            0.0,
                            SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS
                    )
            );
        }
        // create binary positive indicator variables
        Variable[] positiveIndicators = new Variable[assetCount];
        for (int i = 0; i != assetCount; ++i) {
            variables.add(
                    positiveIndicators[i] = scip.createVar(
                            "posInd_" + i,
                            0.0,
                            1.0,
                            0.0,
                            SCIP_Vartype.SCIP_VARTYPE_BINARY
                    )
            );
        }
        // linking position variables with positive indicator variables
        for (int i = 0; i != assetCount; ++i) {
            // w_i - b_i <= 0
            Constraint cons = scip.createConsLinear(
                    "posIndLink_" + i,
                    new Variable[]{positionVariables[i], positiveIndicators[i]},
                    new double[]{1.0, -1.0},
                    -scip.infinity(),
                    0.0
            );
            constraints.add(cons);
            scip.addCons(cons);
        }
        if (minPositionWeight > 0.0) {
            // create minimum weight constraints. Skipping the first asset assuming it's a liquidity account.
            for (int i = 1; i != assetCount; ++i) {
                // w_i - minPos * b_i >= 0
                Constraint cons = scip.createConsLinear(
                        "minWeight_" + i,
                        new Variable[]{positionVariables[i], positiveIndicators[i]},
                        new double[]{1.0, -minPositionWeight},
                        0.0,
                        scip.infinity()
                );
                scip.addCons(cons);
                constraints.add(cons);
            }
        }

        // budget constraint: sum of all positions must be equal to 1
        double[] arrayOfOnes = new double[assetCount];
        Arrays.fill(arrayOfOnes, 1.0);
        Constraint budget = scip.createConsLinear(
                "budget",
                positionVariables,
                arrayOfOnes,
                1.0,
                1.0
        );
        scip.addCons(budget);
        constraints.add(budget);

        if (maxPositionCount > 0) {
            // max position count constraint, #(positions > 0) <= maxPositionCount
            Constraint positionCountConstraint = scip.createConsLinear(
                    "maxPositionCount",
                    positiveIndicators,
                    arrayOfOnes,
                    0.0,
                    maxPositionCount
            );
            scip.addCons(positionCountConstraint);
            constraints.add(positionCountConstraint);
        }

        Variable objVar = scip.createVar(
                "objVar",
                -scip.infinity(),
                scip.infinity(),
                1.0,
                SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS
        );
        variables.add(objVar);

        // create objective constraint and link it to objVar1
        // maximize returns while also avoiding unnecessary risk. The proportionality factor between risk and return
        // is expressed through riskAffinity parameter. Higher riskAffinity means the user cares more about maximizing
        // his returns and less about avoiding risk:
        // max(
        //    - 1 / (2 * riskAffinity) * positionWeights^T * covarianceMatrix * positionWeights
        //    + returns * positionWeights
        // )
        // Since objVar is minimized by Scip, we need to create the constraint times -1:
        // 1 / (2 * riskAffinity) * positionWeights^T * covarianceMatrix * positionWeights
        //    - returns * positionWeights
        //    - objVar <= 0

        // quadratic objective terms
        int quadObjectiveTermCount = assetCount * (assetCount + 1) / 2; // one triangle of the matrix plus its diagonal
        Variable[] quadVars1 = new Variable[quadObjectiveTermCount];
        Variable[] quadVars2 = new Variable[quadObjectiveTermCount];
        double[] quadCoeffs = new double[quadObjectiveTermCount];
        int quadIdx = 0;
        for (int i = 0; i != assetCount; ++i) {
            for (int j = 0; j <= i; ++j) {
                quadVars1[quadIdx] = positionVariables[i];
                quadVars2[quadIdx] = positionVariables[j];
                quadCoeffs[quadIdx] = covarianceMatrix[i][j] / riskAffinity; // division by 2 disappears, because we
                // would also need to add covarianceMatrix[j][i] but it's symmetric by construction
                ++quadIdx;
            }
        }
        // linear objective terms
        Variable[] linVars = new Variable[assetCount + 1]; // number of positions plus objective variable
        double[] linCoeffs = new double[assetCount + 1];
        for (int i = 0; i != assetCount; ++i) {
            linCoeffs[i] = -returns[i];
            linVars[i] = positionVariables[i];
        }
        linCoeffs[assetCount] = -1;
        linVars[assetCount] = objVar;
        // objective constraint
        Constraint objectiveConstraint = scip.createConsQuadratic(
                "objectiveConstraint",
                quadVars1,
                quadVars2,
                quadCoeffs,
                linVars,
                linCoeffs,
                -scip.infinity(),
                0.0
        );
        scip.addCons(objectiveConstraint);
        constraints.add(objectiveConstraint);

        // adding event handler with custom stopping criteria and solve
        scip.addEventHandler(
                new CustomStoppingCriterion(
                        positionVariables,
                        returns,
                        covarianceMatrix
                )
        );
        scip.addEventHandler(
                new AllEventHandler(
                        "All events",
                        "Dummy handler for all events"
                )
        );
        scip.solve();

        // print result
        Solution solution = scip.getBestSol();
        double[] portfolioWeights = new double[assetCount];
        double[] indicatorValues = new double[assetCount];
        for (int i = 0; i != assetCount; ++i) {
            portfolioWeights[i] = scip.getSolVal(solution, positionVariables[i]);
            indicatorValues[i] = scip.getSolVal(solution, positiveIndicators[i]);
        }

        for (int i = 0; i != assetCount; ++i) {
            double weight = portfolioWeights[i];
            double assetReturn = returns[i];
            if (weight > 0.0) {
                System.out.printf(
                        "Position %3d: %15.10f (pos indicator: %10.3f, return: %8.5f)%n",
                        i,
                        weight,
                        indicatorValues[i],
                        assetReturn
                );
            }
        }
        System.out.format("%n%20s | %15s | %15s%n", "Portfolio", "expectedReturn", "variance");
        System.out.println("=======================================================");
        String format = "%20s | %15.10f | %15.10f%n";
        System.out.format(
                format,
                "Best Solution",
                dot(returns, portfolioWeights),
                calculateVariance(covarianceMatrix, portfolioWeights)
        );
        double[] equalPortfolio = new double[assetCount];
        for (int i = 0; i != assetCount; ++i) {
            equalPortfolio[i] = 1.0 / assetCount;
        }
        System.out.format(
                format,
                "Equally distributed",
                dot(returns, equalPortfolio),
                calculateVariance(covarianceMatrix, equalPortfolio)
        );
        for (int i = 0; i != assetCount; ++i) {
            double[] singleAssetPortfolio = unitVector(assetCount, i);
            System.out.format(
                    format,
                    "SingleAsset " + i + " Pf",
                    dot(returns, singleAssetPortfolio),
                    calculateVariance(covarianceMatrix, singleAssetPortfolio)
            );
        }

        for (Variable variable : variables) {
            scip.releaseVar(variable);
        }
        for (Constraint constraint : constraints) {
            scip.releaseCons(constraint);
        }
        scip.free();
    }

    private static double[] readSolution(Scip scip, Solution solution, Variable[] variables) {
        double[] solVector = new double[variables.length];
        for (int i = 0; i != variables.length; ++i) {
            solVector[i] = scip.getSolVal(solution, variables[i]);
        }
        return solVector;
    }

    private static double calculateVariance(double[][] covariance, double[] portfolio) {
        double variance = 0.0;
        for (int i = 0; i != portfolio.length; ++i) {
            double weightI = portfolio[i];
            double[] covRow = covariance[i];
            if (weightI > 0) {
                for (int j = 0; j != portfolio.length; ++j) {
                    variance += covRow[j] * weightI * portfolio[j];
                }
            }
        }
        return variance;
    }

    public static double dot(double[] a, double[] b) {
        double sum = 0.0;
        assert a.length == b.length : "Vectors must be of equal length.";
        for (int i = 0; i != a.length; ++i) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static double[] unitVector(int size, int unitIndex) {
        double[] result = new double[size];
        result[unitIndex] = 1.0;
        return result;
    }

    public static class CustomStoppingCriterion extends jscip.EventHandler {

        private final Variable[] positionVariables;
        private final double[] returns;
        private final double[][] covarianceMatrix;
        private final long started = System.currentTimeMillis();
        private double lastObjValue;
        private int solutionCount = 0;
        private long lastExecution = started;

        public CustomStoppingCriterion(
                Variable[] positionVariables,
                double[] returns,
                double[][] covarianceMatrix
        ) {
            super("SolutionObserver", "Implements custom stopping criterion.", EventType.BESTSOLFOUND);
            this.positionVariables = positionVariables;
            this.returns = returns;
            this.covarianceMatrix = covarianceMatrix;
        }

        @Override
        protected void scipExec(Scip scip, Event event) {
            assert event instanceof EventSolution : "Unexpected event caught";
            Solution solution = ((EventSolution) event).getSolution();
            if (scip.isSolveInterrupted()) {
                System.out.println("Already interrupted. Skipping execution.");
                return;
            }
            ElapsedSeconds elapsed = elapsedSeconds();
            double currentObjValue = scip.getSolOrigObj(solution);
            { // debug output
                double[] solVec = readSolution(scip, solution, positionVariables);
                double variance = calculateVariance(covarianceMatrix, solVec);
                double expReturn = dot(solVec, returns);
                System.out.println(
                        "Got solution " + solutionCount + ", return: " + expReturn + ", variance: " + variance
                );
            }
            if (solutionCount > 0) {
                double currentGap = scip.getGap();
                if (currentGap < 0.05) {
                    double temporalGap = Math.abs(lastObjValue - currentObjValue)
                            / Math.max(lastObjValue, currentObjValue);
                    if (temporalGap < 1.0e-4 && elapsed.sinceStart > 5.0) {
                        System.out.println("Objective value improvement below limit. Interrupting.");
                        scip.interruptSolve();
                    }
                }
            }
            ++solutionCount;
            lastObjValue = currentObjValue;
        }

        private ElapsedSeconds elapsedSeconds() {
            long currentMillis = System.currentTimeMillis();
            ElapsedSeconds result = new ElapsedSeconds(
                    (currentMillis - started) / 1000.0,
                    (currentMillis - lastExecution) / 1000.0
            );
            lastExecution = currentMillis;
            return result;
        }

    }

    private static class ElapsedSeconds {
        private final double sinceStart;
        private final double sincePreviousExecution;

        private ElapsedSeconds(double sinceStart, double sincePreviousExecution) {
            this.sinceStart = sinceStart;
            this.sincePreviousExecution = sincePreviousExecution;
        }
    }

    /**
     * Implements general matrix multiplication a * b^T if a and b are given in row major order.
     *
     * @param a           first matrix
     * @param bTransposed second matrix transposed
     * @return
     */
    private static double[][] gemm(double[][] a, double[][] bTransposed) {
        double[][] result = new double[a.length][];
        for (int i = 0; i != a.length; ++i) {
            double[] aCol = a[i];
            double[] outRow = new double[aCol.length];
            for (int j = 0; j != bTransposed.length; ++j) {
                outRow[j] = dot(aCol, bTransposed[j]);
            }
            result[i] = outRow;
        }
        return result;
    }

    private static double trace(double[][] a) {
        double result = 0.0;
        for (int i = 0; i != a.length; ++i) {
            result += a[i][i];
        }
        return result;
    }

    private static class AllEventHandler extends jscip.EventHandler {

        public AllEventHandler(String name, String description) {
            super(
                    name,
                    description,
                    EventType.NODEEVENT
                            | EventType.VAREVENT
                            | EventType.SOLEVENT
                            | EventType.ROWEVENT
                            | EventType.PRESOLVEROUND
                            | EventType.LPEVENT
                            | EventType.SYNC
            );
        }

        @Override
        protected void scipExec(Scip scip, Event event) {
            System.out.println("Caught event type: " + event.getClass());
        }
    }

}
