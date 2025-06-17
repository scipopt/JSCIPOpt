package jscip;

/** Class representing an expression ownercreate callback and its data. */
public class ExpressionOwnerCreateCallback
{
   private SWIGTYPE_p_SCIP_EXPR_OWNERCREATE _funcptr; /** pointer address class created by SWIG */
   private SWIGTYPE_p_void _dataptr; /** data pointer (as the void pointer address class created by SWIG) */

   /** default constructor */
   public ExpressionOwnerCreateCallback(SWIGTYPE_p_SCIP_EXPR_OWNERCREATE funcptr, SWIGTYPE_p_void dataptr)
   {
      _funcptr = funcptr;
      _dataptr = dataptr;
   }

   /** returns SWIG object type representing a SCIP_EXPR_OWNERCREATE pointer */
   public SWIGTYPE_p_SCIP_EXPR_OWNERCREATE getFuncPtr()
   {
      return _funcptr;
   }

   // @todo this function should not be public
   public void setFuncPtr(SWIGTYPE_p_SCIP_EXPR_OWNERCREATE funcptr)
   {
      _funcptr = funcptr;
   }

   /** returns SWIG object type representing the void pointer to the data */
   public SWIGTYPE_p_void getDataPtr()
   {
      return _dataptr;
   }

   // @todo this function should not be public
   public void setDataPtr(SWIGTYPE_p_void dataptr)
   {
      _dataptr = dataptr;
   }
}
