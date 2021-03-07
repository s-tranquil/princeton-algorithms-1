/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points;

    public PointSET()                               // construct an empty set of points
    {
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return points.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return points.size();
    }

    private void throwIllegal() {
        throw new IllegalArgumentException();
    }

    private void throwIfNull(Point2D p) {
        if (p == null) {
            throwIllegal();
        }
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        throwIfNull(p);
        points.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        throwIfNull(p);
        return points.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        points.forEach(p -> p.draw());
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) {
            throwIllegal();
        }

        List<Point2D> result = new ArrayList<Point2D>();
        points.forEach(p -> {
            if (rect.contains(p)) {
                result.add(p);
            }
        });
        return result;
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (isEmpty()) {
            return null;
        }
        throwIfNull(p);
        double distance = Double.POSITIVE_INFINITY;
        Point2D result = null;
        for (Point2D x : points) {
            double d = p.distanceSquaredTo(x);
            if (d < distance) {
                distance = d;
                result = x;
            }
        }
        return result;
    }

    public static void main(
            String[] args)                  // unit testing of the methods (optional)
    {
        // use Visualizers for tests
    }
}
