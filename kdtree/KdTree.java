/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D point, RectHV rectangle) {
            this.p = point;
            this.rect = rectangle;
        }
    }

    private Node root;
    private int nodesCount;

    public KdTree()                               // construct an empty set of points
    {
        root = null;
        nodesCount = 0;
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return nodesCount == 0;
    }

    public int size()                         // number of points in the set
    {
        return nodesCount;
    }

    private void put(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, point, true, new RectHV(0, 0, 1, 1));
    }

    private Node put(Node x, Point2D point, boolean isVertical, RectHV rect) {
        if (x == null) {
            nodesCount++;
            return new Node(point, rect);
        }

        if (point.equals(x.p)) {
            return x;
        }

        int cmp = compareDimension(point, x.p, isVertical);
        if (cmp < 0) {
            if (x.lb != null) {
                x.lb = put(x.lb, point, !isVertical, x.lb.rect);
            }

            // lowering the upper bound
            RectHV r = isVertical
                       ? new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y())
                       : new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax());
            x.lb = put(x.lb, point, !isVertical, r);
        }
        else {
            if (x.rt != null) {
                x.rt = put(x.rt, point, !isVertical, x.rt.rect);
            }

            // raising the lower bound
            RectHV r = isVertical
                       ? new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax())
                       : new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            x.rt = put(x.rt, point, !isVertical, r);
        }

        return x;
    }

    private int compareDimension(Point2D a, Point2D b, boolean isVertical) {
        return isVertical
               ? Double.compare(a.y(), b.y())
               : Double.compare(a.x(), b.x());
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        put(p);
    }

    private Node get(Point2D key) {
        return get(root, key, true);
    }

    private Node get(Node x, Point2D key, boolean isVertical) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }

        if (x == null) {
            return null;
        }

        int cmp = compareDimension(x.p, key, isVertical);
        if (cmp < 0) {
            return get(x.lb, key, !isVertical);
        }
        else if (cmp > 0) {
            return get(x.rt, key, !isVertical);
        }
        return x;
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return get(p) != null;
    }

    public void draw()                         // draw all points to standard draw
    {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        StdDraw.setPenRadius();
        node.rect.draw();

        if (node.rt != null) {
            draw(node.rt, !isVertical);
        }
        if (node.lb != null) {
            draw(node.lb, !isVertical);
        }
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        return range(rect, root, new ArrayList<Point2D>());
    }

    private Iterable<Point2D> range(RectHV rect, Node node, ArrayList<Point2D> acc) {
        if (rect.contains(node.p)) {
            acc.add(node.p);
        }

        if (node.rt != null && node.rt.rect.intersects(rect)) {
            range(rect, node.rt, acc);
        }

        if (node.lb != null && node.lb.rect.intersects(rect)) {
            range(rect, node.lb, acc);
        }

        return acc;
    }

    private Point2D nearest(Node node, Point2D p, Point2D currentBest, double currentDistance) {
        if (node.p.equals(p)) {
            return node.p;
        }

        double distance = node.p.distanceSquaredTo(p);
        if (distance < currentDistance) {
            currentDistance = distance;
            currentBest = node.p;
        }

        double lb = node.lb != null ? node.lb.rect.distanceSquaredTo(p) : Double.POSITIVE_INFINITY;
        double rt = node.rt != null ? node.rt.rect.distanceSquaredTo(p) : Double.POSITIVE_INFINITY;

        Point2D left = null;
        Point2D right = null;
        if (lb < currentDistance) {
            left = nearest(node.lb, p, currentBest, currentDistance);
        }
        if (rt < currentDistance) {
            right = nearest(node.rt, p, currentBest, currentDistance);
        }

        if (left != null && right != null) {
            return left.distanceSquaredTo(p) < right.distanceSquaredTo(p)
                   ? left
                   : right;
        }
        else if (left == null ^ right == null) {
            return left == null ? right : left;
        }

        return currentBest;
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, null, Double.POSITIVE_INFINITY);
    }

    public static void main(String[] args) {

    }
}
