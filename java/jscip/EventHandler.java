package jscip;

public class EventHandler {

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

    protected void scipExec(Scip scip, SCIP_Event event) {
    }

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
                    scipExec(scip, event);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }
        };
        SCIPJNI.SCIPincludeObjEventhdlr(scipptr, _objEventhdlr, 1L);
    }

}
