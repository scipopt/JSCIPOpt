package jscip;

public class ScipPropTiming {

    public static final int BEFORELP = 0x001;
    public static final int DURINGLPLOOP = 0x002;
    public static final int AFTERLPLOOP = 0x004;
    public static final int AFTERLPNODE = 0x008;
    public static final int ALWAYS = BEFORELP | DURINGLPLOOP | AFTERLPLOOP | AFTERLPNODE;

}
