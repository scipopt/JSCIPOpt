package jscip;

public class ScipPresolTiming {
    
    public static final int NONE = 0x002;
    public static final int FAST = 0x004;
    public static final int MEDIUM = 0x008;
    public static final int EXHAUSTIVE = 0x010;
    public static final int FINAL = 0x020;
    public static final int ALWAYS = FAST | MEDIUM | EXHAUSTIVE;
    public static final int MAX = FAST | MEDIUM | EXHAUSTIVE | FINAL;

}
