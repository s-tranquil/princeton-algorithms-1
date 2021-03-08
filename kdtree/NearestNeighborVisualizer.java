/******************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointSET.java KdTree.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {

        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);

            StdOut.println("________________");
            StdOut.println("size before: " + kdtree.size());
            kdtree.insert(p);
            StdOut.println("inserted: " + p.toString());
            StdOut.println("size after: " + kdtree.size());

            brute.insert(p);
        }


        //test 2a input5.txt
        // boolean test2aContains = kdtree.contains(new Point2D(0.87, 0.2));
        // StdOut.println("test2aContains: " + test2aContains);
        // assert (test2aContains == false);

        //test 2a input10.txt
        // boolean test2aContains = kdtree.contains(new Point2D(0.9, 0.6));
        // StdOut.println("test2aContains: " + test2aContains);
        // assert (test2aContains == true);

        //test 2c
        boolean test2cContains = kdtree.contains(new Point2D(0, 0));
        StdOut.println("test2cContains: " + test2cContains);
        assert (test2cContains == true);

        //test 1d
        // int test1DCount = kdtree.size();
        // assert (test1DCount == 3);

        Point2D n = kdtree.nearest(new Point2D(0.083, 0.510));
        StdOut.println(n.toString());
        n = kdtree.nearest(new Point2D(0.5, 0.5));
        StdOut.println(n.toString());
        n = kdtree.nearest(new Point2D(0.1, 0.1));
        StdOut.println(n.toString());

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();
            kdtree.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
