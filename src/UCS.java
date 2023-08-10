import java.util.Arrays;
import java.util.PriorityQueue;

public class UCS {
    private Scenario scene;
    private PriorityQueue<State> pq;

    boolean[][] visited;

    int searched;
    int discovered;

    UCS(Scenario scene) {
        this.scene = scene;
        // Priority queue store all path with actual travel cost
        pq = new PriorityQueue<>();
        visited = new boolean[scene.getMapWidth()][scene.getMapHeight()];
        searched = 0; discovered = 0;
        for (int i = 0; i < scene.getMapHeight(); i++) {
            Arrays.fill(visited[i], false);
        }
    }

    public void search(State initialState) {
        if (initialState != null) {
            initialState.setCost(0);
            pq.offer(initialState);
            State result = null;

            while (!pq.isEmpty()) {
                // Choose the path with the smallest actual travelled cost
                State currentState = pq.poll();
                visited[currentState.getX()][currentState.getY()] = true;
                searched++;
                int[] currentCoord = {currentState.getX(), currentState.getY()};
                if (scene.isSolved(currentCoord)) {
                    result = currentState;
                    break;
                }

                // Check all movable state by order Up, Left, Down, Right (DO check for repeated state)
                if (scene.stateMovableUp(currentState)) {
                    // The State is instantiated include the data of the actual travelled cost
                    State nextState = new State(currentState, currentState.getX(), currentState.getY() - 1, currentState.getCost() + 1);
                    if (!visited[nextState.getX()][nextState.getY()]) {
                        pq.offer(nextState);
                        discovered++;
                    }
                }

                if (scene.stateMovableLeft(currentState)) {
                    State nextState = new State(currentState, currentState.getX() - 1, currentState.getY(), currentState.getCost() + 1);
                    if (!visited[nextState.getX()][nextState.getY()]) {
                        pq.offer(nextState);
                        discovered++;
                    }
                }

                if (scene.stateMovableDown(currentState)) {
                    State nextState = new State(currentState, currentState.getX(), currentState.getY() + 1, currentState.getCost() + 1);
                    if (!visited[nextState.getX()][nextState.getY()]) {
                        pq.offer(nextState);
                        discovered++;
                    }
                }

                if (scene.stateMovableRight(currentState)) {
                    State nextState = new State(currentState, currentState.getX() + 1, currentState.getY(), currentState.getCost() + 1);
                    if (!visited[nextState.getX()][nextState.getY()]) {
                        pq.offer(nextState);
                        discovered++;
                    }
                }
            }
            scene.displaySolution("Uniform-cost search",result,searched,discovered);
        }
    }
}
