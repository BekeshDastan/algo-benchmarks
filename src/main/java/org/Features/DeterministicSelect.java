package org.Features;

import java.util.Arrays;

public class DeterministicSelect {

    public static int comparisons = 0;
    public static int currentDepth = 0;
    public static int maxDepth = 0;
    public static int allocations = 0;

    public static int select(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Index out of bounds: " + k);
        }
        currentDepth = 0;
        maxDepth = 0;
        allocations = 0;
        comparisons = 0;

        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int left, int right, int k) {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;

        while (true) {
            if (left == right) {
                currentDepth--;
                return arr[left];
            }

            int pivotIndex = pivot(arr, left, right);
            pivotIndex = partition(arr, left, right, pivotIndex);

            comparisons++;
            if (k == pivotIndex) {
                currentDepth--;
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            comparisons++;
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static int pivot(int[] arr, int left, int right) {
        if (right - left < 5) {
            allocations++;
            Arrays.sort(arr, left, right + 1);
            return (left + right) / 2;
        }

        int subRight = left;
        for (int i = left; i <= right; i += 5) {
            int subEnd = Math.min(i + 4, right);
            allocations++;
            Arrays.sort(arr, i, subEnd + 1);
            int medianIndex = i + (subEnd - i) / 2;
            swap(arr, medianIndex, subRight);
            subRight++;
        }

        int mid = (subRight - left) / 2;
        return selectIndex(arr, left, subRight - 1, left + mid);
    }

    private static int selectIndex(int[] arr, int left, int right, int k) {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;

        while (true) {
            if (left == right) {
                currentDepth--;
                return left;
            }

            int pivotIndex = pivot(arr, left, right);
            pivotIndex = partition(arr, left, right, pivotIndex);

            comparisons++;
            if (k == pivotIndex) {
                currentDepth--;
                return k;
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        comparisons++;
    }
}
