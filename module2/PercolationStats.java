import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class PercolationStats {

    private Percolation percolation[];
    private double percolation_threshold[];
    private int m_n;
    private int m_trials;
    private double m_mean;
    private double m_stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
      percolation = new Percolation[trials];
      percolation_threshold = new double[trials];
      m_n = n; 
      m_trials = trials;
      for (int i=0;i<trials;i++)
        percolation[i] = new Percolation(n);
    }

    // sample mean of percolation threshold
    public double mean()
    {
      m_mean = StdStats.mean(percolation_threshold); 
      return m_mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
      m_stddev = StdStats.stddev(percolation_threshold);
      return m_stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
      return (m_mean - (1.96*m_stddev)/Math.sqrt(m_trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
      return (m_mean + (1.96*m_stddev)/Math.sqrt(m_trials));
    }
    public void collectStats()
    {
      for (int i=0;i<m_trials;i++) {
        percolation_threshold[i] = percolation[i].launch(m_n);
      }
      mean();
      stddev();
      double low = confidenceLo();
      double high = confidenceHi(); 
      System.out.println("Mean : " + m_mean);
      System.out.println("Stddev : " + m_stddev);
      System.out.println("95% confidence interval : " + low + ", " + high);
    }

    // test client (see below)
    public static void main(String[] args)
    {
      int n = 20;
      int trials = 100;
      if (args.length == 2) {
        n = Integer.parseInt(args[0]);
        trials = Integer.parseInt(args[1]);
      }
      PercolationStats obj = new PercolationStats(n, trials);
      obj.collectStats();
    }
}
