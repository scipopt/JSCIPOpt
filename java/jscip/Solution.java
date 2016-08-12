package jscip;

/** Class representing a single solution (equivalent of SCIP_SOL).*/
public class Solution
{
   private SWIGTYPE_p_SCIP_SOL _solptr; /** pointer address class created by SWIG */

   /** default constructor */
   public Solution(SWIGTYPE_p_SCIP_SOL solptr)
   {
      _solptr = solptr;
   }

   /** returns SWIG object type representing a SCIP_SOL pointer */
   public SWIGTYPE_p_SCIP_SOL getPtr()
   {
      return _solptr;
   }

   // @todo this function should not be public
   public void setPtr(SWIGTYPE_p_SCIP_SOL solptr)
   {
      _solptr = solptr;
   }

   /** wraps SCIPsolGetDepth() */
   public int getDepth()
   {
      return SCIPJNI.SCIPsolGetDepth(_solptr);
   }

   /** wraps SCIPsolGetIndex() */
   public int getIndex()
   {
      return SCIPJNI.SCIPsolGetIndex(_solptr);
   }
}
