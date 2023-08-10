public class State implements Comparable<State> {
    //The x coordinate of the robot
    private final int x;

    //The y coordinate of the robot
    private final int y;

    //Stores a pointer to the node that this is a child of
    private final State parent;

    private int gCost;
    private int hCost;
    private int cost;

    /**
     * Constructor for objects of class State
     */
    public State(State parent, int x, int y) {
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for objects of class State
     */
    public State(State parent, int x, int y, int cost) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    @Override
    public String toString() {
        String parent = this.parent != null
                        ? "Parent: " + "(" + this.parent.getX() + ", " + this.parent.getY() + ")"
                        : "Parent: null";
        return "State (" + parent + ", " + x + ", " + y + ", " + cost + ")";
    }

    @Override
    public int compareTo(State cmpState) {
        return Integer.compare(this.cost, cmpState.getCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        State other = (State) obj;
        return this.x == other.x && this.y == other.y;
    }

    public State getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int value) {
        this.cost = value;
    }

    public void setCost(int gCost, int hCost) {
        setGCost(gCost);
        setHCost(hCost);
        this.cost = gCost + hCost;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int value) {
        this.gCost = value;
    }

    public int getHCost() {
        return this.hCost;
    }

    public void setHCost(int value) {
        this.hCost = value;
    }

    public String comparePosition(State cmpState) {
        if (this.getX() == cmpState.getX() && this.getY() == cmpState.getY() + 1) {
            return "down";
        } else if (this.getX() == cmpState.getX() && this.getY() == cmpState.getY() - 1) {
            return "up";
        } else if (this.getX() == cmpState.getX() + 1 && this.getY() == cmpState.getY()) {
            return "right";
        } else if (this.getX() == cmpState.getX() - 1 && this.getY() == cmpState.getY()) {
            return "left";
        } else {
            return "not adjacent";
        }
    }
}