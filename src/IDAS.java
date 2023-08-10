import java.util.*;

public class IDAS {
    private final Scenario scene;
    private final Stack<State> stack;
    private final boolean[][] visited;
    private final PriorityQueue<State> prunedState;
    private int searched;
    private int discovered;

    IDAS(Scenario scene) {
        this.scene = scene;
        stack = new Stack<>();
        visited = new boolean[scene.getMapWidth()][scene.getMapHeight()];
        prunedState = new PriorityQueue<>();
        searched = 0;
        discovered = 0;
    }

    public void search(State initialState) {
        int[][] targetsCoord = scene.getTargetsCoord();
        ArrayList<State> goalStates = new ArrayList<>();
        for (int[] coord : targetsCoord) {
            goalStates.add(new State(null, coord[0], coord[1]));
        }

        List<Integer> hCosts = new ArrayList<>();
        for (State goalState : goalStates) {
            hCosts.add(manhattanDistance(goalState, initialState));
        }
        Collections.sort(hCosts);
        int initialHCost = hCosts.get(0);
        initialState.setCost(0, initialHCost);
        // Set threshold for pruning
        int threshosld = initialState.getCost();
        stack.push(initialState);
        State result = null;

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            visited[currentState.getX()][currentState.getY()] = true;
            searched++;
            int[] currentCoord = {currentState.getX(), currentState.getY()};
            if (scene.isSolved(currentCoord)) {
                result = currentState;
                break;
            }

            // List to store State that have cost f < threshold
            ArrayList<State> possibleNextStates = new ArrayList<>();

            // Check possible moves
            // Respectively check for Up, Left, Down, Right cell.
            // The operation is same for all neighbor cell, the only difference is the coordinate of the neighbor cells
            if (scene.stateMovableRight(currentState) && !visited[currentState.getX() + 1][currentState.getY()]) {
                State nextState = new State(currentState, currentState.getX() + 1, currentState.getY());
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                nextState.setCost(currentState.getGCost() + 1, nextStateHCost);
                discovered++;
                // Check the estimate cost f = g + h
                // if f < threshold, directly add the State to the traverse stack
                // else push the State to the pruned priority queue to calculate when stack is empty
                if (nextState.getCost() < threshosld) {
                    possibleNextStates.add(nextState);
                } else {
                    prunedState.offer(nextState);
                }
            }

            if (scene.stateMovableLeft(currentState) && !visited[currentState.getX() - 1][currentState.getY()]) {
                State nextState = new State(currentState, currentState.getX() - 1, currentState.getY());
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                nextState.setCost(currentState.getGCost() + 1, nextStateHCost);
                discovered++;
                if (nextState.getCost() < threshosld) {
                    possibleNextStates.add(nextState);
                } else {
                    prunedState.offer(nextState);
                }
            }

            if (scene.stateMovableUp(currentState) && !visited[currentState.getX()][currentState.getY() - 1]) {
                State nextState = new State(currentState, currentState.getX(), currentState.getY() - 1);
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                nextState.setCost(currentState.getGCost() + 1, nextStateHCost);
                discovered++;
                if (nextState.getCost() < threshosld) {
                    possibleNextStates.add(nextState);
                } else {
                    prunedState.offer(nextState);
                }
            }

            if (scene.stateMovableDown(currentState) && !visited[currentState.getX()][currentState.getY() + 1]) {
                State nextState = new State(currentState, currentState.getX(), currentState.getY() + 1);
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                nextState.setCost(currentState.getGCost() + 1, nextStateHCost);
                discovered++;
                if (nextState.getCost() < threshosld) {
                    possibleNextStates.add(nextState);
                } else {
                    prunedState.offer(nextState);
                }
            }

            // Push next state that have cost f < threshold
            if (possibleNextStates.size() > 0) {
                Collections.sort(possibleNextStates);
                threshosld = possibleNextStates.get(0).getCost();
                for (int i = possibleNextStates.size() - 1; i >= 0; i--) {
                    stack.push(possibleNextStates.get(i));
                }
            }
            // If NO state have cost f < threshold and prunedState queue is not empty,
            // poll from the prunedState queue the State with the smallest cost
            if (stack.isEmpty() && prunedState.peek() != null) {
                stack.push(prunedState.poll());
            }
        }
        scene.displaySolution("Iterative deepening A*", result, searched, discovered);
    }

    private static int manhattanDistance(State a, State b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
