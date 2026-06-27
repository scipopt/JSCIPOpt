package jscip;

public class EventLp implements Event {

    private final LpEventType lpEventType;

    public EventLp(LpEventType lpEventType) {
        this.lpEventType = lpEventType;
    }

    public LpEventType getLpEventType() {
        return lpEventType;
    }
}
