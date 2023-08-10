import java.util.ArrayList;
import java.util.LinkedList;

public class BFS {
    private Scenario scene;
    private LinkedList<State> frontier;
    private int searched;
    private int discovered;

    BFS(Scenario scene) {
        this.scene = scene;
        frontier = new LinkedList<>();
        searched = 0;
        discovered = 0;
    }

    public void search(State initial) {
        frontier.add(initial);
        State result = null;
        while (!frontier.isEmpty()) {
            State current = frontier.removeFirst();
            searched++;
            int[] currentCoord = {current.getX(), current.getY()};
            if (scene.isSolved(currentCoord)) {
                result = current;
                break;
            }
            addMovesToFrontier(scene.determinePossibleMoves(current));
        }
        scene.displaySolution("BFS algorithm", result, searched, discovered);
    }

    public void addMovesToFrontier(ArrayList<State> possibleMoves) {
        frontier.addAll(possibleMoves);
        discovered++;
    }
}
