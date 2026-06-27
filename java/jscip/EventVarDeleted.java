package jscip;

public class EventVarDeleted implements Event {

    private final Variable variable;

    public EventVarDeleted(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
