package jscip;

/** Class representing a single expression (equivalent of SCIP_EXPR).*/
public class Expression
{
   private SWIGTYPE_p_SCIP_EXPR _exprptr; /** pointer address class created by SWIG */

   /** default constructor */
   public Expression(SWIGTYPE_p_SCIP_EXPR exprptr)
   {
      assert(exprptr != null);
      _exprptr = exprptr;
   }

   /** returns SWIG object type representing a SCIP_EXPR pointer */
   public SWIGTYPE_p_SCIP_EXPR getPtr()
   {
      return _exprptr;
   }

   // @todo this function should not be public
   public void setPtr(SWIGTYPE_p_SCIP_EXPR exprptr)
   {
      _exprptr = exprptr;
   }
}
