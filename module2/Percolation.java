import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.*;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private int m_n;
    private boolean grid[][];
    private int m_open_sites = 0;
    private WeightedQuickUnionUF m_uf;
    private int firstRow[];
    private boolean m_percolates;

    public Percolation(int n)
    {
      if (n<0)
        throw new IllegalArgumentException();
      this.m_n = n;
      grid = new boolean[n][n];
      m_percolates = false;
      for (int i=0;i<n;i++) {
        for (int j=0;j<n;j++) {
          grid[i][j] = false;
        }
      }
      this.m_open_sites = 0;
      this.m_uf = new WeightedQuickUnionUF(n*n);
      this.firstRow = new int[n];
    }
    private boolean validCell(int row, int col)
    {
      return ((row>=0) && (row<m_n) && (col>=0) && (col<m_n));
    }
    private int index(int row, int col)
    {
      return (row*m_n + col);
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
      if (!validCell(row, col))
        throw new IllegalArgumentException("Invalid cell");
      if (isOpen(row,col))
        return;
      grid[row][col] = true;
      m_open_sites++;
      
      // left
      if (validCell(row,col-1) && isOpen(row,col-1))
        m_uf.union(index(row,col-1), index(row,col));
      // right
      if (validCell(row,col+1) && isOpen(row,col+1))
        m_uf.union(index(row,col+1), index(row,col));
      // up
      if (validCell(row-1,col) && isOpen(row-1, col))
        m_uf.union(index(row-1,col), index(row,col));
      // down
      if (validCell(row+1,col) && isOpen(row+1, col))
        m_uf.union(index(row+1,col), index(row,col));
      
      if (m_percolates == true)
        return;
      int canon_elem = m_uf.find(index(row,col));
      if (row == 0)
        return;
      for (int i=0;i<m_n;i++) {
        int first_row_can = m_uf.find(index(0,i));
        if (first_row_can == canon_elem) {
          m_percolates = true;
          return;
        }
      }      

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
      if (!validCell(row, col))
        throw new IllegalArgumentException("Invalid cell");
      return (grid[row][col]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
      if (!validCell(row, col))
        throw new IllegalArgumentException("Invalid cell");
      return (isOpen(row,col)) ? true : false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
      return m_open_sites;
    }

    // does the system percolate?
    public boolean percolates()
    {
      return m_percolates;
    }
    
    /*private double launch(int n)
    {
      System.out.println("Grid : " + n + "x" + n);
      while (!percolates()) {
        int row = StdRandom.uniformInt(0,n);
        int col = StdRandom.uniformInt(0,n);
        if (isFull(row,col)) {
          continue;
        }
        open(row,col);
      }
      
      int sitesOpened = numberOfOpenSites();
      System.out.println("Sites opened = " + sitesOpened);
      double percolationThreshold = (sitesOpened*1.0) / ((n*n)*1.0);
      System.out.println("Percolation threshold = " + percolationThreshold);
      return percolationThreshold;
    }*/

    // test client (optional)
    public static void main(String[] args)
    {
      int n = 20;
      if (args.length > 0)
        n = Integer.parseInt(args[0]);
      System.out.println("Grid : " + n + "x" + n);
      Percolation obj = new Percolation(n);
      while (!obj.percolates()) {
        int row = StdRandom.uniformInt(0,n);
        int col = StdRandom.uniformInt(0,n);
        if (obj.isFull(row,col)) {
          continue;
        }
        obj.open(row,col);
      }
      
      int sitesOpened = obj.numberOfOpenSites();
      System.out.println("Sites opened = " + sitesOpened);
      double percolationThreshold = (sitesOpened*1.0) / ((n*n)*1.0);
      System.out.println("Percolation threshold = " + percolationThreshold);
    }


}
