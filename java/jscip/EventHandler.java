package jscip;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class EventHandler {

    private static final Map<Long, Function<SCIP_Event, Event>> EVENT_BUILDER;

    private static EventBdChg buildBdChangedEvent(SCIP_Event event, BoundChangeType boundChangeType) {
        SCIP_EventBdChg scipEvent = SCIPJNI.getEventDataBdChg(event);
        return new EventBdChg(new Variable(scipEvent.getVar()), boundChangeType, scipEvent.getOldbound(), scipEvent.getNewbound());
    }

    private static EventHole buildHoleEvent(SCIP_Event event, HoleEventType holeEventType) {
        SCIP_EventHole scipEvent = SCIPJNI.getEventDataHole(event);
        return new EventHole(holeEventType, new Variable(scipEvent.getVar()), scipEvent.getLeft(), scipEvent.getRight());
    }

    private static EventTypeChg buildTypeChangedEvent(SCIP_Event event) {
        SCIP_EventTypeChg scipEvent = SCIPJNI.getEventDataTypeChg(event);
        return new EventTypeChg(new Variable(scipEvent.getVar()), scipEvent.getOldtype(), scipEvent.getNewtype());
    }

    private static EventNode buildNodeEvent(SCIP_Event event, NodeEventType nodeEventType) {
        // SWIGTYPE_p_SCIP_NODE scipNode = SCIPJNI.getEventDataNode(ev); // Not yet implemented.
        return new EventNode(nodeEventType);
    }

    private static EventSolution buildSolutionEvent(SCIP_Event event, SolutionEventType solutionEventType) {
        return new EventSolution(solutionEventType, new Solution(SCIPJNI.getEventDataSolution(event)));
    }

    static {
        // Java 8 does not support switch statements over long. Using this map as a replacement.
        Map<Long, Function<SCIP_Event, Event>> builderMap = new HashMap<>();
        builderMap.put(EventType.VARADDED, ev -> new EventVarAdded(new Variable(SCIPJNI.getEventDataVarAdde(ev).getVar())));
        builderMap.put(EventType.VARDELETED, ev -> new EventVarDeleted(new Variable(SCIPJNI.getEventDataVarDeleted(ev).getVar())));
        builderMap.put(EventType.VARFIXED, ev -> new EventVarFixed(new Variable(SCIPJNI.getEventDataVarFixed(ev).getVar())));
        builderMap.put(EventType.VARUNLOCKED, ev -> new EventVarUnlocked(new Variable(SCIPJNI.getEventDataVarUnlocked(ev).getVar())));
        builderMap.put(EventType.GLBCHANGED, ev -> buildBdChangedEvent(ev, BoundChangeType.GLB_CHANGED));
        builderMap.put(EventType.GUBCHANGED, ev -> buildBdChangedEvent(ev, BoundChangeType.GUB_CHANGED));
        builderMap.put(EventType.LBTIGHTENED, ev -> buildBdChangedEvent(ev, BoundChangeType.LB_TIGHTENED));
        builderMap.put(EventType.LBRELAXED, ev -> buildBdChangedEvent(ev, BoundChangeType.LB_RELAXED));
        builderMap.put(EventType.UBTIGHTENED, ev -> buildBdChangedEvent(ev, BoundChangeType.UB_TIGHTENED));
        builderMap.put(EventType.UBRELAXED, ev -> buildBdChangedEvent(ev, BoundChangeType.UB_RELAXED));
        builderMap.put(EventType.GHOLEADDED, ev -> buildHoleEvent(ev, HoleEventType.G_HOLE_ADDED));
        builderMap.put(EventType.GHOLEREMOVED, ev -> buildHoleEvent(ev, HoleEventType.G_HOLE_REMOVED));
        builderMap.put(EventType.LHOLEADDED, ev -> buildHoleEvent(ev, HoleEventType.L_HOLE_ADDED));
        builderMap.put(EventType.LHOLEREMOVED, ev -> buildHoleEvent(ev, HoleEventType.L_HOLE_REMOVED));
        builderMap.put(EventType.IMPLADDED, ev -> new EventImplAdd(new Variable(SCIPJNI.getEventDataImplAdd(ev).getVar())));
        builderMap.put(EventType.TYPECHANGED, EventHandler::buildTypeChangedEvent);
        builderMap.put(EventType.PRESOLVEROUND, ev -> new EventPresolveRound());
        builderMap.put(EventType.NODEFOCUSED, ev -> buildNodeEvent(ev, NodeEventType.FOCUSED));
        builderMap.put(EventType.NODEFEASIBLE, ev -> buildNodeEvent(ev, NodeEventType.FEASIBLE));
        builderMap.put(EventType.NODEINFEASIBLE, ev -> buildNodeEvent(ev, NodeEventType.INFEASIBLE));
        builderMap.put(EventType.NODEBRANCHED, ev -> buildNodeEvent(ev, NodeEventType.BRANCHED));
        builderMap.put(EventType.NODEDELETE, ev -> buildNodeEvent(ev, NodeEventType.DELETE));
        builderMap.put(EventType.FIRSTLPSOLVED, ev -> new EventLp(LpEventType.FIRST_LP_SOLVED));
        builderMap.put(EventType.LPSOLVED, ev -> new EventLp(LpEventType.LP_SOLVED));
        builderMap.put(EventType.POORSOLFOUND, ev -> buildSolutionEvent(ev, SolutionEventType.POOR_SOL_FOUND));
        builderMap.put(EventType.BESTSOLFOUND, ev -> buildSolutionEvent(ev, SolutionEventType.BEST_SOL_FOUND));
        builderMap.put(EventType.ROWADDEDSEPA, ev -> new EventRowAddedSepa());
        builderMap.put(EventType.ROWDELETEDSEPA, ev -> new EventRowDeletedSepa());
        builderMap.put(EventType.ROWADDEDLP, ev -> new EventRowAddedLp());
        builderMap.put(EventType.ROWDELETEDLP, ev -> new EventRowDeletedSepa());
        builderMap.put(EventType.ROWCOEFCHANGED, ev -> new EventRowCoefChanged());
        builderMap.put(EventType.ROWCONSTCHANGED, ev -> new EventRowConstChanged());
        builderMap.put(EventType.ROWSIDECHANGED, ev -> new EventRowSideChanged());
        builderMap.put(EventType.SYNC, ev -> new EventSync());

        EVENT_BUILDER = Collections.unmodifiableMap(builderMap);
    }

    private final String name;
    private final String description;
    private final long events;
    private ObjEventhdlr _objEventhdlr = null;

    public EventHandler(String name, String description, long events) {
        this.name = name;
        this.description = description;
        this.events = events;
    }

    protected void scipFree(Scip scip) {
    }

    protected void scipInit(Scip scip) {
    }

    protected void scipExit(Scip scip) {
    }

    protected void scipInitsol(Scip scip) {
    }

    protected void scipExitsol(Scip scip) {
    }

    protected void scipDelete(Scip scip) {
    }

    protected abstract void scipExec(Scip scip, Event event);

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    void attach(Scip scip, SWIGTYPE_p_SCIP scipptr) {
        this._objEventhdlr = new ObjEventhdlr(scipptr, name, description) {
            @Override
            public SCIP_Retcode scip_free(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                try {
                    scipFree(scip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_init(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                try {
                    SCIPJNI.SCIPcatchEvent(
                            scipptr,
                            events,
                            eventhdlr,
                            null,
                            null
                    );
                    scipInit(scip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_exit(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                try {
                    scipExit(scip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_initsol(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                try {
                    scipInitsol(scip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_exitsol(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                try {
                    scipExitsol(scip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_delete(
                    SWIGTYPE_p_SCIP scipptr,
                    SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr,
                    SWIGTYPE_p_p_SCIP_EVENTDATA eventdata
            ) {
                try {
                    scipDelete(scip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_exec(
                    SWIGTYPE_p_SCIP scipptr,
                    SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr,
                    SCIP_Event event,
                    SWIGTYPE_p_SCIP_EVENTDATA eventdata
            ) {
                try {
                    scipExec(scip, buildEvent(event));
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }
        };
        SCIPJNI.SCIPincludeObjEventhdlr(scipptr, _objEventhdlr, 1L);
    }

    private Event buildEvent(SCIP_Event event) {
        Function<SCIP_Event, Event> eventMapper = EVENT_BUILDER.get(event.getEventtype());
        if (eventMapper == null) {
            throw new IllegalStateException("Unknown event type <" + event.getEventtype() + ">.");
        }
        return eventMapper.apply(event);
    }

}
