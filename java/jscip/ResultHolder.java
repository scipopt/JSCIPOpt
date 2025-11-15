package jscip;

import java.util.Objects;

public class ResultHolder {

    private SCIP_Result value = SCIP_Result.SCIP_DIDNOTRUN;

    public void setValue(SCIP_Result value) {
        this.value = Objects.requireNonNull(value, "result");
    }

    public boolean isSet() {
        return value != SCIP_Result.SCIP_DIDNOTRUN;
    }

    public SCIP_Result getValue() {
        return value;
    }

}
