package jscip;

public class EventVarUnlocked implements Event {

    private final Variable variable;

    public EventVarUnlocked(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
}
