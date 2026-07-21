package jscip;

/** Wrapper around a SCIP_EVENT pointer. */
public final class Event
{
   private final SWIGTYPE_p_SCIP_Event _eventptr;

   public Event(SWIGTYPE_p_SCIP_Event eventptr)
   {
      if( eventptr == null )
         throw new NullPointerException("event pointer must not be null");
      _eventptr = eventptr;
   }

   private void require(long mask, String operation)
   {
      if( !matches(mask) )
         throw new UnsupportedOperationException(
               operation + " is not available for event type " + getType());
   }

   public SWIGTYPE_p_SCIP_Event getPtr()
   {
      return _eventptr;
   }

   public long getType()
   {
      assert(_eventptr != null);
      return SCIPJNI.SCIPeventGetType(_eventptr);
   }

   public boolean matches(long mask)
   {
      return EventMask.matches(getType(), mask);
   }

   public Variable getVar()
   {
      require(EventMask.VAR_EVENT, "getVar");
      SWIGTYPE_p_SCIP_VAR ptr = SCIPJNI.SCIPeventGetVar(_eventptr);
      return (ptr == null) ? null : new Variable(ptr);
   }

   public double getOldObj()
   {
      require(EventMask.OBJ_CHANGED, "getOldObj");
      return SCIPJNI.SCIPeventGetOldobj(_eventptr);
   }

   public double getNewObj()
   {
      require(EventMask.OBJ_CHANGED, "getNewObj");
      return SCIPJNI.SCIPeventGetNewobj(_eventptr);
   }

   public double getOldBound()
   {
      require(EventMask.GBD_CHANGED | EventMask.BOUND_CHANGED, "getOldBound");
      return SCIPJNI.SCIPeventGetOldbound(_eventptr);
   }

   public double getNewBound()
   {
      require(EventMask.GBD_CHANGED | EventMask.BOUND_CHANGED, "getNewBound");
      return SCIPJNI.SCIPeventGetNewbound(_eventptr);
   }

   public SCIP_Vartype getOldType()
   {
      require(EventMask.TYPE_CHANGED, "getOldType");
      return SCIPJNI.SCIPeventGetOldtype(_eventptr);
   }

   public SCIP_Vartype getNewType()
   {
      require(EventMask.TYPE_CHANGED, "getNewType");
      return SCIPJNI.SCIPeventGetNewtype(_eventptr);
   }

   public Node getNode()
   {
      require(EventMask.NODE_EVENT | EventMask.NODE_DELETE | EventMask.LP_EVENT, "getNode");
      SWIGTYPE_p_SCIP_Node ptr = SCIPJNI.SCIPeventGetNode(_eventptr);
      return (ptr == null) ? null : new Node(ptr);
   }

   public Solution getSol()
   {
      require(EventMask.SOL_EVENT, "getSol");
      SWIGTYPE_p_SCIP_SOL ptr = SCIPJNI.SCIPeventGetSol(_eventptr);
      return (ptr == null) ? null : new Solution(ptr);
   }

   public double getHoleLeft()
   {
      require(EventMask.HOLE_CHANGED, "getHoleLeft");
      return SCIPJNI.SCIPeventGetHoleLeft(_eventptr);
   }

   public double getHoleRight()
   {
      require(EventMask.HOLE_CHANGED, "getHoleRight");
      return SCIPJNI.SCIPeventGetHoleRight(_eventptr);
   }

   public Row getRow()
   {
      require(EventMask.ROW_EVENT, "getRow");
      SWIGTYPE_p_SCIP_Row ptr = SCIPJNI.SCIPeventGetRow(_eventptr);
      return (ptr == null) ? null : new Row(ptr);
   }

   public Column getRowCol()
   {
      require(EventMask.ROW_COEF_CHANGED, "getRowCol");
      SWIGTYPE_p_SCIP_Col ptr = SCIPJNI.SCIPeventGetRowCol(_eventptr);
      return (ptr == null) ? null : new Column(ptr);
   }

   public double getRowOldCoefVal()
   {
      require(EventMask.ROW_COEF_CHANGED, "getRowOldCoefVal");
      return SCIPJNI.SCIPeventGetRowOldCoefVal(_eventptr);
   }

   public double getRowNewCoefVal()
   {
      require(EventMask.ROW_COEF_CHANGED, "getRowNewCoefVal");
      return SCIPJNI.SCIPeventGetRowNewCoefVal(_eventptr);
   }

   public double getRowOldConstVal()
   {
      require(EventMask.ROW_CONST_CHANGED, "getRowOldConstVal");
      return SCIPJNI.SCIPeventGetRowOldConstVal(_eventptr);
   }

   public double getRowNewConstVal()
   {
      require(EventMask.ROW_CONST_CHANGED, "getRowNewConstVal");
      return SCIPJNI.SCIPeventGetRowNewConstVal(_eventptr);
   }

   public SCIP_SideType getRowSide()
   {
      require(EventMask.ROW_SIDE_CHANGED, "getRowSide");
      return SCIPJNI.SCIPeventGetRowSide(_eventptr);
   }

   public double getRowOldSideVal()
   {
      require(EventMask.ROW_SIDE_CHANGED, "getRowOldSideVal");
      return SCIPJNI.SCIPeventGetRowOldSideVal(_eventptr);
   }

   public double getRowNewSideVal()
   {
      require(EventMask.ROW_SIDE_CHANGED, "getRowNewSideVal");
      return SCIPJNI.SCIPeventGetRowNewSideVal(_eventptr);
   }

   @Override
   public String toString()
   {
      return "event[type=" + getType() + "]";
   }
}
