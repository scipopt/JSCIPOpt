import jscip.*;

/** Example how to create a problem with quadratic constraints. */
public class Quadratic
{
   public static void main(String args[])
   {
      // load generated C-library
      System.loadLibrary("jscip");

      Scip scip = new Scip();

      // set up data structures of SCIP
      scip.create("LinearExample");

      // create variables (also adds variables to SCIP)
      Variable x = scip.createVar("x", 2.0, 3.0, 1.0, SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS);
      Variable y = scip.createVar("y", 0.0, 1.0, -3.0, SCIP_Vartype.SCIP_VARTYPE_BINARY);

      // create quadratic constraint: x^2 + 2xy - y^2 + x + y <= 11
      Variable[] quadvars1 = {x, x, y};
      Variable[] quadvars2 = {x, y, y};
      double[] quadcoefs = {1.0, 2.0, -1.0};
      Variable[] linvars = {x, y};
      double[] lincoefs = {1.0, 1.0};

      Constraint quadcons = scip.createConsQuadratic("quadcons", quadvars1, quadvars2, quadcoefs, linvars, lincoefs, -scip.infinity(), 11);

      // add constraint to SCIP
      scip.addCons(quadcons);

      // release constraint (if not needed anymore)
      scip.releaseCons(quadcons);

      // solve problem
      scip.solve();

      // print the best solution
      Solution sol = scip.getBestSol();

      if( sol != null )
      {
         System.out.println("best solution (x,y) = (" + scip.getSolVal(sol, x) + ", " + scip.getSolVal(sol, y) + ") with objective value " + scip.getSolOrigObj(sol));
      }

      // release variables (if not needed anymore)
      scip.releaseVar(y);
      scip.releaseVar(x);

      // free SCIP
      scip.free();
   }
}
