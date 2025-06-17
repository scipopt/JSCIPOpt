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

   /** gets the data object associated with an expression created by a Java
       expression handler (WARNING: if the expression was not created by a Java
       expression handler, the behavior of this method is undefined, i.e., it
       is likely to crash your application or to return a garbage object that
       will end up crashing your application later) */
   public Object getData()
   {
      SCIP_EXPRDATA data = SCIPJNI.SCIPexprGetData(_exprptr);
      return data != null ? data.getJobj() : null;
   }

   /** wraps SCIPexprGetNChildren */
   public int getNChildren()
   {
      return SCIPJNI.SCIPexprGetNChildren(_exprptr);
   }

   /** wraps SCIPexprGetChildren */
   public Expression[] getChildren()
   {
      SWIGTYPE_p_p_SCIP_EXPR res = SCIPJNI.SCIPexprGetChildren(_exprptr);
      if (res == null) {
         return null;
      }
      int n = getNChildren();
      Expression[] children = new Expression[n];
      for (int i = 0; i < n; i++) {
         children[i] = new Expression(SCIPJNI.SCIP_EXPR_array_getitem(res, i));
      }
      return children;
   }

   /** wraps SCIPexprGetEvalValue */
   public double getEvalValue()
   {
      return SCIPJNI.SCIPexprGetEvalValue(_exprptr);
   }

   /** wraps SCIPexprGetDot */
   public double getDot()
   {
      return SCIPJNI.SCIPexprGetDot(_exprptr);
   }

   /** wraps SCIPexprIsIntegral */
   public boolean isIntegral()
   {
      return SCIPJNI.SCIPexprIsIntegral(_exprptr) != 0;
   }

   /** wraps SCIPexprGetActivity */
   public Interval getActivity()
   {
      return new Interval(SCIPJNI.SCIPexprGetActivity(_exprptr));
   }
}
