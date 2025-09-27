package org.Features;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AlgoBenchCLI {
    public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 5000};
        Random rand = new Random();
        String[] algorithms = {"MergeSort", "QuickSort", "DeterministicSelect", "ClosestPair"};

        double[][] timeMs = new double[sizes.length][4];
        int[][] comparisons = new int[sizes.length][4];
        int[][] allocations = new int[sizes.length][4];
        int[][] maxDepth = new int[sizes.length][4];

        for (int si = 0; si < sizes.length; si++) {
            int n = sizes[si];

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(10000);

            MergeSort.resetMetrics();
            long msStart = System.nanoTime();
            MergeSort.mergesort(arr.clone());
            long msEnd = System.nanoTime();
            timeMs[si][0] = (msEnd - msStart) / 1_000_000.0;
            comparisons[si][0] = MergeSort.comparisons;
            allocations[si][0] = MergeSort.allocation;
            maxDepth[si][0] = MergeSort.maxDepth;

            QuickSort.resetMetrics();
            long qsStart = System.nanoTime();
            QuickSort.quickSort(arr.clone(), 0, arr.length - 1);
            long qsEnd = System.nanoTime();
            timeMs[si][1] = (qsEnd - qsStart) / 1_000_000.0;
            comparisons[si][1] = QuickSort.comparisons;
            allocations[si][1] = 0;
            maxDepth[si][1] = QuickSort.maxDepth;

            DeterministicSelect.comparisons = 0;
            DeterministicSelect.currentDepth = 0;
            DeterministicSelect.maxDepth = 0;
            DeterministicSelect.allocations = 0;
            int k = n / 2;
            long dsStart = System.nanoTime();
            int kth = DeterministicSelect.select(arr.clone(), k);
            long dsEnd = System.nanoTime();
            timeMs[si][2] = (dsEnd - dsStart) / 1_000_000.0;
            comparisons[si][2] = DeterministicSelect.comparisons;
            allocations[si][2] = DeterministicSelect.allocations;
            maxDepth[si][2] = DeterministicSelect.maxDepth;

            Closestpair.resetMetrics();
            double[][] points = new double[n][2];
            for (int i = 0; i < n; i++) {
                points[i][0] = rand.nextDouble() * 10000 - 5000;
                points[i][1] = rand.nextDouble() * 10000 - 5000;
            }
            long cpStart = System.nanoTime();
            double minDist = Closestpair.closestPair(points);
            long cpEnd = System.nanoTime();
            timeMs[si][3] = (cpEnd - cpStart) / 1_000_000.0;
            comparisons[si][3] = Closestpair.comparisons;
            allocations[si][3] = Closestpair.allocations;
            maxDepth[si][3] = Closestpair.maxDepth;
        }

        saveWideCSV(sizes, algorithms, timeMs, comparisons, allocations, maxDepth, "benchmark_wide.csv");
    }

    private static void saveWideCSV(int[] sizes, String[] algorithms,
                                    double[][] timeMs, int[][] comparisons,
                                    int[][] allocations, int[][] maxDepth,
                                    String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writeMetric(writer, "TimeMs", sizes, algorithms, timeMs);
            writeMetric(writer, "Comparisons", sizes, algorithms, comparisons);
            writeMetric(writer, "Allocations", sizes, algorithms, allocations);
            writeMetric(writer, "MaxDepth", sizes, algorithms, maxDepth);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMetric(FileWriter writer, String metric, int[] sizes, String[] algorithms,
                                    double[][] values) throws IOException {
        writer.write(metric + "\n");
        writer.write("Size");
        for (String alg : algorithms) writer.write("," + alg);
        writer.write("\n");
        for (int i = 0; i < sizes.length; i++) {
            writer.write(Integer.toString(sizes[i]));
            for (int j = 0; j < algorithms.length; j++) writer.write("," + values[i][j]);
            writer.write("\n");
        }
        writer.write("\n");
    }

    private static void writeMetric(FileWriter writer, String metric, int[] sizes, String[] algorithms,
                                    int[][] values) throws IOException {
        writer.write(metric + "\n");
        writer.write("Size");
        for (String alg : algorithms) writer.write("," + alg);
        writer.write("\n");
        for (int i = 0; i < sizes.length; i++) {
            writer.write(Integer.toString(sizes[i]));
            for (int j = 0; j < algorithms.length; j++) writer.write("," + values[i][j]);
            writer.write("\n");
        }
        writer.write("\n");
    }
}
