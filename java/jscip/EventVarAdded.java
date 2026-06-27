package jscip;

public class EventVarAdded implements Event {

    private final Variable variable;

    public EventVarAdded(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
