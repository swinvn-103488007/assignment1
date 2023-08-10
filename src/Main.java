import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public Scenario scene = null;
    public State initialState = null;

    public static void main(String[] args) {
        /**
         * @param args -
         *        args[0] - The filename of the file we want to read from
         *        args[1] - The search method to perform
         */
        new Main(args[0], args[1]);
    }

    public Main(String filePath, String inputMethod) {
        try {
            readMapFile(filePath);
            String method = switch (inputMethod.toLowerCase()) {
                case "bfs" -> "Breadth First Search";
                case "dfs" -> "Depth First Search";
                case "gbfs" -> "Greedy Best First Search";
                case "as" -> "A* Search";
                case "ucs" -> "Uniform-Cost Search";
                case "idas" -> "Iterative Deepening A* Search";
                default -> "No method found";
            };
            if (method.equals("No method found")) {
                System.out.print("File: " + filePath + " Method: Search method not found");
            } else {
                System.out.print("\n\"File:" + filePath + "\"" + " \"Method: " + method + "\"");
                switch (inputMethod.toLowerCase()) {
                    case "bfs" -> runBFS();
                    case "dfs" -> runDFS();
                    case "gbfs" -> runGBFS();
                    case "as" -> runAS();
                    case "ucs" -> runUCS();
                    case "idas" -> runIDAS();
                }
            }
//            runDFS();
//            runGBFS();
//            runAS();
//            runUCS();
//            runIDAS();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void readMapFile(String fileName) throws IOException {
        ArrayList<Cell> cells = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;

        // Read the size of the n x m grid
        line = in.readLine();
        line = line.substring(1, line.length() - 1);
        String[] contents = line.split(",");
        int mapWidth = Integer.parseInt(contents[1]); // width of the grid map
        int mapHeight = Integer.parseInt(contents[0]); // height of the grid map
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                Cell cell = new Cell(j, i);
                cells.add(cell);
            }
        }

        // Read the initial state of the robot
        line = in.readLine();
        line = line.substring(1, line.length() - 1);
        contents = line.split(",");
        this.initialState = new State(null, Integer.parseInt(contents[0]), Integer.parseInt(contents[1]));

        // Read the target for the robot
        line = in.readLine();
        contents = line.split("\\|");
        int[][] targets = new int[contents.length][2];
        for (int i = 0; i < targets.length; i++) {
            contents[i] = contents[i].substring(contents[i].indexOf("(") + 1, contents[i].indexOf(")"));
            String[] targetsDetail = contents[i].split(",");
            int[] target = new int[2];
            target[0] = Integer.parseInt(targetsDetail[0]);
            target[1] = Integer.parseInt(targetsDetail[1]);
            targets[i] = target;
        }

        // Mark the cells which are wall
        while ((line = in.readLine()) != null) {
            line = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            String[] wallDetails = line.split(",");
            int wallTopXCoord = Integer.parseInt(wallDetails[0]);
            int wallTopYCoord = Integer.parseInt(wallDetails[1]);
            int wallWidth = Integer.parseInt(wallDetails[2]);
            int wallHeight = Integer.parseInt(wallDetails[3]);

            // loops to set the wall for the map
            for (int i = 0; i < wallHeight; i++) {
                for (int j = 0; j < wallWidth; j++) {
                    cells.get((mapWidth) * (wallTopYCoord + i) + (wallTopXCoord + j)).setIsWall(true);
                }
            }
        }
        in.close();

//        Debug message
//        for (int[] t : targets) {
//            System.out.println(t[0] + ", " + t[1]);
//        }
//        for (Cell c : cells) {
//            if (c.isWall()) {
//                System.out.println("Cell(" + c.getX() + ", " + c.getY() + ") is wall");
//            }
//        }
//        System.out.println(initialState.getX() + ", " + initialState.getY());
//        Scenario scene = new Scenario(cells, targets, mapWidth, mapHeight);
//        ArrayList<State> testPossibleMove = scene.determinePossibleMoves(initialState);
//        for (State s : testPossibleMove) {
//            System.out.println(s.getX() + ", "  + s.getY());
//        }
        this.scene = new Scenario(cells, targets, mapWidth, mapHeight);
    }

    public void runBFS() {
        BFS bfs = new BFS(scene);
        bfs.search(initialState);
    }

    public void runDFS() {
        DFS dfs = new DFS(scene);
        dfs.search(initialState);
    }

    public void runGBFS() {
        GBFS gbfs = new GBFS(scene);
        gbfs.search(initialState);
    }

    public void runAS() {
        AS as = new AS(scene);
        as.search(initialState);
    }

    public void runUCS() {
        UCS ucs = new UCS(scene);
        ucs.search(initialState);
    }

    public void runIDAS() {
        IDAS idas = new IDAS(scene);
        idas.search(initialState);
    }
}


