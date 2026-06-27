package jscip;

public class EventVarFixed implements Event {

    private final Variable variable;

    public EventVarFixed(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
