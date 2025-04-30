import jscip.*;

/** Example how to create a problem with nonlinear constraints.
    Implements: https://www.minlplib.org/prob10.html */
public class Nonlinear
{
   public static void main(String args[])
   {
      // load generated C-library
      System.loadLibrary("jscip");

      Scip scip = new Scip();

      // set up data structures of SCIP
      scip.create("NonlinearExample");

      // create variables (also adds variables to SCIP)
      Variable objvar = scip.createVar("objvar", -scip.infinity(), scip.infinity(), 1.0, SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS);
      Variable x2 = scip.createVar("x2", 0.0, 10.0, 0.0, SCIP_Vartype.SCIP_VARTYPE_CONTINUOUS);
      Variable i3 = scip.createVar("i3", 0.0, 10.0, 0.0, SCIP_Vartype.SCIP_VARTYPE_INTEGER);

      // create linear constraints (also adds constraints to SCIP)
      Variable[] vars = {x2, i3};
      // e1: 0.7*x2 + i3 <= 7
      double[] vals1 = {0.7, 1.0};
      Constraint e1 = scip.createConsLinear("e1", vars, vals1, -scip.infinity(), 7.0);
      scip.addCons(e1);
      scip.releaseCons(e1);
      // e2: 2.5*x2 + i3 <= 19
      double[] vals2 = {2.5, 1.0};
      Constraint e2 = scip.createConsLinear("e2", vars, vals2, -scip.infinity(), 19.0);
      scip.addCons(e2);
      scip.releaseCons(e2);

      // create nonlinear constraint (also adds constraint to SCIP)
      // e3: 1.1*((-10 + 2*x2)^2 + (-5 + i3)^2) + sin((-10 + 2*x2)^2 + (-5 + i3)^2) - objvar = 0
      Expression x2e = scip.createExprVar(x2);
      Expression i3e = scip.createExprVar(i3);
      Expression objvare = scip.createExprVar(objvar);
      Expression sum1 = scip.createExprSum(new Expression[]{x2e}, new double[]{2.0}, -10.0);
      Expression sqr1 = scip.createExprPow(sum1, 2.0);
      Expression sum2 = scip.createExprSum(new Expression[]{i3e}, null, -5.0);
      Expression sqr2 = scip.createExprPow(sum2, 2.0);
      Expression sum3 = scip.createExprSum(new Expression[]{sqr1, sqr2}, null, 0.0);
      Expression sinSum3 = scip.createExprSin(sum3);
      Expression e3e = scip.createExprSum(new Expression[]{sum3, sinSum3, objvare},  new double[]{1.1, 1.0, -1.0}, 0.0);
      Constraint e3 = scip.createConsNonlinear("e3", e3e, 0.0, 0.0);
      scip.releaseExpr(e3e);
      scip.releaseExpr(sinSum3);
      scip.releaseExpr(sum3);
      scip.releaseExpr(sqr2);
      scip.releaseExpr(sum2);
      scip.releaseExpr(sqr1);
      scip.releaseExpr(sum1);
      scip.releaseExpr(objvare);
      scip.releaseExpr(i3e);
      scip.releaseExpr(x2e);
      scip.addCons(e3);
      scip.releaseCons(e3);

      // set parameters
      scip.setRealParam("limits/time", 100.0);
      scip.setRealParam("limits/memory", 10000.0);
      scip.setLongintParam("limits/totalnodes", 1000);

      // solve problem
      scip.solve();
      System.out.println("final gap = " + scip.getGap());

      // print all solutions
      Solution[] allsols = scip.getSols();

      for( int s = 0; allsols != null && s < allsols.length; ++s )
         System.out.println("solution (x2,i3) = (" + scip.getSolVal(allsols[s], x2) + ", " + scip.getSolVal(allsols[s], i3) + ") with objective value " + scip.getSolOrigObj(allsols[s]));

      // release variables (if not needed anymore)
      scip.releaseVar(objvar);
      scip.releaseVar(i3);
      scip.releaseVar(x2);

      // free SCIP
      scip.free();
   }
}
