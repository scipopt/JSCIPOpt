package jscip;

public class EventImplAdd implements Event {

    private final Variable variable;

    public EventImplAdd(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
