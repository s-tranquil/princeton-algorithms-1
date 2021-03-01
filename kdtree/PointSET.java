/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PointSET {
    private HashSet<Point2D> _points;

    public PointSET()                               // construct an empty set of points
    {
        _points = new HashSet<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return _points.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return _points.size();
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        _points.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return _points.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        _points.forEach(p -> p.draw());
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        List<Point2D> result = new ArrayList<Point2D>();
        _points.forEach(p -> {
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
        double distance = Double.POSITIVE_INFINITY;
        Point2D result = null;
        for (Point2D x : _points) {
            if (p.distanceTo(x) < distance) {
                distance = p.distanceTo(x);
                result = x;
            }
        }
        return result;
    }

    public static void main(
            String[] args)                  // unit testing of the methods (optional)
    {
    }
}
