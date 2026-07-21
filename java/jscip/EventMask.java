package jscip;

/** Full SCIP event mask set, aligned with SCIP_EVENTTYPE and russcip's EventMask. */
public final class EventMask
{
   public static final long DISABLED         = 0x000000000L;

   public static final long VAR_ADDED        = 0x000000001L;
   public static final long VAR_DELETED      = 0x000000002L;
   public static final long VAR_FIXED        = 0x000000004L;
   public static final long VAR_UNLOCKED     = 0x000000008L;
   public static final long OBJ_CHANGED      = 0x000000010L;
   public static final long GLB_CHANGED      = 0x000000020L;
   public static final long GUB_CHANGED      = 0x000000040L;
   public static final long LB_TIGHTENED     = 0x000000080L;
   public static final long LB_RELAXED       = 0x000000100L;
   public static final long UB_TIGHTENED     = 0x000000200L;
   public static final long UB_RELAXED       = 0x000000400L;
   public static final long GHOLE_ADDED      = 0x000000800L;
   public static final long GHOLE_REMOVED    = 0x000001000L;
   public static final long LHOLE_ADDED      = 0x000002000L;
   public static final long LHOLE_REMOVED    = 0x000004000L;
   public static final long IMPL_ADDED       = 0x000008000L;
   public static final long TYPE_CHANGED     = 0x000010000L;

   public static final long PRESOLVE_ROUND   = 0x000020000L;

   public static final long NODE_FOCUSED     = 0x000040000L;
   public static final long NODE_FEASIBLE    = 0x000080000L;
   public static final long NODE_INFEASIBLE  = 0x000100000L;
   public static final long NODE_BRANCHED    = 0x000200000L;
   public static final long NODE_DELETE      = 0x000400000L;

   public static final long FIRST_LP_SOLVED  = 0x000800000L;
   public static final long LP_SOLVED        = 0x001000000L;

   public static final long POOR_SOL_FOUND   = 0x002000000L;
   public static final long BEST_SOL_FOUND   = 0x004000000L;

   public static final long ROW_ADDED_SEPA   = 0x008000000L;
   public static final long ROW_DELETED_SEPA = 0x010000000L;
   public static final long ROW_ADDED_LP     = 0x020000000L;
   public static final long ROW_DELETED_LP   = 0x040000000L;
   public static final long ROW_COEF_CHANGED = 0x080000000L;
   public static final long ROW_CONST_CHANGED = 0x100000000L;
   public static final long ROW_SIDE_CHANGED = 0x200000000L;

   public static final long SYNC             = 0x400000000L;

   public static final long GBD_CHANGED      = GLB_CHANGED | GUB_CHANGED;
   public static final long LB_CHANGED       = LB_TIGHTENED | LB_RELAXED;
   public static final long UB_CHANGED       = UB_TIGHTENED | UB_RELAXED;
   public static final long BOUND_TIGHTENED  = LB_TIGHTENED | UB_TIGHTENED;
   public static final long BOUND_RELAXED    = LB_RELAXED | UB_RELAXED;
   public static final long BOUND_CHANGED    = LB_CHANGED | UB_CHANGED;
   public static final long GHOLE_CHANGED    = GHOLE_ADDED | GHOLE_REMOVED;
   public static final long LHOLE_CHANGED    = LHOLE_ADDED | LHOLE_REMOVED;
   public static final long HOLE_CHANGED     = GHOLE_CHANGED | LHOLE_CHANGED;
   public static final long DOM_CHANGED      = BOUND_CHANGED | HOLE_CHANGED;
   public static final long VAR_CHANGED      = VAR_FIXED | VAR_UNLOCKED | OBJ_CHANGED | GBD_CHANGED | DOM_CHANGED | IMPL_ADDED | VAR_DELETED | TYPE_CHANGED;
   public static final long VAR_EVENT        = VAR_ADDED | VAR_CHANGED | TYPE_CHANGED;

   public static final long NODE_SOLVED      = NODE_FEASIBLE | NODE_INFEASIBLE | NODE_BRANCHED;
   public static final long NODE_EVENT       = NODE_FOCUSED | NODE_SOLVED;

   public static final long LP_EVENT         = FIRST_LP_SOLVED | LP_SOLVED;

   public static final long SOL_FOUND        = POOR_SOL_FOUND | BEST_SOL_FOUND;
   public static final long SOL_EVENT        = SOL_FOUND;

   public static final long ROW_CHANGED      = ROW_COEF_CHANGED | ROW_CONST_CHANGED | ROW_SIDE_CHANGED;
   public static final long ROW_EVENT        = ROW_ADDED_SEPA | ROW_DELETED_SEPA | ROW_ADDED_LP | ROW_DELETED_LP | ROW_CHANGED;

   private EventMask()
   {
   }

   public static boolean matches(long eventtype, long mask)
   {
      return (eventtype & mask) != 0L;
   }

   public static long combine(long... masks)
   {
      long combined = 0L;

      for( long mask : masks )
         combined |= mask;

      return combined;
   }
}
