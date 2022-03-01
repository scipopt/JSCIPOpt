package jscip;

/** Class representing a single variable (equivalent of SCIP_VAR).*/
public class Variable
{
   private SWIGTYPE_p_SCIP_VAR _varptr; /** pointer address class created by SWIG */

   /** default constructor */
   public Variable(SWIGTYPE_p_SCIP_VAR varptr)
   {
      _varptr = varptr;
   }

   /** returns SWIG object type representing a SCIP_VAR pointer */
   public SWIGTYPE_p_SCIP_VAR getPtr()
   {
      return _varptr;
   }

   // @todo this function should not be public
   public void setPtr(SWIGTYPE_p_SCIP_VAR varptr)
   {
      _varptr = varptr;
   }

   /** wraps SCIPvarGetName() */
   public String getName()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetName(_varptr);
   }

   /** wraps SCIPvarGetType() */
   public SCIP_Vartype getType()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetType(_varptr);
   }

   /** wraps SCIPvarGetLbLocal() */
   public double getLbLocal()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetLbLocal(_varptr);
   }

   /** wraps SCIPvarGetUbLocal() */
   public double getUbLocal()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetUbLocal(_varptr);
   }

   /** wraps SCIPvarGetLbGlobal() */
   public double getLbGlobal()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetLbGlobal(_varptr);
   }

   /** wraps SCIPvarGetUbGlobal() */
   public double getUbGlobal()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetUbGlobal(_varptr);
   }

   /** wraps SCIPvarGetObj() */
   public double getObj()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetObj(_varptr);
   }

   /** wraps SCIPvarGetBranchPriority() */
   public int getBranchPriority()
   {
      assert(_varptr != null);
      return SCIPJNI.SCIPvarGetBranchPriority(_varptr);
   }

   /** returns a String representation */
   public String toString()
   {
      return getName() + " in [" + getLbLocal() + "," + getUbLocal() + "] obj = " + getObj() + " type = " + getType();
   }
}
