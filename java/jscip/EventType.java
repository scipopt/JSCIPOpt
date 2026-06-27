package jscip;

/**
 * See type_event.h
 */
public class EventType {

    public static final long DISABLED = 0x000000000L;

    /* variable events */
    public static final long VARADDED = 0x000000001L;
    public static final long VARDELETED = 0x000000002L;
    public static final long VARFIXED = 0x000000004L;
    public static final long VARUNLOCKED = 0x000000008L;
    public static final long OBJCHANGED = 0x000000010L;
    public static final long GLBCHANGED = 0x000000020L;
    public static final long GUBCHANGED = 0x000000040L;
    public static final long LBTIGHTENED = 0x000000080L;
    public static final long LBRELAXED = 0x000000100L;
    public static final long UBTIGHTENED = 0x000000200L;
    public static final long UBRELAXED = 0x000000400L;
    public static final long GHOLEADDED = 0x000000800L;
    public static final long GHOLEREMOVED = 0x000001000L;
    public static final long LHOLEADDED = 0x000002000L;
    public static final long LHOLEREMOVED = 0x000004000L;
    public static final long IMPLADDED = 0x000008000L;
    public static final long TYPECHANGED = 0x000010000L;

    /* presolving events */
    public static final long PRESOLVEROUND = 0x000020000L;

    /* node events */
    public static final long NODEFOCUSED = 0x000040000L;
    public static final long NODEFEASIBLE = 0x000080000L;
    public static final long NODEINFEASIBLE = 0x000100000L;
    public static final long NODEBRANCHED = 0x000200000L;
    public static final long NODEDELETE = 0x000400000L;


    /* LP events */
    public static final long FIRSTLPSOLVED = 0x000800000L;
    public static final long LPSOLVED = 0x001000000L;

    /* primal solution events */
    public static final long POORSOLFOUND = 0x002000000L;
    public static final long BESTSOLFOUND = 0x004000000L;

    /* linear row events */
    public static final long ROWADDEDSEPA = 0x008000000L;
    public static final long ROWDELETEDSEPA = 0x010000000L;
    public static final long ROWADDEDLP = 0x020000000L;
    public static final long ROWDELETEDLP = 0x040000000L;
    public static final long ROWCOEFCHANGED = 0x080000000L;
    public static final long ROWCONSTCHANGED = 0x100000000L;
    public static final long ROWSIDECHANGED = 0x200000000L;

    /* sync event */
    public static final long SYNC = 0x400000000L;

    /* event masks for variable events */
    public static final long GBDCHANGED = GLBCHANGED | GUBCHANGED;
    public static final long LBCHANGED = LBTIGHTENED | LBRELAXED;
    public static final long UBCHANGED = UBTIGHTENED | UBRELAXED;
    public static final long BOUNDTIGHTENED = LBTIGHTENED | UBTIGHTENED;
    public static final long BOUNDRELAXED = LBRELAXED | UBRELAXED;
    public static final long BOUNDCHANGED = LBCHANGED | UBCHANGED;
    public static final long GHOLECHANGED = GHOLEADDED | GHOLEREMOVED;
    public static final long LHOLECHANGED = LHOLEADDED | LHOLEREMOVED;
    public static final long HOLECHANGED = GHOLECHANGED | LHOLECHANGED;
    public static final long DOMCHANGED = BOUNDCHANGED | HOLECHANGED;
    public static final long VARCHANGED = VARFIXED
            | VARUNLOCKED
            | OBJCHANGED
            | GBDCHANGED
            | DOMCHANGED
            | IMPLADDED
            | VARDELETED
            | TYPECHANGED;
    public static final long VAREVENT = VARADDED | VARCHANGED | TYPECHANGED;

    /* event masks for node events */
    public static final long NODESOLVED = NODEFEASIBLE | NODEINFEASIBLE | NODEBRANCHED;
    public static final long NODEEVENT = NODEFOCUSED | NODESOLVED;

    /* event masks for LP events */
    public static final long LPEVENT = FIRSTLPSOLVED | LPSOLVED;

    /* event masks for primal solution events */
    public static final long SOLFOUND = POORSOLFOUND | BESTSOLFOUND;
    public static final long SOLEVENT = SOLFOUND;

    /* event masks for row events */
    public static final long ROWCHANGED = ROWCOEFCHANGED | ROWCONSTCHANGED | ROWSIDECHANGED;
    public static final long ROWEVENT = ROWADDEDSEPA | ROWDELETEDSEPA | ROWADDEDLP | ROWDELETEDLP | ROWCHANGED;

    private EventType() {
        throw new IllegalStateException("Utility class");
    }

}
