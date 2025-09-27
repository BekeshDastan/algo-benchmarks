package org.Features;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

import java.util.Arrays;

public class QuickSortTest {
    @Test
    public void testFixedArrays() {
        int [][] testcases = {
                {7,1,3,2,6,4,8,5,9},
                {},
                {4},
                {1,2,3,4,5,6,7,8,9},
                {9,8,7,6,5,4,3,2,1},
                {1,2,3,5,4,6,7,8,9},
                {2,2,1,3,4,5,6,7,9},
                {5,5,5,5,5,5,5,5,5}
        };
        for(int[] arr :testcases ){
            int[] expected = Arrays.copyOf(arr, arr.length);
            Arrays.sort(expected);
            QuickSort.quickSort(arr, 0, arr.length-1);
            assertArrayEquals(expected, arr);
        }
    }
    @Test
    public void testRandom(){
        Random rnd =new Random(12);
        for(int i =0; i<100; i++){
            int n = rnd.nextInt(1000)+1;
            int[] arr = new int[n];
            for(int j=0; j<n; j++){
                arr[j]= rnd.nextInt(9999)-5000;
            }
            int[] expected= Arrays.copyOf(arr,arr.length);
            Arrays.sort(expected);
            QuickSort.quickSort(arr, 0, arr.length-1);
            assertArrayEquals(expected, arr);
        }
    }
}
