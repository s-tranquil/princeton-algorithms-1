import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private class Coordinates {
        public final int x;
        public final int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class CoordinatesHelper {
        public Coordinates fromZeroBased(int x, int y) {
            return new Coordinates(x, y);
        }

        public Coordinates fromOneBased(int row, int col) {
            return new Coordinates(col - 1, row - 1);
        }

        public boolean getByCoordinates(boolean[][] currentField, Coordinates c) {
            return currentField[c.y][c.x];
        }

        public void openByCoordinates(boolean[][] currentField, Coordinates c) {
            currentField[c.y][c.x] = true;
        }
    }

    private final WeightedQuickUnionUF ids;
    private final boolean[][] field;
    private final int size;
    private final CoordinatesHelper ch;
    private final int topCellId;
    private final int bottomCellId;
    private int openedCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        openedCount = 0;
        size = n;
        ch = new CoordinatesHelper();
        int twoDimensinalSize = n * n;
        ids = new WeightedQuickUnionUF(twoDimensinalSize + 2);

        topCellId = twoDimensinalSize;
        bottomCellId = twoDimensinalSize + 1;
        int bottomIndex = twoDimensinalSize - size;
        for (int x = 0; x < n; x++) {
            ids.union(topCellId, x);
            ids.union(bottomCellId, bottomIndex + x);
        }

        field = new boolean[n][];
        for (int y = 0; y < n; y++) {
            boolean[] xx = new boolean[n];
            for (int x = 0; x < n; x++) {
                xx[x] = false;
            }
            field[y] = xx;
        }
    }

    private int getId(Coordinates coordinates) {
        return (coordinates.y) * size + coordinates.x;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        Coordinates c = ch.fromOneBased(row, col);
        if (!isOpenField(c)) {
            openedCount++;
        }
        ch.openByCoordinates(field, c);

        int currentId = getId(c);
        if (c.y >= 0 && c.y < size - 1) {
            unionAdjacent(c.x, c.y + 1, currentId);
        }
        if (c.y > 0 && c.y < size) {
            unionAdjacent(c.x, c.y - 1, currentId);
        }
        if (c.x >= 0 && c.x < size - 1) {
            unionAdjacent(c.x + 1, c.y, currentId);
        }
        if (c.x > 0 && c.x < size) {
            unionAdjacent(c.x - 1, c.y, currentId);
        }
    }

    private void unionAdjacent(int x, int y, int currentId) {
        Coordinates cAdjacent = ch.fromZeroBased(x, y);
        int adjacentId = getId(cAdjacent);

        if (isOpenField(cAdjacent)) {
            ids.union(currentId, adjacentId);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        Coordinates c = ch.fromOneBased(row, col);
        return isOpenField(c);
    }

    private boolean isOpenField(Coordinates c) {
        return ch.getByCoordinates(field, c);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        Coordinates c = ch.fromOneBased(row, col);
        return isOpenField(c) && isPercolatable(c);
    }

    private boolean isPercolatable(Coordinates c) {
        int currentId = getId(c);
        int parent = ids.find(currentId);
        boolean isTopConnected = ids.find(topCellId) == parent;
        return isTopConnected;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedCount;
    }

    // does the system percolate?
    public boolean percolates() {
        int topParent = ids.find(topCellId);
        int bottomParent = ids.find(bottomCellId);

        // corner case
        if (size == 1) {
            return isOpen(1, 1);
        }

        return topParent == bottomParent;
    }

    private String getFieldString() {
        char[] result = new char[size * size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                result[x + y * size] = field[y][x] ? '1' : '0';
            }
        }
        return new String(result);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        StdOut.println(p.getFieldString());
        p.open(1, 1);
        StdOut.println("isOpen(1,1): " + p.isOpen(1, 1));
        StdOut.println("isFull(1,1): " + p.isFull(1, 1));
        p.open(2, 1);
        StdOut.println("isOpen(2,1): " + p.isOpen(2, 1));
        StdOut.println("isFull(2,1): " + p.isFull(2, 1));
        StdOut.println(p.getFieldString());
    }
}
