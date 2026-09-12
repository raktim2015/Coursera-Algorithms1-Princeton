import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[][] grid;
    private int openSites;

    // Used for checking percolation.
    private final WeightedQuickUnionUF uf;

    // Used for checking fullness and avoiding backwash.
    private final WeightedQuickUnionUF fullUf;

    // Virtual top and bottom sites.
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.grid = new boolean[n + 1][n + 1];
        this.openSites = 0;

        // n*n actual sites + 2 virtual sites
        this.virtualTop = 0;
        this.virtualBottom = n * n + 1;

        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.fullUf = new WeightedQuickUnionUF(n * n + 1);
    }

    // Converts (row, col) into a union-find index.
    private int index(int row, int col) {
        return (row - 1) * n + col;
    }

    private boolean validCell(int row, int col) {
        return row >= 1 && row <= n &&
               col >= 1 && col <= n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell");
        }

        if (isOpen(row, col)) {
            return;
        }

        grid[row][col] = true;
        openSites++;

        int current = index(row, col);

        // Connect top row to virtual top.
        if (row == 1) {
            uf.union(current, virtualTop);
            fullUf.union(current, virtualTop);
        }

        // Connect bottom row to virtual bottom,
        // but ONLY in uf, not fullUf.
        if (row == n) {
            uf.union(current, virtualBottom);
        }

        // Connect to open neighboring sites.
        connectIfOpen(row, col, row - 1, col);
        connectIfOpen(row, col, row + 1, col);
        connectIfOpen(row, col, row, col - 1);
        connectIfOpen(row, col, row, col + 1);
    }

    private void connectIfOpen(int row, int col,
                               int neighborRow, int neighborCol) {

        if (!validCell(neighborRow, neighborCol)) {
            return;
        }

        if (!isOpen(neighborRow, neighborCol)) {
            return;
        }

        int current = index(row, col);
        int neighbor = index(neighborRow, neighborCol);

        uf.union(current, neighbor);
        fullUf.union(current, neighbor);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell");
        }

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validCell(row, col)) {
            throw new IllegalArgumentException("Invalid cell");
        }

        if (!isOpen(row, col)) {
            return false;
        }

        return fullUf.find(index(row, col))
                == fullUf.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop)
                == uf.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
