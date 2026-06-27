package jscip;

public class EventSolution implements Event {

    private final SolutionEventType solutionEventType;
    private final Solution solution;

    public EventSolution(SolutionEventType solutionEventType, Solution solution) {
        this.solutionEventType = solutionEventType;
        this.solution = solution;
    }

    public SolutionEventType getSolutionEventType() {
        return solutionEventType;
    }

    public Solution getSolution() {
        return solution;
    }
}
