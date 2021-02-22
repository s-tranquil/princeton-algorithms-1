/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private int[][] _tiles;
    private int _size;
    private int _hamming;
    private int _manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        _tiles = tiles;
        _size = tiles.length;

        _hamming = get_hamming();
        _manhattan = get_manhattan();
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(_size);
        for (int[] tileRow : _tiles) {
            builder.append(System.lineSeparator());
            for (int tile : tileRow) {
                builder.append(" " + tile);
            }
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return _size;
    }

    private int get_hamming() {
        int result = 0;
        int current = 0;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                current++;
                if (_tiles[i][j] != current && !(i == _size - 1 && j == _size - 1)) {
                    result++;
                }
            }
        }
        return result;
    }

    // number of tiles out of place
    public int hamming() {
        return _hamming;
    }

    private int get_manhattan() {
        int result = 0;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                int currentValue = _tiles[i][j];
                if (currentValue != 0) {
                    // getting reference positions of the current tile (where it should be eventually)
                    int refI = (int) Math.floor((double) (currentValue - 1) / _size);
                    int refJ = currentValue % _size == 0 ? _size - 1 : currentValue % _size - 1;
                    result += Math.abs(refI - i);
                    result += Math.abs(refJ - j);
                }
            }
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return _manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() + manhattan() == 0;
    }

    public boolean equals(Object y) {
        Board that = (Board) y;
        if (that._size != this._size) {
            return false;
        }

        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                if (that._tiles[i][j] != this._tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroI = 0;
        int zeroJ = 0;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                if (this._tiles[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }

        List<Board> neighbours = new ArrayList<>();
        if (zeroI - 1 >= 0) {
            neighbours.add(getNeighbour(zeroI, zeroJ, zeroI - 1, zeroJ));
        }
        if (zeroI + 1 < _size) {
            neighbours.add(getNeighbour(zeroI, zeroJ, zeroI + 1, zeroJ));
        }
        if (zeroJ - 1 >= 0) {
            neighbours.add(getNeighbour(zeroI, zeroJ, zeroI, zeroJ - 1));
        }
        if (zeroJ + 1 < _size) {
            neighbours.add(getNeighbour(zeroI, zeroJ, zeroI, zeroJ + 1));
        }

        return () -> neighbours.iterator();
    }

    private Board getNeighbour(int i, int j, int newZeroI, int newZeroJ) {
        int[][] newTiles = getTilesCopy();
        newTiles[i][j] = newTiles[newZeroI][newZeroJ];
        newTiles[newZeroI][newZeroJ] = 0;
        return new Board(newTiles);
    }

    private int[][] getTilesCopy() {
        int[][] newTiles = new int[_size][_size];
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                newTiles[i][j] = this._tiles[i][j];
            }
        }
        return newTiles;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = getTilesCopy();
        int[] a = new int[] { -1, -1 };
        int[] b = new int[] { -1, -1 };

        do {
            // randomly select a tile
            int[] coords = new int[] { StdRandom.uniform(_size - 1), StdRandom.uniform(_size - 1) };

            // dont use blank square
            if (newTiles[coords[0]][coords[1]] == 0) {
                continue;
            }

            // if "a" tile is not specified, use random coordinates
            if (a[0] == -1) {
                a = coords;
                continue;
            }

            // if "b" tile is not specified and random coordinates not equal to "a" tile
            if (b[0] == -1 && !Arrays.equals(a, coords)) {
                b = coords;
                continue;
            }
        }
        while (a[0] == -1 || b[0] == -1);

        int aValue = newTiles[a[0]][a[1]];
        newTiles[a[0]][a[1]] = newTiles[b[0]][b[1]];
        newTiles[b[0]][b[1]] = aValue;

        return new Board(newTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board b1 = new Board(new int[][] {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        });
        StdOut.println(b1.hamming());
        StdOut.println(b1.manhattan());

        Board b2 = new Board(new int[][] {
                { 0, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 1 }
        });
        StdOut.println(b2.hamming());
        StdOut.println(b2.manhattan());

        Board b3 = b1.twin();
        StdOut.println(b1.toString());
        StdOut.println(b3.toString());
    }

}
