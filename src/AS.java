import java.util.*;

public class AS {
    private Scenario scene;
    private PriorityQueue<State> pq;
    private int searched;
    private int discovered;

    AS(Scenario scene) {
        this.scene = scene;
        pq = new PriorityQueue<>();
        searched = 0;
        discovered = 0;
    }

    public void search(State start) {
        int[][] targetsCoord = scene.getTargetsCoord(); // 2-dimensional array of target cells
        List<State> closedSet = new ArrayList<>();
        ArrayList<State> goalStates = new ArrayList<>();
        for (int[] coord : targetsCoord) {
            goalStates.add(new State(null, coord[0], coord[1]));
        }

        // A list to pick the shortest path
        ArrayList<State> results = new ArrayList<>();

        // Set up the cost for the start state
        List<Integer> hCosts = new ArrayList<>();
        for (State goalState : goalStates) {
            hCosts.add(manhattanDistance(goalState, start));
        }
        Collections.sort(hCosts);
        int initialHCost = hCosts.get(0);
        start.setCost(0, initialHCost);
        pq.offer(start);

        while (!pq.isEmpty()) {
            // Define the best state to move to
            State current = pq.poll();
            searched++;
            int[] currentCoord = {current.getX(), current.getY()};
                if (scene.isSolved(currentCoord)) {
                    results.add(current);
                }

            // Mark a State as visited
            closedSet.add(current);
            // Check all possible move: Left, right, top or bottom
            // If a state is not moved on, add that state to the priority queue for polling in the next loop
            if (scene.stateMovableUp(current)) {
                int nextX = current.getX();
                int nextY = current.getY() - 1;
                State nextState = new State(current, nextX, nextY);
                int tentativeGCost = current.getGCost() + 1;
                nextState.setGCost(tentativeGCost);

                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                nextState.setHCost(nextStateHCost);
                int nextStateCost = tentativeGCost + nextStateHCost;

                // set actual cost for next possible state
                nextState.setCost(nextStateCost);
                if (!closedSet.contains(nextState)) {
                    pq.offer(nextState);
                    discovered++;
//                    System.out.println("Up cell: " + nextState);
                }
            }

            if (scene.stateMovableLeft(current)) {
                int nextX = current.getX() - 1;
                int nextY = current.getY();
                State nextState = new State(current, nextX, nextY);
                int tentativeGCost = current.getGCost() + 1;
                nextState.setGCost(tentativeGCost);
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                nextState.setHCost(nextStateHCost);
                int nextStateCost = tentativeGCost + nextStateHCost;
                nextState.setCost(nextStateCost);
                if (!closedSet.contains(nextState)) {
                    pq.offer(nextState);
                    discovered++;
//                    System.out.println("Left cell: " + nextState);
                }
            }

            if (scene.stateMovableDown(current)) {
                int nextX = current.getX();
                int nextY = current.getY() + 1;
                State nextState = new State(current, nextX, nextY);
                int tentativeGCost = current.getGCost() + 1;
                nextState.setGCost(tentativeGCost);
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextState.setHCost(nextStateHCost);
                int nextStateCost = tentativeGCost + nextStateHCost;
                nextState.setCost(nextStateCost);
                if (!closedSet.contains(nextState)) {
                    pq.offer(nextState);
                    discovered++;
//                    System.out.println("Down cell: " + nextState);
                }
            }

            if (scene.stateMovableRight(current)) {
                int nextX = current.getX() + 1;
                int nextY = current.getY();
                State nextState = new State(current, nextX, nextY);
                int tentativeGCost = current.getGCost() + 1;
                nextState.setGCost(tentativeGCost);
                // Calculate h = min(manhantanDistance(goal_1,current), manhantanDistance(goal_2,current), ... manhantanDistance(goal_n,current))
                int nextStateHCost = manhattanDistance(nextState, goalStates.get(0));
                for (int i = 1; i < goalStates.size(); i++) {
                    if (manhattanDistance(nextState, goalStates.get(i)) < nextStateHCost) {
                        nextStateHCost = manhattanDistance(nextState, goalStates.get(i));
                    }
                }
                nextState.setHCost(nextStateHCost);
                int nextStateCost = tentativeGCost + nextStateHCost;
                nextState.setCost(nextStateCost);
                if (!closedSet.contains(nextState)) {
                    pq.offer(nextState);
                    discovered++;
//                    System.out.println("Right cell" + nextState);
                }
            }
            // Break if there are at least one path and the smallest calculated cost of the movable > smallest found path
            if (results.size() > 0 && pq.peek().getCost() > Collections.min(results).getCost()) {
                break;
            }
        }
        State result = (results.size() > 0) ? Collections.min(results) : null;
        scene.displaySolution("A* algorithm", result, searched, discovered);

    }

    private static int manhattanDistance(State a, State b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
