package jscip;

public class EventNode implements Event {

    private final NodeEventType nodeEventType;

    public EventNode(NodeEventType nodeEventType) {
        this.nodeEventType = nodeEventType;
    }

    public NodeEventType getNodeEventType() {
        return nodeEventType;
    }
}
