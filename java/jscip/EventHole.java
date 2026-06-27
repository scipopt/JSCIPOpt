package jscip;

public class EventHole implements Event {

    private final HoleEventType holeEventType;
    private final Variable variable;
    private final double left;
    private final double right;

    public EventHole(HoleEventType holeEventType, Variable variable, double left, double right) {
        this.holeEventType = holeEventType;
        this.variable = variable;
        this.left = left;
        this.right = right;
    }

    public HoleEventType getHoleEventType() {
        return holeEventType;
    }

    public Variable getVariable() {
        return variable;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
