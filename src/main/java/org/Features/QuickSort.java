package org.Features;

public class QuickSort {
    public static int comparisons = 0;
    public static int currentDepth = 0;
    public static int maxDepth = 0;
    public static int allocations = 0;

    public static void resetMetrics() {
        comparisons = 0;
        maxDepth = 0;
        allocations = 0;
        currentDepth = 0;
    }


    public static void quickSort(int[] arr, int start, int end) {
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
        currentDepth++;



        if(start>=end) return;
        int pivot = partition(arr, start,end);
        quickSort(arr,start,pivot-1);
        quickSort(arr,pivot+1,end);
        currentDepth--;
    }

    public static int  partition (int[] arr, int start, int end) {
        int pivot = arr[end];
        int i = start -1;
        for (int j = start; j < end; j++) {
            comparisons++;
            if(arr[j]<pivot) {

                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = temp;

        return ++i;
    }

}
