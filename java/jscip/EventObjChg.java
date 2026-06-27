package jscip;

public class EventObjChg implements Event {

    private final Variable variable;
    private final double oldObjective;
    private final double newObjective;

    public EventObjChg(Variable variable, double oldObjective, double newObjective) {
        this.variable = variable;
        this.oldObjective = oldObjective;
        this.newObjective = newObjective;
    }

    public Variable getVariable() {
        return variable;
    }

    public double getOldObjective() {
        return oldObjective;
    }

    public double getNewObjective() {
        return newObjective;
    }
}
