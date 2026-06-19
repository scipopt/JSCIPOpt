package jscip;

public class ConstraintHandler {

    private final String name;
    private final String description;
    private final int sepapriority;
    private final int enfopriority;
    private final int checkpriority;
    private final int sepafreq;
    private final int propfreq;
    private final int eagerfreq;
    private final int maxprerounds;
    private final long delaysepa;
    private final long delayprop;
    private final long needscons;
    private final long proptiming;
    private final long presoltiming;
    private ObjConshdlr _objConshdlr = null;

    /**
     * For parameter descriptions check objconshdlr.h
     *
     * @param name
     * @param description
     * @param sepapriority
     * @param enfopriority
     * @param checkpriority
     * @param sepafreq
     * @param propfreq
     * @param eagerfreq
     * @param maxprerounds
     * @param delaysepa
     * @param delayprop
     * @param needscons
     * @param proptiming
     * @param presoltiming
     */
    public ConstraintHandler(
            String name,
            String description,
            int sepapriority,
            int enfopriority,
            int checkpriority,
            int sepafreq,
            int propfreq,
            int eagerfreq,
            int maxprerounds,
            long delaysepa,
            long delayprop,
            long needscons,
            long proptiming,
            long presoltiming
    ) {
        this.name = name;
        this.description = description;
        this.sepapriority = sepapriority;
        this.enfopriority = enfopriority;
        this.checkpriority = checkpriority;
        this.sepafreq = sepafreq;
        this.propfreq = propfreq;
        this.eagerfreq = eagerfreq;
        this.maxprerounds = maxprerounds;
        this.delaysepa = delaysepa;
        this.delayprop = delayprop;
        this.needscons = needscons;
        this.proptiming = proptiming;
        this.presoltiming = presoltiming;
    }

    protected void free(Scip scip) {
    }

    protected void init(Scip scip) {
    }

    protected void exit(Scip scip) {
    }

    protected void initpre(Scip scip) {
    }

    protected void exitpre(Scip scip) {
    }

    protected void initsol(Scip scip) {
    }

    protected void exitsol(Scip scip, long restart) {
    }

    protected void delete(Scip scip) {
    }

    protected void trans(Scip scip) {
    }

    protected Integer initlp(Scip scip) {
        return 0;
    }

    protected SCIP_Result sepalp(Scip scip) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result sepasol(Scip scip, Solution solution) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result enfolp(Scip scip) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result enforelax(Scip scip) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result enfops(Scip scip, long solinfeasible, long objinfeasible) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result check(
            Scip scip,
            Solution solution,
            long checkintegrality,
            long checklprows,
            long printreason,
            long completely
    ) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result prop(Scip scip) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result presol(Scip scip) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected SCIP_Result resprop(Scip scip) {
        return SCIP_Result.SCIP_DIDNOTRUN;
    }

    protected void lock(Scip scip, SCIP_LockType locktype, int nlocksneg, int nlockspos) {
    }

    protected void active(Scip scip) {
    }

    protected void deactive(Scip scip) {
    }

    protected void enable(Scip scip) {
    }

    protected void disable(Scip scip) {
    }

    protected void delvars(Scip scip) {
    }

    protected void print(Scip scip) {
    }

    protected Integer copy(Scip scip) {
        return null;
    }

    protected Integer parse(Scip scip) {
        return null;
    }

    protected Integer getvars(Scip scip) {
        return null;
    }

    protected int getnvars(Scip scip) {
        return 0;
    }

    protected GetDiveBdChgsResult getdivebdchgs(
            Scip scip,
            Solution solution
    ) {
        return null;
    }

    protected Integer getpermsymgraph(Scip scip) {
        return null;
    }

    protected Integer getsignedpermsymgraph(Scip scip) {
        return null;
    }

    public void attach(Scip jScip, SWIGTYPE_p_SCIP cPtr) {
        _objConshdlr = new ObjConshdlr(
                cPtr,
                name,
                description,
                sepapriority,
                enfopriority,
                checkpriority,
                sepafreq,
                propfreq,
                eagerfreq,
                maxprerounds,
                delaysepa,
                delayprop,
                needscons,
                proptiming,
                presoltiming
        ) {
            @Override
            public SCIP_Retcode scip_free(SWIGTYPE_p_SCIP scip, SWIGTYPE_p_SCIP_CONSHDLR conshdlr) {
                try {
                    free(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_init(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss
            ) {
                try {
                    init(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_exit(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss
            ) {
                try {
                    exit(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception ex) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_initpre(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss
            ) {
                try {
                    initpre(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_exitpre(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss
            ) {
                try {
                    exitpre(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_initsol(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss
            ) {
                try {
                    initsol(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_exitsol(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    long restart
            ) {
                try {
                    exitsol(jScip, restart);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_delete(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_p_SCIP_CONSDATA consdata
            ) {
                try {
                    ConstraintHandler.this.delete(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_trans(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS sourcecons,
                    SWIGTYPE_p_p_SCIP_CONS targetcons
            ) {
                try {
                    trans(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_initlp(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    SWIGTYPE_p_unsigned_int infeasible
            ) {
                try {
                    Integer infeasibleResult = initlp(jScip);
                    if (infeasibleResult != null) {
                        SCIPJNI.unsigned_int_array_setitem(infeasible, 0, infeasibleResult);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_sepalp(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nusefulconss,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = sepalp(jScip);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_sepasol(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nusefulconss,
                    SWIGTYPE_p_SCIP_SOL sol,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = sepasol(jScip, new Solution(sol));
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_enfolp(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nusefulconss,
                    long solinfeasible,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = enfolp(jScip);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_enforelax(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_SOL sol,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nusefulconss,
                    long solinfeasible,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = enforelax(jScip);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_enfops(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nusefulconss,
                    long solinfeasible,
                    long objinfeasible,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = enfops(jScip, solinfeasible, objinfeasible);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_check(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    SWIGTYPE_p_SCIP_SOL sol,
                    long checkintegrality,
                    long checklprows,
                    long printreason,
                    long completely,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = check(
                            jScip,
                            new Solution(sol),
                            checkintegrality,
                            checklprows,
                            printreason,
                            completely
                    );
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_prop(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nusefulconss,
                    int nmarkedconss,
                    SWIGTYPE_p_SCIP_PROPTIMING proptiming,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = prop(jScip);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_presol(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss,
                    int nrounds,
                    SWIGTYPE_p_SCIP_PRESOLTIMING presoltiming,
                    int nnewfixedvars,
                    int nnewaggrvars,
                    int nnewchgvartypes,
                    int nnewchgbds,
                    int nnewholes,
                    int nnewdelconss,
                    int nnewaddconss,
                    int nnewupgdconss,
                    int nnewchgcoefs,
                    int nnewchgsides,
                    SWIGTYPE_p_int nfixedvars,
                    SWIGTYPE_p_int naggrvars,
                    SWIGTYPE_p_int nchgvartypes,
                    SWIGTYPE_p_int nchgbds,
                    SWIGTYPE_p_int naddholes,
                    SWIGTYPE_p_int ndelconss,
                    SWIGTYPE_p_int naddconss,
                    SWIGTYPE_p_int nupgdconss,
                    SWIGTYPE_p_int nchgcoefs,
                    SWIGTYPE_p_int nchgsides,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = presol(jScip);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_resprop(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_SCIP_VAR infervar,
                    int inferinfo,
                    SCIP_BoundType boundtype,
                    SWIGTYPE_p_SCIP_BDCHGIDX bdchgidx,
                    double relaxedbd,
                    SWIGTYPE_p_SCIP_Result result
            ) {
                try {
                    SCIP_Result resultValue = resprop(jScip);
                    SCIPJNI.setResult(result, resultValue);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_lock(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SCIP_LockType locktype,
                    int nlockspos,
                    int nlocksneg
            ) {
                try {
                    lock(jScip, locktype, nlocksneg, nlockspos);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_active(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons
            ) {
                try {
                    active(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_deactive(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons
            ) {
                try {
                    deactive(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_enable(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons
            ) {
                try {
                    enable(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_disable(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons
            ) {
                try {
                    disable(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_delvars(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS conss,
                    int nconss
            ) {
                try {
                    delvars(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_print(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_FILE file
            ) {
                try {
                    print(jScip);
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_copy(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_p_SCIP_CONS cons,
                    String name,
                    SWIGTYPE_p_SCIP sourcescip,
                    SWIGTYPE_p_SCIP_CONSHDLR sourceconshdlr,
                    SWIGTYPE_p_SCIP_CONS sourcecons,
                    SWIGTYPE_p_SCIP_HASHMAP varmap,
                    SWIGTYPE_p_SCIP_HASHMAP consmap,
                    long initial,
                    long separate,
                    long enforce,
                    long check,
                    long propagate,
                    long local,
                    long modifiable,
                    long dynamic,
                    long removable,
                    long stickingatnode,
                    long global,
                    SWIGTYPE_p_unsigned_int valid
            ) {
                try {
                    Integer isValid = copy(jScip);
                    if (isValid != null) {
                        SCIPJNI.unsigned_int_array_setitem(valid, 0, isValid);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_parse(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_p_SCIP_CONS cons,
                    String name,
                    String str,
                    long initial,
                    long separate,
                    long enforce,
                    long check,
                    long propagate,
                    long local,
                    long modifiable,
                    long dynamic,
                    long removable,
                    long stickingatnode,
                    SWIGTYPE_p_unsigned_int success
            ) {
                try {
                    Integer isSuccess = parse(jScip);
                    if (isSuccess != null) {
                        SCIPJNI.unsigned_int_array_setitem(success, 0, isSuccess);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_getvars(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_p_SCIP_VAR vars,
                    int varssize,
                    SWIGTYPE_p_unsigned_int success
            ) {
                try {
                    Integer isSuccess = getvars(jScip);
                    if (isSuccess != null) {
                        SCIPJNI.unsigned_int_array_setitem(success, 0, isSuccess);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_getnvars(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_int nvars,
                    SWIGTYPE_p_unsigned_int success
            ) {
                try {
                    int result = getnvars(jScip);
                    if (result >= 0) {
                        SCIPJNI.unsigned_int_array_setitem(success, 0, 1);
                        SCIPJNI.int_array_setitem(nvars, 0, result);
                    } else {
                        SCIPJNI.unsigned_int_array_setitem(success, 0, 0);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_getdivebdchgs(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_DIVESET diveset,
                    SWIGTYPE_p_SCIP_SOL sol,
                    SWIGTYPE_p_unsigned_int success,
                    SWIGTYPE_p_unsigned_int infeasible
            ) {
                try {
                    Solution solution = new Solution(sol);
                    GetDiveBdChgsResult result = getdivebdchgs(jScip, solution);
                    if (result == null) {
                        return SCIP_Retcode.SCIP_OKAY;
                    }
                    switch(result) {
                        case INFEASIBLE:
                            SCIPJNI.unsigned_int_array_setitem(success, 0, 1);
                            SCIPJNI.unsigned_int_array_setitem(infeasible, 0, 1);
                        case FEASIBLE:
                            SCIPJNI.unsigned_int_array_setitem(success, 0, 1);
                            SCIPJNI.unsigned_int_array_setitem(infeasible, 0, 0);
                        case ERROR:
                            SCIPJNI.unsigned_int_array_setitem(success, 0, 0);
                            return SCIP_Retcode.SCIP_ERROR;
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_getpermsymgraph(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_SYM_GRAPH graph,
                    SWIGTYPE_p_unsigned_int success
            ) {
                try {
                    Integer isSuccess = getpermsymgraph(jScip);
                    if (isSuccess != null) {
                        SCIPJNI.unsigned_int_array_setitem(success, 0, isSuccess);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }

            @Override
            public SCIP_Retcode scip_getsignedpermsymgraph(
                    SWIGTYPE_p_SCIP scip,
                    SWIGTYPE_p_SCIP_CONSHDLR conshdlr,
                    SWIGTYPE_p_SCIP_CONS cons,
                    SWIGTYPE_p_SYM_GRAPH graph,
                    SWIGTYPE_p_unsigned_int success
            ) {
                try {
                    Integer isSuccess = getsignedpermsymgraph(jScip);
                    if (isSuccess != null) {
                        SCIPJNI.unsigned_int_array_setitem(success, 0, isSuccess);
                    }
                    return SCIP_Retcode.SCIP_OKAY;
                } catch (Exception e) {
                    return SCIP_Retcode.SCIP_ERROR;
                }
            }
        };
        SCIPJNI.SCIPincludeObjConshdlr(cPtr, _objConshdlr, 1L);
        _objConshdlr.swigReleaseOwnership();
    }

}
