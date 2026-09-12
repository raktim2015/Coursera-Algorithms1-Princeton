import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.*;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private int m_n;
    private boolean grid[][];
    public Percolation(int n)
    {
      if (n<0)
        throw new IllegalArgumentException();
      this.m_n = n;
      grid = new boolean[n+1][n+1];
      for (int i=0;i<=n;i++) {
        for (int j=0;j<=n;j++) {
          grid[i][j] = false;
        }
      }
    }
    public boolean validRow(int row, int col)
    {
      return ((row>0) && (row<=m_n) && (col>0) && (col<=m_n));
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
      if (!validRow(row, col))
        throw new IllegalArgumentException("Invalid cell");
      grid[row][col] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
      if (!validRow(row, col))
        throw new IllegalArgumentException("Invalid cell");
      return (grid[row][col]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
      if (!validRow(row, col))
        throw new IllegalArgumentException("Invalid cell");

      return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
      return 0;
    }

    // does the system percolate?
    public boolean percolates()
    {
      return false;
    }
    // test client (optional)
    public static void main(String[] args)
    {

    }
}
