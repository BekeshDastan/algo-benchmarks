package org.Features;

import java.util.Arrays;
import java.util.Comparator;

public class Closestpair {

    public static int currentDepth = 0;
    public static int maxDepth = 0;
    public static int allocations = 0;
    public static int comparisons = 0;

    public static void resetMetrics() {
        currentDepth = 0;
        maxDepth = 0;
        allocations = 0;
        comparisons = 0;
    }

    public static double closestPair(double[][] points) {

        double[][] pts = points.clone();
        allocations++;
        Arrays.sort(pts, Comparator.comparingDouble(p -> p[0]));
        return closest(pts, 0, pts.length - 1);
    }

    private static double closest(double[][] pts, int l, int r) {
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);

        if (r - l <= 3) {
            return bruteForce(pts, l, r);
        }

        int mid = (l + r) / 2;
        double midX = pts[mid][0];

        double dLeft = closest(pts, l, mid);
        double dRight = closest(pts, mid + 1, r);
        double d = Math.min(dLeft, dRight);

        double[][] strip = new double[r - l + 1][];
        allocations++;
        int sz = 0;
        for (int i = l; i <= r; i++) {
            comparisons++;
            if (Math.abs(pts[i][0] - midX) < d) {
                strip[sz++] = pts[i];
            }
        }


        Arrays.sort(strip, 0, sz, Comparator.comparingDouble(p -> p[1]));

        for (int i = 0; i < sz; i++) {
            for (int j = i + 1; j < sz && (strip[j][1] - strip[i][1]) < d; j++) {
                comparisons++;
                d = Math.min(d, dist(strip[i], strip[j]));
            }
        }
        currentDepth--;
        return d;
    }

    private static double bruteForce(double[][] pts, int l, int r) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++) {
            for (int j = i + 1; j <= r; j++) {
                comparisons++;
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double dist(double[] a, double[] b) {
        double dx = a[0] - b[0];
        double dy = a[1] - b[1];
        return Math.sqrt(dx * dx + dy * dy);
    }


}

