package domain;

/**
 * A position on the maze.
 *
 * @author aarisbaskaran
 */

public class Position {
    private final int x;
    private final int y;

    /**
     * Construct position on the maze
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
