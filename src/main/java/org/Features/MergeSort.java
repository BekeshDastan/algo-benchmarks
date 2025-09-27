package org.Features;

public class MergeSort {
    static int currentDepth = 0;
    static int maxDepth = 0;
    static int allocation = 0;
    static int comparisons = 0;

    public static void resetMetrics() {
        comparisons = 0;
        maxDepth = 0;
        allocation = 0;
        currentDepth = 0;
    }

    public static void mergesort(int[] arr) {
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);
        int len = arr.length;
        if (len == 0 || len == 1) {
            currentDepth--;
            return;
        }
        int mid = len / 2;
        int[] left = new int[mid];
        allocation += left.length;
        int[] right = new int[len - mid];
        allocation += right.length;
        int i = 0;
        int j=0;
        for (; i < len; i++) {
            if (i < mid) {
                left[i] = arr[i];
            } else {
                right[j] = arr[i];
                j++;
            }
        }
        mergesort(left);
        mergesort(right);
        merge(left, right, arr);
        currentDepth--;

    }


    public static void merge(int[] leftArray, int[] rightArray, int[] array) {
        int leftLen = array.length / 2;
        int rightLen = array.length - leftLen;
        int i = 0, l = 0, r = 0;
        while (l < leftLen && r < rightLen) {
            comparisons++;
            if(leftArray[l] < rightArray[r]) {
                array[i] = leftArray[l];
                l++;
                i++;
            } else {
                array[i] = rightArray[r];
                r++;
                i++;
            }
        }
        while (l < leftLen) {
            array[i] = leftArray[l];
            i++;
            l++;
        }
        while (r < rightLen) {
            array[i] = rightArray[r];
            i++;
            r++;
        }
    }
}

