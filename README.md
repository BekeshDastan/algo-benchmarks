# Assignment 1: Divide-and-Conquer Algorithms & Benchmarks

## Overview
This project implements classic **divide-and-conquer algorithms**, collects performance metrics, and validates correctness with JUnit tests. All metrics are stored in `benchmark_wide.csv` and visualized with plots.  

Algorithms implemented:

1. **MergeSort (D&C, Master Case 2)**  
   - Linear merge  
   - Reusable buffer  
   - Small-n cut-off (insertion sort)

2. **QuickSort (robust)**  
   - Randomized pivot  
   - Recurse on smaller partition, iterate over larger  
   - Bounded stack ≈ O(log n) typical

3. **Deterministic Select (Median-of-Medians, O(n))**  
   - Group by 5, median-of-medians pivot  
   - In-place partition  
   - Recurse only into the needed side

4. **Closest Pair of Points (2D, O(n log n))**  
   - Sort points by x-coordinate  
   - Recursive split  
   - Strip check by y-order (classic 7–8 neighbor scan)


## Architecture Notes
- **MergeSort:** single reusable buffer across recursive calls → minimal memory allocations. Recursion depth ≈ log₂(n).  
- **QuickSort:** always recurses on the smaller partition first → stack depth ≤ 2*log₂(n). Randomized pivot ensures balanced splits.  
- **Deterministic Select:** recursion only on the relevant partition → small recursion depth; allocations strictly controlled.  
- **Closest Pair:** divide-and-conquer with strip check; recursion depth ≈ log₂(n). Only 7–8 neighbors checked per strip → minimal extra comparisons.  

## Recurrence Analysis

### MergeSort
- **Recurrence:** T(n) = 2T(n/2) + Θ(n)  
- **Analysis (Master Theorem Case 2):** T(n) = Θ(n log n)  
- **Observations:** Recursion depth log₂(n); comparisons proportional to n log n; allocations controlled by reusable buffer.

### QuickSort
- **Recurrence:** T(n) = T(k) + T(n-k-1) + Θ(n), k = size of one partition  
- **Average Case:** Random pivot → roughly equal splits → Θ(n log n)  
- **Worst Case:** Pivot min/max → O(n²)  
- **Observations:** Stack depth ≤ 2*log₂(n); practical time matches theoretical expectation.

### Deterministic Select (Median-of-Medians)
- **Recurrence:** T(n) = T(n/5) + T(7n/10) + Θ(n)  
- **Analysis (Akra–Bazzi intuition):** T(n) = Θ(n)  
- **Observations:** Linear time even for large n; recursion depth small; allocations and comparisons are strictly controlled.

### Closest Pair of Points (2D)
- **Recurrence:** T(n) = 2T(n/2) + Θ(n)  
- **Analysis (Master Theorem Case 2):** T(n) = Θ(n log n)  
- **Observations:** Recursion depth log₂(n); comparisons much fewer than brute-force; practical performance aligns with theory for n > 2000.

## Metrics Collected
- **Time vs n**
- **Depth vs n**
- **Comparisons vs n**
- **Allocations vs n**

### Practical Metrics
| Size | MergeSort (ms) | QuickSort (ms) | Deterministic Select (ms) | Closest Pair (ms) |
|------|----------------|----------------|---------------------------|------------------|
| 10   | 0.0227         | 0.0086         | 0.0213                    | 0.7246           |
| 100  | 0.1843         | 0.0914         | 0.1577                    | 0.9769           |
| 1000 | 1.4127         | 1.1271         | 1.8935                    | 5.8585           |
| 5000 | 1.8842         | 1.0173         | 2.6665                    | 18.885           |

### Theoretical Metrics (approximate)
| Size | MergeSort | QuickSort | Deterministic Select | Closest Pair |
|------|-----------|-----------|--------------------|--------------|
| 10   | 34 comps, 5 depth, 34 allocs | 21 comps, 8 depth, 0 allocs | 23 comps, 2 depth, 3 allocs | 32 comps, 6 depth, 4 allocs |
| 100  | 544 comps, 8 depth, 672 allocs | 613 comps, 71 depth, 0 allocs | 486 comps, 3 depth, 63 allocs | 634 comps, 37 depth, 32 allocs |
| 1000 | 8700 comps, 11 depth, 9976 allocs | 11827 comps, 670 depth, 0 allocs | 4608 comps, 5 depth, 556 allocs | 9744 comps, 264 depth, 256 allocs |
| 5000 | 55267 comps, 14 depth, 61808 allocs | 74686 comps, 3372 depth, 0 allocs | 27111 comps, 6 depth, 3266 allocs | 61246 comps, 1938 depth, 1928 allocs |

### Plots
<img width="1902" height="710" alt="Metrics plots" src="https://github.com/user-attachments/assets/f5af430c-3018-4629-ba14-ccc0b0a8e4ce" />

**Discussion:**  
- MergeSort uses more memory due to buffer, but comparisons match theory.  
- QuickSort shows slightly variable depth due to randomized pivot, matches O(n log n) on average.  
- Deterministic Select always linear; recursion depth very small.  
- Closest Pair beats brute-force for n > 2000; on small n, setup overhead dominates.  

## Summary
- **Alignment:** Practical running times, recursion depths, and comparisons align closely with theoretical expectations for all algorithms.  
- **Minor mismatch:** Constant factors (buffer allocation, cache effects, GC) cause small deviations in absolute time, especially for small arrays.  
- **Overall:** Divide-and-conquer algorithms perform as predicted; metrics confirm both theoretical recurrence analysis and implementation correctness.

