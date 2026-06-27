package jscip;

public class EventBdChg implements Event {

    private final Variable variable;
    private final BoundChangeType boundChangeType;
    private final double oldBound;
    private final double newBound;

    public EventBdChg(Variable variable, BoundChangeType boundChangeType, double oldBound, double newBound) {
        this.variable = variable;
        this.boundChangeType = boundChangeType;
        this.oldBound = oldBound;
        this.newBound = newBound;
    }

    public Variable getVariable() {
        return variable;
    }

    public BoundChangeType getBoundType() {
        return boundChangeType;
    }

    public double getOldBound() {
        return oldBound;
    }

    public double getNewBound() {
        return newBound;
    }
}
