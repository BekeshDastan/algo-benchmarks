package org.Features;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.Arrays;

public class DeterministicSelectTest {
    @Test
    public void testMedianFixedArrays() {
        int[][] testCases = {
                {7, 1, 3, 2, 6, 4, 8, 5, 9},
                {1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 2, 3, 5, 4, 6, 7, 8, 9},
                {2, 2, 1, 3, 4, 5, 6, 7, 9}
        };

        for (int[] arr : testCases) {
            int[] original = arr.clone();
            int n = arr.length;
            if (n > 0) {
                int k = (n - 1) / 2;
                int[] expected = Arrays.copyOf(original, original.length);
                Arrays.sort(expected);
                int result = DeterministicSelect.select(arr.clone(), k);
                assertEquals(expected[k], result, "Failed median for array " + Arrays.toString(original));
            }
        }
    }

    @Test
    public void testMedianRandomArrays() {
        Random rnd = new Random(12);
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(1000) + 1;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = rnd.nextInt(10000) - 5000;
            }
            int[] original = arr.clone();
            int k = (n - 1) / 2;
            int[] expected = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            int result = DeterministicSelect.select(arr.clone(), k);
            assertEquals(expected[k], result, "Failed median for array of size " + n);
        }
    }

    @Test
    public void testEmptyArrayThrows() {
        int[] empty = {};
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(empty, 0),
                "Expected IllegalArgumentException for empty array");
    }
}
