package jscip;

public class ConstraintHandlerOptions {

    public int sepapriority = 1000000;
    public int enfopriority = -2000000;
    public int checkpriority = -2000000;
    public int sepafreq = 1;
    public int propfreq = -1;
    public int eagerfreq = 1;
    public int maxprerounds = 0;
    public long delaysepa = 0L;
    public long delayprop = 0L;
    public long needscons = 0L;
    public int proptiming = ScipPropTiming.BEFORELP;
    public int presoltiming = ScipPresolTiming.FAST;

}
