import java.util.Arrays;
import java.util.Stack;

public class DFS {
    private Scenario scene;
    private Stack<State> movesTracker;

    private int searched;
    private int discovered;

    boolean[] visited;

    DFS(Scenario scene) {
        this.scene = scene;
        this.movesTracker = new Stack<>();
        // Array for checking the repeated state
        this.visited = new boolean[scene.getMapWidth() * scene.getMapHeight()];
        searched = 0;
        discovered = 0;
        Arrays.fill(visited, false);
    }

    public void search(State state) {
        movesTracker.push(state);
        State resultState = null;
        while (!movesTracker.empty()) {
            State currentState = movesTracker.pop();
            searched++;
            visited[scene.getMapWidth() * currentState.getY() + currentState.getX()] = true;
            int[] currentCoord = {currentState.getX(), currentState.getY()};
            if (scene.isSolved(currentCoord)) {
                resultState = currentState;
                break;
            }

            // Check all movable neighbor state
            // Push to the stack by order Right, Down, Left, Up
            // As the priority order is Up, Left, Down, Right and Stack mechanism is LIFO
            if (scene.stateMovableRight(currentState)) {
                State next = new State(currentState, currentState.getX() + 1, currentState.getY());
                if (!visited[scene.getMapWidth() * next.getY() + next.getX()]) {
                    movesTracker.push(next);
                    discovered++;
                }
            }

            if (scene.stateMovableDown(currentState)) {
                State next = new State(currentState, currentState.getX(), currentState.getY() + 1);
                if (!visited[scene.getMapWidth() * next.getY() + next.getX()]) {
                    movesTracker.push(next);
                    discovered++;
                }
            }

            if (scene.stateMovableLeft(currentState)) {
                State next = new State(currentState, currentState.getX() - 1, currentState.getY());
                if (!visited[scene.getMapWidth() * next.getY() + next.getX()]) {
                    movesTracker.push(next);
                    discovered++;
                }
            }
            if (scene.stateMovableUp(currentState)) {
                State next = new State(currentState, currentState.getX(), currentState.getY() - 1);
                if (!visited[scene.getMapWidth() * next.getY() + next.getX()]) {
                    movesTracker.push(next);
                    discovered++;
                }
            }
        }
        scene.displaySolution("DFS algorithm",resultState, searched, discovered);
    }
}
