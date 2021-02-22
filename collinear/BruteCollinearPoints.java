import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int segmentIndex;

    public BruteCollinearPoints(Point[] points) {
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

        for (int x1 = 0; x1 < points.length - 3; x1++) {
            for (int x2 = x1 + 1; x2 < points.length - 2; x2++) {
                for (int x3 = x2 + 1; x3 < points.length - 1; x3++) {
                    for (int x4 = x3 + 1; x4 < points.length; x4++) {
                        Point[] currentSegment = new Point[] {
                                points[x1], points[x2], points[x3], points[x4]
                        };

                        double slope12 = currentSegment[0].slopeTo(currentSegment[1]);
                        double slope13 = currentSegment[0].slopeTo(currentSegment[2]);
                        double slope14 = currentSegment[0].slopeTo(currentSegment[3]);

                        if (slope12 == slope13 && slope12 == slope14) {
                            Point min = currentSegment[0];
                            Point max = currentSegment[0];
                            for (int k = 1; k < 4; k++) {
                                if (min.compareTo(currentSegment[k]) > 0) {
                                    min = currentSegment[k];
                                }
                                if (max.compareTo(currentSegment[k]) < 0) {
                                    max = currentSegment[k];
                                }
                            }

                            addSegment(min, max);
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points

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
    }        // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segmentIndex);
    }                // the line segments
}
