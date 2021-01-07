/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_COEFFICIENT = 1.96;
    private final double[] sample;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        sample = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            do {
                int row = (int) Math.ceil(StdRandom.uniform() * n);
                int col = (int) Math.ceil(StdRandom.uniform() * n);
                perc.open(row, col);
            }
            while (!perc.percolates());

            int size = n * n;
            double currentThreshold = perc.numberOfOpenSites() / (double) size;
            sample[i] = currentThreshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(sample);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(sample);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - CONFIDENCE_COEFFICIENT * this.stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + CONFIDENCE_COEFFICIENT * this.stddev() / Math.sqrt(this.trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 0;
        int t = 0;
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
                t = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
            }
        }

        if (n == 0 || t == 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean\t\t\t\t\t\t = " + stats.mean());
        StdOut.println("stddev\t\t\t\t\t\t = " + stats.stddev());
        StdOut.println(
                "95%  confidence interval\t = [" +
                        stats.confidenceLo() +
                        ", " +
                        stats.confidenceHi() +
                        "]"
        );
    }

}
