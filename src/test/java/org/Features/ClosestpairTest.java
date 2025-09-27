package org.Features;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ClosestpairTest {

    @Test
    public void testFixedArrays() {
        double[][][] testCases = {
                {{0,0}, {3,4}, {7,7}, {1,1}},
                {{2,3}, {12,30}, {40,50}, {5,1}, {12,10}, {3,4}},
                {{0,0}, {0,1}},
                {{1,1}, {1,1}, {2,2}},
                {{-5,-5}, {5,5}, {0,0}}
        };

        double[] expectedResults = {
                Math.sqrt(2),
                Math.sqrt(2),
                1.0,
                0.0,
                Math.sqrt(50)
        };

        for (int i = 0; i < testCases.length; i++) {
            double actual = Closestpair.closestPair(testCases[i]);
            assertEquals(expectedResults[i], actual, 1e-6, "Failed case " + i);
        }
    }

    @Test
    public void testRandomArrays() {
        Random rnd = new Random(42);
        for (int t = 0; t < 50; t++) {
            int n = rnd.nextInt(500) + 2;
            double[][] pts = new double[n][2];
            for (int i = 0; i < n; i++) {
                pts[i][0] = rnd.nextDouble() * 1000 - 500; // [-500, 500]
                pts[i][1] = rnd.nextDouble() * 1000 - 500;
            }

            double expected = bruteForce(pts);
            double actual = Closestpair.closestPair(pts);
            assertEquals(expected, actual, 1e-6, "Failed random test, n=" + n);
        }
    }

    private double bruteForce(double[][] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i][0] - pts[j][0];
                double dy = pts[i][1] - pts[j][1];
                min = Math.min(min, Math.sqrt(dx*dx + dy*dy));
            }
        }
        return min;
    }
}
