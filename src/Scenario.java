import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Scenario {
    // array of cells that makes up the map
    private ArrayList<Cell> cells;

    // Array of target coordinates, each target is an array [x,y]
    private int[][] targets;
    private int mapWidth;
    private int mapHeight;

    public Scenario(ArrayList<Cell> cells, int[][] targets, int mapWidth, int mapHeight) {
        this.cells = cells;
        this.targets = targets;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public boolean isSolved(int[] currentCoord) {
        boolean isSolved = false;
        for (int[] e : this.targets) {
            if (Arrays.equals(e, currentCoord)) {
                isSolved = true;
                break;
            }
        }
        return isSolved;
    }

    public ArrayList<State> determinePossibleMoves(State currentState) {
        ArrayList<State> movableStates = new ArrayList<>();
        Cell topCell;
        Cell rightCell;
        Cell bottomCell;
        Cell leftCell;

        // Check if move top is possible
        if (currentState.getY() > 0) {
            topCell = cells.get(mapWidth * (currentState.getY() - 1) + currentState.getX());
            if (!topCell.isWall()) {
                movableStates.add(new State(currentState, currentState.getX(), currentState.getY() - 1));
            }
        }

        // Check if move left is possible
        if (currentState.getX() > 0) {
            leftCell = cells.get(mapWidth * (currentState.getY()) + currentState.getX() - 1);
            if (!leftCell.isWall()) {
                movableStates.add(new State(currentState, currentState.getX() - 1, currentState.getY()));
            }
        }

        // Check if move bottom is possible
        if (currentState.getY() < mapHeight - 1) {
            bottomCell = cells.get(mapWidth * (currentState.getY() + 1) + currentState.getX());
            if (!bottomCell.isWall()) {
                movableStates.add(new State(currentState, currentState.getX(), currentState.getY() + 1));
            }
        }

        // Check if move right is possible
        if (currentState.getX() < mapWidth - 1) {
            rightCell = cells.get(mapWidth * currentState.getY() + currentState.getX() + 1);
            if (!rightCell.isWall()) {
                movableStates.add(new State(currentState, currentState.getX() + 1, currentState.getY()));
            }
        }
        return movableStates;
    }

    public void displaySolution(String searchAlgo, State state, int searched, int discovered) {
        //Create a stack that will have each node in the solution chain added until we reach the starting node in the search
        Stack<State> stack = new Stack<>();
        System.out.println(" \"Searched: " + searched + "\"" +" \"Number of nodes in search tree: " + discovered + "\""+ " \n");
        if (state != null) {
            while (state != null) {
                stack.push(state);
                state = state.getParent();
            }
        } else {
            System.out.println("No solution found.\n");
            return;
        }

        //While the state is not null, we need to add it to the stack, and then make the state = to the states parent. This is a recursive
        //solution that will get us to the top of the search tree
        State s;
        //Now we are able to print out the solution from the beginning to the end
        while (!stack.isEmpty()) {
            s = stack.pop();
            if (s.getParent() != null) {
                System.out.print(s.comparePosition(s.getParent()) + " ");
            }
        }
        System.out.print(" finished\n");
    }

    public boolean stateMovableRight(State state) {
        Cell currentCell;
        if (state.getX() < mapWidth - 1) {
            currentCell = cells.get(mapWidth * state.getY() + state.getX() + 1);
            return !currentCell.isWall();
        }
        return false;
    }

    public boolean stateMovableLeft(State state) {
        Cell currentCell;
        if (state.getX() > 0) {
            currentCell = cells.get(mapWidth * state.getY() + state.getX() - 1);
            return !currentCell.isWall();
        }
        return false;
    }

    public boolean stateMovableUp(State state) {
        Cell currentCell;
        if (state.getY() > 0) {
            currentCell = cells.get(mapWidth * (state.getY() - 1) + state.getX());
            return !currentCell.isWall();
        }
        return false;
    }

    public boolean stateMovableDown(State state) {
        Cell currentCell;
        if (state.getY() < mapHeight - 1) {
            currentCell = cells.get(mapWidth * (state.getY() + 1) + state.getX());
            return !currentCell.isWall();
        }
        return false;
    }

    public int[][] getTargetsCoord() {
        return targets;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

}
