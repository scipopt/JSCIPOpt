package jscip;

import java.lang.reflect.Method;

/** Class representing an expression handler (equivalent of SCIP_Exprhdlr). */
public abstract class ExpressionHandler
{
   // constants related to expression handlers
   public final static long SCIP_EXPRITER_ENTEREXPR = SCIPJNI.getSCIP_EXPRITER_ENTEREXPR();
   public final static long SCIP_EXPRITER_VISITINGCHILD = SCIPJNI.getSCIP_EXPRITER_VISITINGCHILD();
   public final static long SCIP_EXPRITER_VISITEDCHILD = SCIPJNI.getSCIP_EXPRITER_VISITEDCHILD();
   public final static long SCIP_EXPRITER_LEAVEEXPR = SCIPJNI.getSCIP_EXPRITER_LEAVEEXPR();
   public final static long SCIP_EXPRITER_ALLSTAGES = SCIPJNI.getSCIP_EXPRITER_ALLSTAGES();
   public final static int SCIP_EXPR_MAXINITESTIMATES = SCIPJNI.getSCIP_EXPR_MAXINITESTIMATES();

   private SWIGTYPE_p_SCIP_Exprhdlr _exprhdlrptr; /** pointer address class created by SWIG */
   private final ObjExprhdlr _objexprhdlr; /** wrapper implementing the C++ interface */

   // constructor argument remembered for use in almost all methods
   protected final Scip scip;
   // constructor arguments remembered for cloning
   private final String _name;
   private final String _desc;
   private final long _precedence;
   private final boolean _has_simplify;
   private final boolean _has_compare;
   private final boolean _has_print;
   private final boolean _has_parse;
   private final boolean _has_bwdiff;
   private final boolean _has_fwdiff;
   private final boolean _has_bwfwdiff;
   private final boolean _has_inteval;
   private final boolean _has_estimate;
   private final boolean _has_initestimates;
   private final boolean _has_reverseprop;
   private final boolean _has_hash;
   private final boolean _has_curvature;
   private final boolean _has_monotonicity;
   private final boolean _has_integrality;
   private final boolean _has_getsymdata;

   /** reflection check whether a given method is overridden */
   private static boolean hasOverride(Class<? extends ExpressionHandler> subclass, String methodName, Class<?>... parameterTypes)
   {
      Class<?> currclass = subclass;
      Method method;
      while (!currclass.equals(ExpressionHandler.class)) {
         try {
            method = currclass.getDeclaredMethod(methodName, parameterTypes);
         } catch (NoSuchMethodException e) {
            method = null;
         }
         if (method != null) {
            return true;
         }
         currclass = currclass.getSuperclass();
      }
      return false;
   }

   /** constructor to construct a Java expression handler, using reflection to autodetermine the _has_* fields */
   protected ExpressionHandler(Scip scipobj, String name, String desc, long precedence)
   {
      scip = scipobj;
      _name = name;
      _desc = desc;
      _precedence = precedence;
      Class<? extends ExpressionHandler> subclass = getClass();
      _has_simplify = hasOverride(subclass, "simplify", Expression.class, ExpressionOwnerCreateCallback.class);
      _has_compare = hasOverride(subclass, "compare", Expression.class, Expression.class);
      _has_print = hasOverride(subclass, "print", Expression.class, long.class, int.class, long.class, SWIGTYPE_p_FILE.class);
      _has_parse = hasOverride(subclass, "parse", ByteBuffer.class, ExpressionOwnerCreateCallback.class);
      _has_bwdiff = hasOverride(subclass, "bwdiff", Expression.class, int.class);
      _has_fwdiff = hasOverride(subclass, "fwdiff", Expression.class, Solution.class);
      _has_bwfwdiff = hasOverride(subclass, "bwfwdiff", Expression.class, int.class, Solution.class);
      _has_inteval = hasOverride(subclass, "inteval", Expression.class, ExpressionIntEvalVarCallback.class);
      _has_estimate = hasOverride(subclass, "estimate", Expression.class, Interval[].class, Interval[].class, double[].class, boolean.class, double.class);
      _has_initestimates = hasOverride(subclass, "initestimates", Expression.class, Interval[].class, boolean.class);
      _has_reverseprop = hasOverride(subclass, "reverseprop", Expression.class, Interval.class, Interval[].class);
      _has_hash = hasOverride(subclass, "hash", Expression.class, int[].class);
      _has_curvature = hasOverride(subclass, "curvature", Expression.class, SCIP_EXPRCURV.class);
      _has_monotonicity = hasOverride(subclass, "monotonicity", Expression.class, int.class);
      _has_integrality = hasOverride(subclass, "integrality", Expression.class);
      _has_getsymdata = hasOverride(subclass, "getsymdata", Expression.class);
      SWIGTYPE_p_SCIP scipptr = scipobj.getPtr();
      _objexprhdlr = createObjExprhdlr(scipptr);
   }

   /** default constructor to construct a Java expression handler */
   protected ExpressionHandler(Scip scipobj, String name, String desc, long precedence, boolean has_simplify, boolean has_compare, boolean has_print, boolean has_parse, boolean has_bwdiff, boolean has_fwdiff, boolean has_bwfwdiff, boolean has_inteval, boolean has_estimate, boolean has_initestimates, boolean has_reverseprop, boolean has_hash, boolean has_curvature, boolean has_monotonicity, boolean has_integrality, boolean has_getsymdata)
   {
      scip = scipobj;
      _name = name;
      _desc = desc;
      _precedence = precedence;
      _has_simplify = has_simplify;
      _has_compare = has_compare;
      _has_print = has_print;
      _has_parse = has_parse;
      _has_bwdiff = has_bwdiff;
      _has_fwdiff = has_fwdiff;
      _has_bwfwdiff = has_bwfwdiff;
      _has_inteval = has_inteval;
      _has_estimate = has_estimate;
      _has_initestimates = has_initestimates;
      _has_reverseprop = has_reverseprop;
      _has_hash = has_hash;
      _has_curvature = has_curvature;
      _has_monotonicity = has_monotonicity;
      _has_integrality = has_integrality;
      _has_getsymdata = has_getsymdata;
      SWIGTYPE_p_SCIP scipptr = scipobj.getPtr();
      _objexprhdlr = createObjExprhdlr(scipptr);
   }

   /** helper method to construct the ObjExprhdlr implementation */
   private ObjExprhdlr createObjExprhdlr(SWIGTYPE_p_SCIP scipptr) {
      ObjExprhdlr objexprhdlr = new ObjExprhdlr(scipptr, _name, _desc, _precedence, 1, 1, _has_simplify ? 1 : 0, _has_compare ? 1 : 0, _has_print ? 1 : 0, _has_parse ? 1 : 0, _has_bwdiff ? 1 : 0, _has_fwdiff ? 1 : 0, _has_bwfwdiff ? 1 : 0, _has_inteval ? 1 : 0, _has_estimate ? 1 : 0, _has_initestimates ? 1 : 0, _has_reverseprop ? 1 : 0, _has_hash ? 1 : 0, _has_curvature ? 1 : 0, _has_monotonicity ? 1 : 0, _has_integrality ? 1 : 0, _has_getsymdata ? 1 : 0) {
         @Override
         public SCIP_Retcode scip_eval(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SWIGTYPE_p_double val, SWIGTYPE_p_SCIP_SOL sol) {
            try {
               double resval = eval(new Expression(expr), new Solution(sol));
               SCIPJNI.double_array_setitem(val, 0, resval);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_freehdlr(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_Exprhdlr exprhdlr, SWIGTYPE_p_p_SCIP_ExprhdlrData exprhdlrdata) {
            try {
               close();
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_copydata(SWIGTYPE_p_SCIP targetscip, SWIGTYPE_p_SCIP_Exprhdlr targetexprhdlr, SWIGTYPE_p_p_SCIP_ExprData targetexprdata, SWIGTYPE_p_SCIP sourcescip, SWIGTYPE_p_SCIP_EXPR sourceexpr) {
            try {
               SCIP_EXPRDATA data = SCIPJNI.SCIPexprGetData(sourceexpr);
               if (data != null) {
                  Object dataobj = data.getJobj();
                  if (dataobj != null) {
                     // dataobj must have a public clone method.
                     // Unfortunately, this is not in any interface.
                     // Object.clone is protected and cannot be invoked directly.
                     // Cloneable does not actually declare a clone method.
                     // So we have to use reflection to try to invoke it.
                     dataobj = dataobj.getClass().getMethod("clone").invoke(dataobj);
                  }
                  data = new SCIP_EXPRDATA();
                  data.setJobj(dataobj);
               }
               SCIPJNI.SCIP_EXPRDATA_array_setitem(targetexprdata, 0, data);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_freedata(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr) {
            SCIP_EXPRDATA data = SCIPJNI.SCIPexprGetData(expr);
            if (data != null) {
               data.setJobj(null); // releases the reference to the old jobj
               data.delete();
               SCIPJNI.SCIPexprSetData(expr, null);
            }
            return SCIP_Retcode.SCIP_OKAY;
         }

         @Override
         public SCIP_Retcode scip_simplify(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SWIGTYPE_p_p_SCIP_EXPR simplifiedexpr, SWIGTYPE_p_SCIP_EXPR_OWNERCREATE ownercreate, SWIGTYPE_p_void ownercreatedata) {
            try {
               Expression result = simplify(new Expression(expr), new ExpressionOwnerCreateCallback(ownercreate, ownercreatedata));
               SWIGTYPE_p_SCIP_EXPR resultptr = result.getPtr();
               if (SWIGTYPE_p_SCIP_EXPR.getCPtr(resultptr) == SWIGTYPE_p_SCIP_EXPR.getCPtr(expr)) {
                  SCIPJNI.SCIPcaptureExpr(resultptr);
               }
               SCIPJNI.SCIP_EXPR_array_setitem(simplifiedexpr, 0, resultptr);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public int scip_compare(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr1, SWIGTYPE_p_SCIP_EXPR expr2) {
            try {
               return compare(new Expression(expr1), new Expression(expr2));
            } catch (Exception e) {
               return 0;
            }
         }

         @Override
         public SCIP_Retcode scip_print(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, long stage, int currentchild, long parentprecedence, SWIGTYPE_p_FILE file) {
            try {
               print(new Expression(expr), stage, currentchild, parentprecedence, file);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_parse(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_Exprhdlr exprhdlr, SWIGTYPE_p_char stringptr, SWIGTYPE_p_p_char endstring, SWIGTYPE_p_p_SCIP_EXPR expr, SWIGTYPE_p_unsigned_int success, SWIGTYPE_p_SCIP_EXPR_OWNERCREATE ownercreate, SWIGTYPE_p_void ownercreatedata) {
            try {
               ByteBuffer string = new ByteBuffer(stringptr);
               Expression result = parse(string, new ExpressionOwnerCreateCallback(ownercreate, ownercreatedata));
               SCIPJNI.char_array_array_setitem(endstring, 0, string.getPtr());
               SCIPJNI.SCIP_EXPR_array_setitem(expr, 0, result.getPtr());
               SCIPJNI.unsigned_int_array_setitem(success, 0, 1);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (java.text.ParseException e) {
               return SCIP_Retcode.SCIP_READERROR;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_bwdiff(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, int childidx, SWIGTYPE_p_double val) {
            try {
               double resval = bwdiff(new Expression(expr), childidx);
               SCIPJNI.double_array_setitem(val, 0, resval);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_fwdiff(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SWIGTYPE_p_double dot, SWIGTYPE_p_SCIP_SOL direction) {
            try {
               double resdot = fwdiff(new Expression(expr), new Solution(direction));
               SCIPJNI.double_array_setitem(dot, 0, resdot);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_bwfwdiff(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, int childidx, SWIGTYPE_p_double bardot, SWIGTYPE_p_SCIP_SOL direction) {
            try {
               double resbardot = bwfwdiff(new Expression(expr), childidx, new Solution(direction));
               SCIPJNI.double_array_setitem(bardot, 0, resbardot);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_inteval(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SCIP_Interval interval, SWIGTYPE_p_SCIP_EXPR_INTEVALVAR intevalvar, SWIGTYPE_p_void intevalvardata) {
            try {
               inteval(new Expression(expr), new ExpressionIntEvalVarCallback(scip, intevalvar, intevalvardata)).assignToPtr(interval);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_estimate(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SCIP_Interval localbounds, SCIP_Interval globalbounds, SWIGTYPE_p_double refpoint, long overestimate, double targetvalue, SWIGTYPE_p_double coefs, SWIGTYPE_p_double constant, SWIGTYPE_p_unsigned_int islocal, SWIGTYPE_p_unsigned_int success, SWIGTYPE_p_unsigned_int branchcand) {
            try {
               int nchildren = SCIPJNI.SCIPexprGetNChildren(expr);
               Interval[] localboundsarr = new Interval[nchildren];
               Interval[] globalboundsarr = new Interval[nchildren];
               double[] refpointarr = new double[nchildren];
               for (int i = 0; i < nchildren; i++) {
                  localboundsarr[i] = new Interval(SCIPJNI.SCIP_INTERVAL_array_getitem(localbounds, i));
                  globalboundsarr[i] = new Interval(SCIPJNI.SCIP_INTERVAL_array_getitem(globalbounds, i));
                  refpointarr[i] = SCIPJNI.double_array_getitem(refpoint, i);
               }
               Estimate res = estimate(new Expression(expr), localboundsarr, globalboundsarr, refpointarr, overestimate != 0, targetvalue);
               if (res == null) {
                  SCIPJNI.unsigned_int_array_setitem(success, 0, 0);
               } else {
                  if (nchildren != 0) {
                     if (res.coefs == null || res.coefs.length != nchildren) {
                        return SCIP_Retcode.SCIP_INVALIDRESULT;
                     }
                     for (int i = 0; i < nchildren; i++) {
                        SCIPJNI.double_array_setitem(coefs, i, res.coefs[i]);
                     }
                  } else if (res.coefs != null && res.coefs.length != 0) {
                     return SCIP_Retcode.SCIP_INVALIDRESULT;
                  }
                  SCIPJNI.double_array_setitem(constant, 0, res.constant);
                  SCIPJNI.unsigned_int_array_setitem(islocal, 0, res.islocal ? 1 : 0);
                  SCIPJNI.unsigned_int_array_setitem(branchcand, 0, res.branchcand ? 1 : 0);
                  SCIPJNI.unsigned_int_array_setitem(success, 0, 1);
               }
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_initestimates(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SCIP_Interval bounds, long overestimate, SWIGTYPE_p_p_double coefs, SWIGTYPE_p_double constant, SWIGTYPE_p_int nreturned) {
            try {
               int nchildren = SCIPJNI.SCIPexprGetNChildren(expr);
               Interval[] boundsarr = new Interval[nchildren];
               for (int i = 0; i < nchildren; i++) {
                  boundsarr[i] = new Interval(SCIPJNI.SCIP_INTERVAL_array_getitem(bounds, i));
               }
               InitEstimate[] res = initestimates(new Expression(expr), boundsarr, overestimate != 0);
               int nret = res != null ? res.length : 0;
               if (nret != 0) {
                  if (nret > SCIP_EXPR_MAXINITESTIMATES) {
                     return SCIP_Retcode.SCIP_INVALIDRESULT;
                  }
                  for (int i = 0; i < nret; i++) {
                     InitEstimate resi = res[i];
                     SWIGTYPE_p_double coefsi = SCIPJNI.double_array_array_getitem(coefs, i);
                     for (int j = 0; j < nchildren; j++) {
                        SCIPJNI.double_array_setitem(coefsi, j, resi.coefs[j]);
                     }
                     SCIPJNI.double_array_setitem(constant, i, resi.constant);
                  }
                  SCIPJNI.int_array_setitem(nreturned, 0, nret);
               }
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_reverseprop(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SCIP_Interval bounds, SCIP_Interval childrenbounds, SWIGTYPE_p_unsigned_int infeasible) {
            try {
               int nchildren = SCIPJNI.SCIPexprGetNChildren(expr);
               Interval[] childrenboundsarr = new Interval[nchildren];
               for (int i = 0; i < nchildren; i++) {
                  childrenboundsarr[i] = new Interval(SCIPJNI.SCIP_INTERVAL_array_getitem(childrenbounds, i));
               }
               Interval[] res = reverseprop(new Expression(expr), new Interval(bounds), childrenboundsarr);
               if (res == null) {
                  SCIPJNI.unsigned_int_array_setitem(infeasible, 0, 1);
               } else {
                  if (res.length != nchildren) {
                     return SCIP_Retcode.SCIP_INVALIDRESULT;
                  }
                  for (int i = 0; i < nchildren; i++) {
                     SCIPJNI.SCIP_INTERVAL_array_setitem(childrenbounds, i, res[i].getPtr());
                  }
                  SCIPJNI.unsigned_int_array_setitem(infeasible, 0, 0);
               }
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_hash(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SWIGTYPE_p_unsigned_int hashkey, SWIGTYPE_p_unsigned_int childrenhashes) {
            try {
               int nchildren = SCIPJNI.SCIPexprGetNChildren(expr);
               int[] childrenhashesarr = new int[nchildren];
               for (int i = 0; i < nchildren; i++) {
                  childrenhashesarr[i] = (int) SCIPJNI.unsigned_int_array_getitem(childrenhashes, i);
               }
               long reshashkey = (long) hash(new Expression(expr), childrenhashesarr);
               if (reshashkey < 0L) { // force unsigned
                  reshashkey += (1L << 32);
               }
               SCIPJNI.unsigned_int_array_setitem(hashkey, 0, reshashkey);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_curvature(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SCIP_EXPRCURV exprcurvature, SWIGTYPE_p_unsigned_int success, SWIGTYPE_p_SCIP_EXPRCURV childcurv) {
            try {
               int nchildren = SCIPJNI.SCIPexprGetNChildren(expr);
               SCIP_EXPRCURV[] res = curvature(new Expression(expr), exprcurvature);
               if (res == null) {
                  SCIPJNI.unsigned_int_array_setitem(success, 0, 0);
               } else {
                  if (res.length != nchildren) {
                     return SCIP_Retcode.SCIP_INVALIDRESULT;
                  }
                  for (int i = 0; i < nchildren; i++) {
                     SCIPJNI.SCIP_EXPRCURV_array_setitem(childcurv, i, res[i]);
                  }
                  SCIPJNI.unsigned_int_array_setitem(success, 0, 1);
               }
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_monotonicity(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, int childidx, SWIGTYPE_p_SCIP_MONOTONE result) {
            try {
               SCIP_MONOTONE res = monotonicity(new Expression(expr), childidx);
               SCIPJNI.SCIP_MONOTONE_array_setitem(result, 0, res);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_integrality(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SWIGTYPE_p_unsigned_int result) {
            try {
               boolean res = integrality(new Expression(expr));
               SCIPJNI.unsigned_int_array_setitem(result, 0, res ? 1 : 0);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public SCIP_Retcode scip_getsymdata(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_EXPR expr, SWIGTYPE_p_p_SYM_ExprData symdata) {
            try {
               SymData res = getsymdata(new Expression(expr));
               int nconstants = res.constants != null ? res.constants.length : 0;
               int ncoefficients = res.coefficients != null ? res.coefficients.length : 0;
               if ((res.children != null ? res.children.length : 0) != ncoefficients) {
                  return SCIP_Retcode.SCIP_INVALIDRESULT;
               }
               SYM_EXPRDATA result = SCIPJNI.allocSymDataExpr(scip, nconstants, ncoefficients);
               if (result == null) {
                  return SCIP_Retcode.SCIP_NOMEMORY;
               }
               if (nconstants > 0) {
                  SWIGTYPE_p_double constants = result.getConstants();
                  for (int i = 0; i < nconstants; i++) {
                     SCIPJNI.double_array_setitem(constants, i, res.constants[i]);
                  }
               }
               if (ncoefficients > 0) {
                  SWIGTYPE_p_double coefficients = result.getCoefficients();
                  for (int i = 0; i < ncoefficients; i++) {
                     SCIPJNI.double_array_setitem(coefficients, i, res.coefficients[i]);
                  }
                  SWIGTYPE_p_p_SCIP_EXPR children = result.getChildren();
                  for (int i = 0; i < ncoefficients; i++) {
                     SCIPJNI.SCIP_EXPR_array_setitem(children, i, res.children[i].getPtr());
                  }
               }
               SCIPJNI.SYM_EXPRDATA_array_setitem(symdata, 0, result);
               return SCIP_Retcode.SCIP_OKAY;
            } catch (Exception e) {
               return SCIP_Retcode.SCIP_ERROR;
            }
         }

         @Override
         public long iscloneable() {
            return isCloneable() ? 1 : 0;
         }

         @Override
         public ObjCloneable clone(SWIGTYPE_p_SCIP scip) {
            return ExpressionHandler.this.clone(new Scip(scip)).getObjExprhdlr();
         }
      };
      objexprhdlr.swigReleaseOwnership();
      return objexprhdlr;
   }

   /** include this expression handler in the Scip instance scip */
   public void include() {
      if (_objexprhdlr == null) {
         throw new UnsupportedOperationException(
         "Cannot include an ExpressionHandler.Wrapper");
      } else if (_exprhdlrptr != null) {
         throw new IllegalStateException("ExpressionHandler already included");
      } else {
         _exprhdlrptr = SCIPJNI.includeObjExprhdlr(scip.getPtr(), _objexprhdlr, 1);
      }
   }

   /** copy constructor that can be used to implement clone */
   protected ExpressionHandler(Scip targetscip, ExpressionHandler source) {
      this(targetscip, source._name, source._desc, source._precedence,
           source._has_simplify, source._has_compare, source._has_print,
           source._has_parse, source._has_bwdiff, source._has_fwdiff,
           source._has_bwfwdiff, source._has_inteval, source._has_estimate,
           source._has_initestimates, source._has_reverseprop, source._has_hash,
           source._has_curvature, source._has_monotonicity,
           source._has_integrality, source._has_getsymdata);
   }

   /** constructor to wrap a native message handler
    *  (must not be called directly, construct a Wrapper instead) */
   private ExpressionHandler(SWIGTYPE_p_SCIP_Exprhdlr exprhdlrptr)
   {
      _exprhdlrptr = exprhdlrptr;
      _objexprhdlr = null;
      scip = null;
      _name = null;
      _desc = null;
      _precedence = 0;
      _has_simplify = false;
      _has_compare = false;
      _has_print = false;
      _has_parse = false;
      _has_bwdiff = false;
      _has_fwdiff = false;
      _has_bwfwdiff = false;
      _has_inteval = false;
      _has_estimate = false;
      _has_initestimates = false;
      _has_reverseprop = false;
      _has_hash = false;
      _has_curvature = false;
      _has_monotonicity = false;
      _has_integrality = false;
      _has_getsymdata = false;
   }

   /** returns SWIG object type representing a SCIP_Exprhdlr pointer */
   public SWIGTYPE_p_SCIP_Exprhdlr getPtr()
   {
      if (_exprhdlrptr == null) {
         assert(_name != null);
         _exprhdlrptr = SCIPJNI.SCIPfindExprhdlr(scip.getPtr(), _name);
         if (_exprhdlrptr == null) {
            throw new IllegalStateException("include() must be called first");
         }
      }
      return _exprhdlrptr;
   }

   /** returns SWIG object type representing a SCIP_Exprhdlr pointer */
   public static SWIGTYPE_p_SCIP_Exprhdlr getPtr(ExpressionHandler obj) {
      return obj != null ? obj.getPtr() : new SWIGTYPE_p_SCIP_Exprhdlr();
   }

   /** returns SWIG object type representing an ObjExprhdlr pointer (null for an
       ExpressionHandler.Wrapper, which wraps a C SCIP_Exprhdlr pointer, with no
       or an unknown corresponding C++ object) */
   public ObjExprhdlr getObjExprhdlr()
   {
      return _objexprhdlr;
   }

   /** constructs an expression using this expression handler; subclasses should
       add a public createExpression method specific to the subclass, taking the
       correct number of children and any parameters that go into the data
       object, creating the data object, and passing it to this method */
   protected final Expression createExpressionInternal(Object exprdata, Expression[] children, ExpressionOwnerCreateCallback ownercreate) {
      SCIP_EXPRDATA exprdataptr = null;
      if (exprdata != null) {
         exprdataptr = new SCIP_EXPRDATA();
         exprdataptr.setJobj(exprdata);
      }
      int nchildren = (children != null) ? children.length : 0;
      SWIGTYPE_p_p_SCIP_EXPR childrenptr = null;
      if (nchildren != 0) {
         childrenptr = SCIPJNI.new_SCIP_EXPR_array(nchildren);
         for (int i = 0; i < nchildren; i++) {
            SCIPJNI.SCIP_EXPR_array_setitem(childrenptr, i, children[i].getPtr());
         }
      }
      return new Expression(SCIPJNI.createExpr(scip.getPtr(), getPtr(), exprdataptr, nchildren, childrenptr, ownercreate != null ? ownercreate.getFuncPtr() : null, ownercreate != null ? ownercreate.getDataPtr() : null));
   }

   /** override to return true, along with clone, to make this cloneable */
   protected boolean isCloneable() {
      return false;
   }

   /** override to return a clone, along with isCloneable, to make this
       cloneable */
   protected ExpressionHandler clone(Scip targetscip) {
      return null;
   }

   protected abstract double eval(Expression expr, Solution sol) throws Exception;

   protected Expression simplify(Expression expr, ExpressionOwnerCreateCallback ownercreate) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".simplify not implemented, has_simplify should be false");
   }

   protected int compare(Expression expr1, Expression expr2) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".compare not implemented, has_compare should be false");
   }

   protected void print(Expression expr, long stage, int currentchild, long parentprecedence, SWIGTYPE_p_FILE file) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".print not implemented, has_print should be false");
   }

   protected Expression parse(ByteBuffer string, ExpressionOwnerCreateCallback ownercreate) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".parse not implemented, has_parse should be false");
   }

   protected double bwdiff(Expression expr, int childidx) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".bwdiff not implemented, has_bwdiff should be false");
   }

   protected double fwdiff(Expression expr, Solution direction) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".fwdiff not implemented, has_fwdiff should be false");
   }

   protected double bwfwdiff(Expression expr, int childidx, Solution direction) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".bwfwdiff not implemented, has_bwfwdiff should be false");
   }

   protected Interval inteval(Expression expr, ExpressionIntEvalVarCallback intevalvar) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".inteval not implemented, has_inteval should be false");
   }

   protected Estimate estimate(Expression expr, Interval[] localbounds, Interval[] globalbounds, double[] refpoint, boolean overestimate, double targetvalue) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".estimate not implemented, has_estimate should be false");
   }

   protected InitEstimate[] initestimates(Expression expr, Interval[] bounds, boolean overestimate) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".initestimates not implemented, has_initestimates should be false");
   }

   protected Interval[] reverseprop(Expression expr, Interval bounds, Interval[] childrenbounds) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".reverseprop not implemented, has_reverseprop should be false");
   }

   protected int hash(Expression expr, int[] childrenhashes) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".hash not implemented, has_hash should be false");
   }

   protected SCIP_EXPRCURV[] curvature(Expression expr, SCIP_EXPRCURV exprcurvature) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".curvature not implemented, has_curvature should be false");
   }

   protected SCIP_MONOTONE monotonicity(Expression expr, int childidx) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".monotonicity not implemented, has_monotonicity should be false");
   }

   protected boolean integrality(Expression expr) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".integrality not implemented, has_integrality should be false");
   }

   protected SymData getsymdata(Expression expr) throws Exception {
      throw new UnsupportedOperationException(getClass().getSimpleName() + ".getsymdata not implemented, has_getsymdata should be false");
   }

   protected void close() throws Exception {
      // do nothing by default
   }

   /** returns a String representation */
   public String toString()
   {
      return "expression handler of type " + getClass().getName();
   }

   /** Class wrapping an existing native SCIP_Exprhdlr. */
   public static final class Wrapper extends ExpressionHandler {
      /** constructor from a native SCIP_Exprhdlr pointer */
      public Wrapper(SWIGTYPE_p_SCIP_Exprhdlr exprhdlrptr) {
         super(exprhdlrptr);
      }

      @Override
      public double eval(Expression expr, Solution sol) throws Exception {
         // This method will never be reached from native C/C++ code.
         throw new UnsupportedOperationException(
               "ExpressionHandler.Wrapper.eval should never be called");
      }
   }

   protected static class Estimate {
      public double[] coefs;
      public double constant;
      public boolean islocal;
      public boolean branchcand;

      public Estimate(double[] coefs, double constant, boolean islocal, boolean branchcand) {
         this.coefs = coefs;
         this.constant = constant;
         this.islocal = islocal;
         this.branchcand = branchcand;
      }
   }

   protected static class InitEstimate {
      public double[] coefs;
      public double constant;

      public InitEstimate(double[] coefs, double constant) {
         this.coefs = coefs;
         this.constant = constant;
      }

      public InitEstimate() {
         this(null, 0.);
      }
   }

   protected static class SymData {
      public double[] constants;
      public double[] coefficients;
      public Expression[] children;

      public SymData(double[] constants, double[] coefficients, Expression[] children) {
         this.constants = constants;
         this.coefficients = coefficients;
         this.children = children;
      }

      public SymData() {
         this(null, null, null);
      }
   }
}
