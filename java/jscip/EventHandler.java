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

    protected SCIP_Retcode scipFree(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode scipInit(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode scipExit(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode scipInitsol(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode scipExitsol(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode scipDelete(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode scipExec(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
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
                return scipFree(scip);
            }

            @Override
            public SCIP_Retcode scip_init(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                SCIPJNI.SCIPcatchEvent(
                        scipptr,
                        events,
                        eventhdlr,
                        null,
                        null
                );
                return scipInit(scip);
            }

            @Override
            public SCIP_Retcode scip_exit(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                return scipExit(scip);
            }

            @Override
            public SCIP_Retcode scip_initsol(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                return scipInitsol(scip);
            }

            @Override
            public SCIP_Retcode scip_exitsol(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr) {
                return scipExitsol(scip);
            }

            @Override
            public SCIP_Retcode scip_delete(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr, SWIGTYPE_p_p_SCIP_EVENTDATA eventdata) {
                return scipDelete(scip);
            }

            @Override
            public SCIP_Retcode scip_exec(SWIGTYPE_p_SCIP scipptr, SWIGTYPE_p_SCIP_EVENTHDLR eventhdlr, SWIGTYPE_p_SCIP_EVENT event, SWIGTYPE_p_SCIP_EVENTDATA eventdata) {
                return scipExec(scip);
            }
        };
        SCIPJNI.SCIPincludeObjEventhdlr(scipptr, _objEventhdlr, 1L);
        //_objEventhdlr.swigReleaseOwnership();
    }

}
