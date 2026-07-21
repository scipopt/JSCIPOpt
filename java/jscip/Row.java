package jscip;

/** Minimal wrapper for a SCIP_ROW pointer. */
public final class Row
{
   private final SWIGTYPE_p_SCIP_Row _rowptr;

   public Row(SWIGTYPE_p_SCIP_Row rowptr)
   {
      _rowptr = rowptr;
   }

   public SWIGTYPE_p_SCIP_Row getPtr()
   {
      return _rowptr;
   }

   public String getName()
   {
      assert(_rowptr != null);
      return SCIPJNI.SCIProwGetName(_rowptr);
   }

   public int getIndex()
   {
      assert(_rowptr != null);
      return SCIPJNI.SCIProwGetIndex(_rowptr);
   }

   public double getLhs()
   {
      assert(_rowptr != null);
      return SCIPJNI.SCIProwGetLhs(_rowptr);
   }

   public double getRhs()
   {
      assert(_rowptr != null);
      return SCIPJNI.SCIProwGetRhs(_rowptr);
   }

   @Override
   public String toString()
   {
      return getName() + "#" + getIndex() + " [" + getLhs() + "," + getRhs() + "]";
   }
}
