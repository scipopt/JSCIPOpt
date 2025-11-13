package jscip;

import java.util.Objects;

public class ResultHolder {

    SCIP_Result result = SCIP_Result.SCIP_DIDNOTRUN;

    public void setResult(SCIP_Result result) {
        this.result = Objects.requireNonNull(result, "result");
    }

}
