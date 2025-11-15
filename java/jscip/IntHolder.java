package jscip;

public class IntHolder {

    private int value = 0;
    private boolean set = false;

    public int getValue() {
        return value;
    }

    public boolean isSet() {
        return set;
    }

    public void setValue(int value) {
        this.value = value;
        set = true;
    }

}
