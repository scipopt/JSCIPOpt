import jscip.*;

/** Example how to create a problem with a custom expression handler.
    Implements: https://www.minlplib.org/prob10.html using a custom square operator. */
public class CustomExpressionHandler {
   private static final class SquareHandler extends ExpressionHandler {
      public SquareHandler(Scip scip) {
         super(scip, "sqr", "square expression", 55001);
      }

      public SquareHandler(Scip targetscip, SquareHandler source) {
         super(targetscip, source);
      }

      public Expression createExpression(Expression child) {
         return createExpression(child, null);
      }

      public Expression createExpression(Expression child, ExpressionOwnerCreateCallback ownercreate) {
         return createExpressionInternal(null, new Expression[]{child}, ownercreate);
      }

      @Override
      protected boolean isCloneable() {
         return true;
      }

      @Override
      protected SquareHandler clone(Scip targetscip) {
         return new SquareHandler(targetscip, this);
      }

      @Override
      protected double eval(Expression expr, Solution sol) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         double childval = children[0].getEvalValue();
         return childval * childval;
      }

      @Override
      protected Expression simplify(Expression expr, ExpressionOwnerCreateCallback ownercreate) throws Exception {
         return expr; // nothing to simplify
         // As an example, the code below would simplify this square to a power with exponent 2.
         // It is commented out because it would mean that nothing else would get tested in practice.
         /*assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         return scip.createExprPow(children[0], 2.0, ownercreate);*/
      }

      @Override
      protected int compare(Expression expr1, Expression expr2) throws Exception {
         assert(expr1 != null);
         Expression[] children1 = expr1.getChildren();
         assert(children1 != null && children1.length == 1);
         assert(expr2 != null);
         Expression[] children2 = expr2.getChildren();
         assert(children2 != null && children2.length == 1);
         return scip.compareExpr(children1[0], children2[0]);
      }

      @Override
      protected void print(Expression expr, long stage, int currentchild, long parentprecedence, SWIGTYPE_p_FILE file) throws Exception {
         if (stage == SCIP_EXPRITER_ENTEREXPR) {
            scip.infoMessage(file, "sqr(");
         } else if (stage == SCIP_EXPRITER_VISITINGCHILD) {
            assert(currentchild == 0);
         } else if (stage == SCIP_EXPRITER_LEAVEEXPR) {
            scip.infoMessage(file, ")");
         }
      }

      @Override
      protected Expression parse(ByteBuffer string, ExpressionOwnerCreateCallback ownercreate) throws Exception {
         Expression child = scip.parseExpr(string, ownercreate);
         Expression result = createExpression(child, ownercreate);
         scip.releaseExpr(child);
         return result;
      }

      @Override
      protected double bwdiff(Expression expr, int childidx) throws Exception {
         assert(expr != null);
         assert(childidx == 0);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         // d(x*x)/dx=2*x
         return 2.0 * children[0].getEvalValue();
      }

      @Override
      protected double fwdiff(Expression expr, Solution direction) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         // d(x*x)/dx*Du(x)=2*x*Du(x)
         return 2.0 * children[0].getEvalValue() * children[0].getDot();
      }

      @Override
      protected double bwfwdiff(Expression expr, int childidx, Solution direction) throws Exception {
         // dd(x*x)/(dxdx)*Du(x)=2*Du(x)
         assert(expr != null);
         assert(childidx == 0);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         return 2.0 * children[0].getDot();
      }

      @Override
      protected Interval inteval(Expression expr, ExpressionIntEvalVarCallback intevalvar) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         Interval childval = children[0].getActivity();
         // FIXME: This does not do rigorous rounding, use SCIP method instead!
         return childval.isEmpty() ? childval
            : (childval.inf <= 0.0 && childval.sup >= 0.0) ? new Interval(0.0, Math.max(childval.inf * childval.inf, childval.sup * childval.sup))
            : childval.sup <= 0.0 ? new Interval(childval.sup * childval.sup, childval.inf * childval.inf)
            : new Interval(childval.inf * childval.inf, childval.sup * childval.sup);
      }

      @Override
      protected Estimate estimate(Expression expr, Interval[] localbounds, Interval[] globalbounds, double[] refpoint, boolean overestimate, double targetvalue) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         assert(localbounds != null && localbounds.length == 1);
         assert(globalbounds != null && globalbounds.length == 1);
         assert(refpoint != null && refpoint.length == 1);
         assert(!localbounds[0].isEmpty());
         if (overestimate) {
            // secant
            double x1 = localbounds[0].inf;
            double x2 = localbounds[0].sup;
            if (x1 <= -scip.infinity() || x2 >= scip.infinity()) {
               return null;
            }
            double y1 = x1 * x1;
            double y2 = x2 * x2;
            double alpha = (y2 - y1) / (x2 - x1);
            return new Estimate(new double[]{alpha}, y1 - alpha * x1, true, true);
         } else {
            // tangent at the reference point
            double x0 = refpoint[0];
            return new Estimate(new double[]{2.0 * x0}, -(x0 * x0), false, false);
         }
      }

      @Override
      protected InitEstimate[] initestimates(Expression expr, Interval[] bounds, boolean overestimate) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         assert(bounds != null && bounds.length == 1);
         if (bounds[0].isEmpty() || bounds[0].inf == bounds[0].sup) {
            return null;
         }
         double x1 = bounds[0].inf;
         double x2 = bounds[0].sup;
         if (overestimate) {
            // secant
            if (x1 <= -scip.infinity() || x2 >= scip.infinity()) {
               return null;
            }
            double y1 = x1 * x1;
            double y2 = x2 * x2;
            double alpha = (y2 - y1) / (x2 - x1);
            return new InitEstimate[]{new InitEstimate(new double[]{alpha}, y1 - alpha * x1)};
         } else {
            // tangents (normally infinitely many, compute 2 or 3 of them)
            if (x1 <= -scip.infinity()) {
              x1 = -1.0;
            }
            InitEstimate e1 = new InitEstimate(new double[]{2.0 * x1}, -(x1 * x1));
            if (x2 >= -scip.infinity()) {
              x2 = 1.0;
            }
            InitEstimate e2 = new InitEstimate(new double[]{2.0 * x2}, -(x2 * x2));
            if (x1 < 0 && x2 > 0)  {
               return new InitEstimate[]{e1, new InitEstimate(new double[]{0.0}, 0.0), e2};
            } else {
               return new InitEstimate[]{e1, e2};
            }
         }
      }

      @Override
      protected Interval[] reverseprop(Expression expr, Interval bounds, Interval[] childrenbounds) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         assert(childrenbounds != null && childrenbounds.length == 1);
         if (childrenbounds[0].isEmpty()) {
            return childrenbounds;
         }
         // FIXME: This does not do rigorous rounding, use SCIP method instead!
         Interval sqrtbounds = new Interval(Math.sqrt(Math.max(bounds.inf, 0.)), Math.sqrt(Math.max(bounds.sup, 0.)));
         Interval computedbounds;
         if (childrenbounds[0].inf > -sqrtbounds.inf) {
            computedbounds = sqrtbounds;
         } else if (childrenbounds[0].sup < sqrtbounds.inf) {
            // FIXME: Use SCIP negate method instead of doing this by hand.
            computedbounds = new Interval(-sqrtbounds.sup, -sqrtbounds.inf);
         } else {
            computedbounds = new Interval(-sqrtbounds.sup, sqrtbounds.sup);
         }
         // FIXME: Use SCIP intersect method instead of doing this by hand.
         Interval newcbounds = new Interval(Math.max(childrenbounds[0].inf, computedbounds.inf), Math.min(childrenbounds[0].sup, computedbounds.sup));
         if (newcbounds.isEmpty()) {
            newcbounds.setEmpty(); // use canonical empty interval
         }
         return new Interval[]{newcbounds};
      }

      @Override
      protected int hash(Expression expr, int[] childrenhashes) throws Exception {
         assert(childrenhashes != null && childrenhashes.length == 1);
         return 17 * childrenhashes[0];
      }

      @Override
      protected SCIP_EXPRCURV[] curvature(Expression expr, SCIP_EXPRCURV exprcurvature) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         if (exprcurvature != SCIP_EXPRCURV.SCIP_EXPRCURV_CONVEX) {
            return null; // the square is in general never linear or concave
         }
         scip.evalExprActivity(children[0]);
         Interval childbounds = children[0].getActivity();
         if (childbounds.inf >= 0) {
            return new SCIP_EXPRCURV[]{SCIP_EXPRCURV.SCIP_EXPRCURV_CONVEX};
         } else if (childbounds.sup <= 0) {
            return new SCIP_EXPRCURV[]{SCIP_EXPRCURV.SCIP_EXPRCURV_CONCAVE};
         } else {
            return new SCIP_EXPRCURV[]{SCIP_EXPRCURV.SCIP_EXPRCURV_LINEAR};
         }
      }

      @Override
      protected SCIP_MONOTONE monotonicity(Expression expr, int childidx) throws Exception {
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         scip.evalExprActivity(children[0]);
         Interval childbounds = children[0].getActivity();
         if (childbounds.inf >= 0) {
            return SCIP_MONOTONE.SCIP_MONOTONE_INC;
         } else if (childbounds.sup <= 0) {
            return SCIP_MONOTONE.SCIP_MONOTONE_DEC;
         } else {
            return SCIP_MONOTONE.SCIP_MONOTONE_UNKNOWN; // not monotone
         }
      }

      @Override
      protected boolean integrality(Expression expr) throws Exception {
         // x*x is integral if x is integral.
         // (Technically, x could also be an irrational square root of an
         // integer, but that cannot be checked for and is not exactly
         // representable as a floating-point number anyway. And false negatives
         // are allowed here, only false positives are not.)
         assert(expr != null);
         Expression[] children = expr.getChildren();
         assert(children != null && children.length == 1);
         return children[0].isIntegral();
      }

      @Override
      protected SymData getsymdata(Expression expr) throws Exception {
         return new SymData(); // no symmetries
      }
   }

   public static void main(String args[]) {
      // load generated C-library
      System.loadLibrary("jscip");

      Scip scip = new Scip();

      // set up data structures of SCIP
      scip.create("NonlinearExample");

      // set up expression handler
      SquareHandler square = new SquareHandler(scip);
      square.include();

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
      Expression sqr1 = square.createExpression(sum1);
      Expression sum2 = scip.createExprSum(new Expression[]{i3e}, null, -5.0);
      Expression sqr2 = square.createExpression(sum2);
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
