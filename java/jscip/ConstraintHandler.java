package jscip;

public class ConstraintHandler {

    private final String name;
    private final String description;
    private final ConstraintHandlerOptions options;
    private ObjConshdlr _objConshdlr = null;

    public ConstraintHandler(String name, String description, ConstraintHandlerOptions options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    protected SCIP_Retcode free(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode init(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode exit(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode initpre(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode exitpre(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode initsol(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode exitsol(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode delete(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode trans(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode initlp(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode sepalp(Scip scip, ResultHolder resultHolder) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode sepasol(Scip scip, Solution solution, ResultHolder resultHolder) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode enfolp(Scip scip, ResultHolder resultHolder) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode enforelax(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode enfops(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode check(Scip scip, Solution solution, ResultHolder resultHolder) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode prop(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode presol(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode resprop(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode lock(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode active(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode deactive(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode enable(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode disable(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode delvars(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode print(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode copy(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode parse(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode getvars(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode getnvars(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode getdivebdchgs(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode getpermsymgraph(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    protected SCIP_Retcode getsignedpermsymgraph(Scip scip) {
        return SCIP_Retcode.SCIP_OKAY;
    }

    public void attach(Scip jScip, SWIGTYPE_p_SCIP cPtr) {
        _objConshdlr = new ObjConshdlr(
                cPtr,
                name,
                description,
                options.sepapriority,
                options.enfopriority,
                options.checkpriority,
                options.sepafreq,
                options.propfreq,
                options.eagerfreq,
                options.maxprerounds,
                options.delaysepa,
                options.delayprop,
                options.needscons,
                options.proptiming,
                options.presoltiming
        ) {
            @Override
            public SCIP_Retcode scip_free(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr) {
                return free(jScip);
            }

            @Override
            public SCIP_Retcode scip_init(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss) {
                return init(jScip);
            }

            @Override
            public SCIP_Retcode scip_exit(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss) {
                return exit(jScip);
            }

            @Override
            public SCIP_Retcode scip_initpre(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss) {
                return initpre(jScip);
            }

            @Override
            public SCIP_Retcode scip_exitpre(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss) {
                return exitpre(jScip);
            }

            @Override
            public SCIP_Retcode scip_initsol(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss) {
                return initsol(jScip);
            }

            @Override
            public SCIP_Retcode scip_exitsol(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, long restart) {
                return exitsol(jScip);
            }

            @Override
            public SCIP_Retcode scip_delete(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_p_SCIP_CONSDATA consdata) {
                return ConstraintHandler.this.delete(jScip);
            }

            @Override
            public SCIP_Retcode scip_trans(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS sourcecons, SWIGTYPE_p_p_SCIP_CONS targetcons) {
                return trans(jScip);
            }

            @Override
            public SCIP_Retcode scip_initlp(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, SWIGTYPE_p_unsigned_int infeasible) {
                return initlp(jScip);
            }

            @Override
            public SCIP_Retcode scip_sepalp(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nusefulconss, SWIGTYPE_p_SCIP_Result result) {
                ResultHolder resultHolder = new ResultHolder();
                SCIP_Retcode retCode = sepalp(jScip, resultHolder);
                SCIPJNI.setResult(result, resultHolder.result);
                return retCode;
            }

            @Override
            public SCIP_Retcode scip_sepasol(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nusefulconss, SWIGTYPE_p_SCIP_SOL sol, SWIGTYPE_p_SCIP_Result result) {
                ResultHolder resultHolder = new ResultHolder();
                SCIP_Retcode retCode = sepasol(jScip, new Solution(sol), resultHolder);
                SCIPJNI.setResult(result, resultHolder.result);
                return retCode;
            }

            @Override
            public SCIP_Retcode scip_enfolp(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nusefulconss, long solinfeasible, SWIGTYPE_p_SCIP_Result result) {
                ResultHolder resultHolder = new ResultHolder();
                SCIP_Retcode retCode = enfolp(jScip, resultHolder);
                SCIPJNI.setResult(result, resultHolder.result);
                return retCode;
            }

            @Override
            public SCIP_Retcode scip_enforelax(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_SOL sol, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nusefulconss, long solinfeasible, SWIGTYPE_p_SCIP_Result result) {
                return enforelax(jScip);
            }

            @Override
            public SCIP_Retcode scip_enfops(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nusefulconss, long solinfeasible, long objinfeasible, SWIGTYPE_p_SCIP_Result result) {
                return enfops(jScip);
            }

            @Override
            public SCIP_Retcode scip_check(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, SWIGTYPE_p_SCIP_SOL sol, long checkintegrality, long checklprows, long printreason, long completely, SWIGTYPE_p_SCIP_Result result) {
                ResultHolder resultHolder = new ResultHolder();
                SCIP_Retcode retCode = check(jScip, new Solution(sol), resultHolder);
                SCIPJNI.setResult(result, resultHolder.result);
                return retCode;
            }

            @Override
            public SCIP_Retcode scip_prop(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nusefulconss, int nmarkedconss, SWIGTYPE_p_SCIP_PROPTIMING proptiming, SWIGTYPE_p_SCIP_Result result) {
                return prop(jScip);
            }

            @Override
            public SCIP_Retcode scip_presol(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss, int nrounds, SWIGTYPE_p_SCIP_PRESOLTIMING presoltiming, int nnewfixedvars, int nnewaggrvars, int nnewchgvartypes, int nnewchgbds, int nnewholes, int nnewdelconss, int nnewaddconss, int nnewupgdconss, int nnewchgcoefs, int nnewchgsides, SWIGTYPE_p_int nfixedvars, SWIGTYPE_p_int naggrvars, SWIGTYPE_p_int nchgvartypes, SWIGTYPE_p_int nchgbds, SWIGTYPE_p_int naddholes, SWIGTYPE_p_int ndelconss, SWIGTYPE_p_int naddconss, SWIGTYPE_p_int nupgdconss, SWIGTYPE_p_int nchgcoefs, SWIGTYPE_p_int nchgsides, SWIGTYPE_p_SCIP_Result result) {
                return presol(jScip);
            }

            @Override
            public SCIP_Retcode scip_resprop(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_SCIP_VAR infervar, int inferinfo, SCIP_BoundType boundtype, SWIGTYPE_p_SCIP_BDCHGIDX bdchgidx, double relaxedbd, SWIGTYPE_p_SCIP_Result result) {
                return resprop(jScip);
            }

            @Override
            public SCIP_Retcode scip_lock(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_SCIP_LOCKTYPE locktype, int nlockspos, int nlocksneg) {
                return lock(jScip);
            }

            @Override
            public SCIP_Retcode scip_active(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons) {
                return active(jScip);
            }

            @Override
            public SCIP_Retcode scip_deactive(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons) {
                return deactive(jScip);
            }

            @Override
            public SCIP_Retcode scip_enable(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons) {
                return enable(jScip);
            }

            @Override
            public SCIP_Retcode scip_disable(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons) {
                return disable(jScip);
            }

            @Override
            public SCIP_Retcode scip_delvars(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS conss, int nconss) {
                return delvars(jScip);
            }

            @Override
            public SCIP_Retcode scip_print(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_FILE file) {
                return print(jScip);
            }

            @Override
            public SCIP_Retcode scip_copy(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_p_SCIP_CONS cons, String name, SWIGTYPE_p_SCIP sourcescip, SWIGTYPE_p_SCIP_CONSHDLR sourceconshdlr, SWIGTYPE_p_SCIP_CONS sourcecons, SWIGTYPE_p_SCIP_HASHMAP varmap, SWIGTYPE_p_SCIP_HASHMAP consmap, long initial, long separate, long enforce, long check, long propagate, long local, long modifiable, long dynamic, long removable, long stickingatnode, long global, SWIGTYPE_p_unsigned_int valid) {
                return copy(jScip);
            }

            @Override
            public SCIP_Retcode scip_parse(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_p_SCIP_CONS cons, String name, String str, long initial, long separate, long enforce, long check, long propagate, long local, long modifiable, long dynamic, long removable, long stickingatnode, SWIGTYPE_p_unsigned_int success) {
                return parse(jScip);
            }

            @Override
            public SCIP_Retcode scip_getvars(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_p_SCIP_VAR vars, int varssize, SWIGTYPE_p_unsigned_int success) {
                return getvars(jScip);
            }

            @Override
            public SCIP_Retcode scip_getnvars(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_int nvars, SWIGTYPE_p_unsigned_int success) {
                return getnvars(jScip);
            }

            @Override
            public SCIP_Retcode scip_getdivebdchgs(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_DIVESET diveset, SWIGTYPE_p_SCIP_SOL sol, SWIGTYPE_p_unsigned_int success, SWIGTYPE_p_unsigned_int infeasible) {
                return getdivebdchgs(jScip);
            }

            @Override
            public SCIP_Retcode scip_getpermsymgraph(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_SYM_GRAPH graph, SWIGTYPE_p_unsigned_int success) {
                return getpermsymgraph(jScip);
            }

            @Override
            public SCIP_Retcode scip_getsignedpermsymgraph(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr, SWIGTYPE_p_SCIP_CONS cons, SWIGTYPE_p_SYM_GRAPH graph, SWIGTYPE_p_unsigned_int success) {
                return getsignedpermsymgraph(jScip);
            }
        };
        SCIPJNI.SCIPincludeObjConshdlr(cPtr, _objConshdlr, 1L);
        _objConshdlr.swigReleaseOwnership();
    }

}
