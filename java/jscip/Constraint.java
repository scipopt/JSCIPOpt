package jscip;

/** Class representing a single constraint (equivalent of SCIP_CONS).*/
public class Constraint
{
   private SWIGTYPE_p_SCIP_CONS _consptr; /** pointer address class created by SWIG */

   /** default constructor */
   public Constraint(SWIGTYPE_p_SCIP_CONS consptr)
   {
      assert(consptr != null);
      _consptr = consptr;
   }

   /** returns SWIG object type representing a SCIP_CONS pointer */
   public SWIGTYPE_p_SCIP_CONS getPtr()
   {
      return _consptr;
   }

   // @todo this function should not be public
   public void setPtr(SWIGTYPE_p_SCIP_CONS consptr)
   {
      _consptr = consptr;
   }

   /** wraps SCIPconsGetName() */
   public String getName()
   {
      assert(_consptr != null);
      return SCIPJNI.SCIPconsGetName(_consptr);
   }

   /** returns a String representation */
   public String toString()
   {
      return getName();
   }
}
