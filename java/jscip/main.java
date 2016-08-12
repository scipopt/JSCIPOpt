import jscip.*;

public class main {

  public static void main(String argv[]) {
     System.loadLibrary("jni");

     Scip scip = new Scip();

     // create SCIP
     scip.create("hello_world");

     // read problem from a file
     scip.readProb("circle.cip");

     // create and add variables
     Variable x = scip.createVar("x", 0.0, 1.0, 33.3, SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS);
     Variable y = scip.createVar("y", 0.0, 1.0, 23.3, SCIP_Vartype.SCIP_VARTYPE_INTEGER);

     // print all variables in SCIP
     Variable[] allvars = scip.getVars();
     for( int i = 0; i < allvars.length; ++i )
        System.out.println(allvars[i]);

     // create and add a linear constraint
     Variable[] vars = {x, y};
     double[] vals = {6.0, 7.0};
     Constraint lincons = scip.createConsLinear("lincons", vars, vals, -1.0, scip.infinity());
     System.out.println(lincons);
     scip.addCons(lincons);
     scip.releaseCons(lincons);

     // create and add a quadratic constraint
     Variable[] quadvars1 = {x, x, y};
     Variable[] quadvars2 = {x, y, y};
     double[] quadcoefs = {1, 2, 3};
     Variable[] linvars = {x, y};
     double[] lincoefs = {4, 5};
     Constraint quadcons = scip.createConsQuadratic("quadcons", quadvars1, quadvars2, quadcoefs, linvars, lincoefs, -scip.infinity(), 11);
     scip.addCons(quadcons);
     scip.releaseCons(quadcons);

     // write problem into a file
     scip.writeOrigProblem("orig.cip");

     // hide the output
     scip.hideOutput(false);

     // solve the problem, print statistics and the best solution found
     scip.solve();
     scip.printStatistics();
     scip.printBestSol(false);

     // print best solution values
     Solution bestsol = scip.getBestSol();

     if( bestsol != null )
     {
        System.out.println("best solution with objective value " + scip.getSolOrigObj(bestsol));
        for( int i = 0; i < allvars.length; ++i )
        {
           System.out.println(allvars[i].getName() + " = " + scip.getSolVal(bestsol, allvars[i]));
        }
     }

     // print all solutions
     Solution[] allsols = scip.getSols();

     for( int s = 0; allsols != null && s < allsols.length; ++s )
     {
        System.out.println("print solution " + s + " with objective value " + scip.getSolOrigObj(allsols[s]));
        for( int i = 0; i < allvars.length; ++i )
        {
           System.out.println("  " + allvars[i].getName() + " = " + scip.getSolVal(allsols[s], allvars[i]));
        }
     }

     // release created variables and SCIP
     scip.releaseVar(y);
     scip.releaseVar(x);
     scip.free();

     // check if memory has been freed properly
     SCIPJNI.BMScheckEmptyMemory();
     assert(SCIPJNI.BMSgetMemoryUsed() == 0);
  }
}
