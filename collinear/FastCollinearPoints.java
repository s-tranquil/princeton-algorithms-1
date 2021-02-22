/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int segmentIndex;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        segmentIndex = 0;
        segments = new LineSegment[1];

        if (points.length < 4) {
            return;
        }

        for (int i = 0; i < points.length - 3; i++) {
            // Reference point with which all other points making a slope (for sort and compare)
            Point referencePoint = points[i];
            // sorted points excluding first i points.
            // Those i points were already compared with others, we don't need them.
            Point[] pointsBySlope = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(pointsBySlope, referencePoint.slopeOrder());

            // initial segment length - 2 points
            int segmentLength = 2;
            // current point in sorted array that is comparing with reference point
            Point currentPoint = pointsBySlope[0];
            int currentPointIndex = 0;
            // slope of the current point
            double currentSlope = referencePoint.slopeTo(currentPoint);
            for (int k = 0; k < pointsBySlope.length; k++) {
                if (referencePoint.slopeTo(pointsBySlope[k]) == currentSlope) {
                    segmentLength++;

                    if (k == pointsBySlope.length - 1 && segmentLength >= 4) {
                        addSegment(referencePoint, pointsBySlope, currentPointIndex, k);
                    }
                }
                else {
                    if (segmentLength >= 4) {
                        addSegment(referencePoint, pointsBySlope, currentPointIndex, k - 1);
                    }
                    segmentLength = 2;
                    currentPoint = pointsBySlope[k];
                    currentPointIndex = k;
                    currentSlope = referencePoint.slopeTo(currentPoint);
                }
            }
        }
    }

    private void addSegment(
            Point referencePoint,
            Point[] pointsBySlope,
            int startIndex,
            int endIndex
    ) {
        Point min = referencePoint;
        Point max = referencePoint;
        for (int s = startIndex; s <= endIndex; s++) {
            if (min.compareTo(pointsBySlope[s]) > 0) {
                min = pointsBySlope[s];
            }
            if (max.compareTo(pointsBySlope[s]) < 0) {
                max = pointsBySlope[s];
            }
        }
        addSegment(min, max);
    }

    private void addSegment(Point a, Point b) {
        if (segmentIndex == segments.length) {
            LineSegment[] newSegments = new LineSegment[segments.length * 2];
            for (int i = 0; i < segments.length; i++) {
                newSegments[i] = segments[i];
            }
            segments = newSegments;
        }
        segments[segmentIndex] = new LineSegment(a, b);
        segmentIndex++;
    }

    public int numberOfSegments() {
        return segmentIndex;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segmentIndex);
    }
}
