package jscip;

/** Minimal wrapper for a SCIP_COL pointer. */
public final class Column
{
   private final SWIGTYPE_p_SCIP_Col _colptr;

   public Column(SWIGTYPE_p_SCIP_Col colptr)
   {
      _colptr = colptr;
   }

   public SWIGTYPE_p_SCIP_Col getPtr()
   {
      return _colptr;
   }

   public Variable getVar()
   {
      assert(_colptr != null);
      SWIGTYPE_p_SCIP_VAR ptr = SCIPJNI.SCIPcolGetVar(_colptr);
      return (ptr == null) ? null : new Variable(ptr);
   }

   public int getIndex()
   {
      assert(_colptr != null);
      return SCIPJNI.SCIPcolGetIndex(_colptr);
   }

   public String getName()
   {
      Variable var = getVar();
      return (var == null) ? "<unnamed-col>" : var.getName();
   }

   @Override
   public String toString()
   {
      return getName() + "#" + getIndex();
   }
}
