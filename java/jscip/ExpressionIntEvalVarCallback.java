package jscip;

/** Class representing an expression intevalvar callback and its data. */
public class ExpressionIntEvalVarCallback
{
   private final SWIGTYPE_p_SCIP _scipptr;
   private final SWIGTYPE_p_SCIP_EXPR_INTEVALVAR _funcptr; /** pointer address class created by SWIG */
   private final SWIGTYPE_p_void _dataptr; /** data pointer (as the void pointer address class created by SWIG) */

   /** default constructor */
   public ExpressionIntEvalVarCallback(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EXPR_INTEVALVAR funcptr, SWIGTYPE_p_void dataptr)
   {
      _scipptr = scipptr;
      _funcptr = funcptr;
      _dataptr = dataptr;
   }

   /** apply (invoke) this function */
   public Interval apply(Variable var)
   {
      return new Interval(SCIPJNI.invokeExprIntevalvar(_funcptr, _scipptr, var.getPtr(), _dataptr));
   }
}
