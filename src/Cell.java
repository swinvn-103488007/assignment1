public class Cell {
    private int x;
    private int y;
    private boolean isWall = false;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setIsWall(boolean value) {
        this.isWall = value;
    }

    public boolean isWall() {
        return this.isWall;
    }

}
