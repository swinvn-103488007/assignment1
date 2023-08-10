import java.awt.*;
import java.util.*;

public class GBFS {
    private Scenario scene;
    PriorityQueue<State> pq;

    boolean[][] visited;
    private int searched;
    private int discovered;

    GBFS(Scenario scene) {
        this.scene = scene;
        pq = new PriorityQueue<>();
        this.visited = new boolean[scene.getMapWidth()][scene.getMapHeight()];
        searched = 0;
        discovered = 0;
        for (boolean[] booleans : visited) {
            Arrays.fill(booleans, false);
        }
    }

    public void search(State state) {
        int[][] targetsCoord = scene.getTargetsCoord();
        ArrayList<State> goalStates = new ArrayList<>();
        for (int[] coord : targetsCoord) {
            goalStates.add(new State(null, coord[0], coord[1]));
        }


        pq.offer(state);
        State result = null;
        while (!pq.isEmpty()) {
            // Pick the best state based on the heuristic
            State currentState = pq.poll();
            visited[currentState.getX()][currentState.getY()] = true;
            searched++;
            int[] currentCoord = {currentState.getX(), currentState.getY()};

            // Check if current state is the target
            if (scene.isSolved(currentCoord)) {
                result = currentState;
                break;
            }

            // check if upper cell is movable
            if (scene.stateMovableUp(currentState)) {
                int nextX = currentState.getX();
                int nextY = currentState.getY() - 1;
                if (!visited[nextX][nextY]) {
                    State nextState = new State(currentState, nextX, nextY);
                    ArrayList<Integer> costs = new ArrayList<>();
                    for (State s : goalStates) {
                        costs.add(manhattanDistance(s, nextState));
                    }
                    int nextCost = Collections.min(costs);
                    nextState.setCost(nextCost);
                    discovered++;
                    pq.offer(nextState);
//                        System.out.println("Up cell: " + nextX + ", " + nextY + " is movable. Cost: " + nextCost);
                }
            }

            // check if left cell is movable
            if (scene.stateMovableLeft(currentState)) {
                int nextX = currentState.getX() - 1;
                int nextY = currentState.getY();
                if (!visited[nextX][nextY]) {
                    State nextState = new State(currentState, nextX, nextY);
                    ArrayList<Integer> costs = new ArrayList<>();
                    for (State s : goalStates) {
                        // cost f = h. h is manhattan distance between goal and the possible next state
                        costs.add(manhattanDistance(s, nextState));
                    }
                    int nextCost = Collections.min(costs);
                    nextState.setCost(nextCost);
                    discovered++;
                    pq.offer(nextState);
//                        System.out.println("Left cell: " + nextX + ", " + nextY + " is movable. Cost: " + nextCost);
                }
            }

            // check if down cell is movable
            if (scene.stateMovableDown(currentState)) {
                int nextX = currentState.getX();
                int nextY = currentState.getY() + 1;
                if (!visited[nextX][nextY]) {
                    State nextState = new State(currentState, nextX, nextY);
                    ArrayList<Integer> costs = new ArrayList<>();
                    for (State s : goalStates) {
                        costs.add(manhattanDistance(s, nextState));
                    }
                    int nextCost = Collections.min(costs);
                    nextState.setCost(nextCost);
                    discovered++;
                    pq.offer(nextState);
//                        System.out.println("Down cell: " + nextX + ", " + nextY + " is movable. Cost: " + nextCost);
                }
            }

            // check if right cell is movable
            if (scene.stateMovableRight(currentState)) {
                int nextX = currentState.getX() + 1;
                int nextY = currentState.getY();
                if (!visited[nextX][nextY]) {
                    State nextState = new State(currentState, nextX, nextY);
                    ArrayList<Integer> costs = new ArrayList<>();
                    for (State s : goalStates) {
                        costs.add(manhattanDistance(s, nextState));
                    }
                    int nextCost = Collections.min(costs);
                    nextState.setCost(nextCost);
                    discovered++;
                    pq.offer(nextState);
//                        System.out.println("Right cell: " + nextX + ", " + nextY + " is movable. Cost: " + nextCost);
                }
            }
        }
        scene.displaySolution("GBFS algorithm", result, searched, discovered);
    }

    private static int manhattanDistance(State a, State b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
