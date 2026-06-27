package jscip;

public class EventTypeChg implements Event {

    private final Variable variable;
    private final SCIP_Vartype oldType;
    private final SCIP_Vartype newType;

    public EventTypeChg(Variable variable, SCIP_Vartype oldType, SCIP_Vartype newType) {
        this.variable = variable;
        this.oldType = oldType;
        this.newType = newType;
    }

    public Variable getVariable() {
        return variable;
    }

    public SCIP_Vartype getOldType() {
        return oldType;
    }

    public SCIP_Vartype getNewType() {
        return newType;
    }
}
