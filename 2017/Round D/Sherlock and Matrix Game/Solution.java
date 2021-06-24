import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Solution {
  static final int RANGE_SUM_LIMIT = 100_000_000;
  static final long PRODUCT_LIMIT = 10_000_000_000_000_000L;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int A1 = sc.nextInt();
      int B1 = sc.nextInt();
      int C = sc.nextInt();
      int D = sc.nextInt();
      int E1 = sc.nextInt();
      int E2 = sc.nextInt();
      int F = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, K, A1, B1, C, D, E1, E2, F)));
    }

    sc.close();
  }

  static long solve(int N, int K, int A1, int B1, int C, int D, int E1, int E2, int F) {
    int[] x = new int[N];
    int[] y = new int[N];
    x[0] = A1;
    y[0] = B1;
    for (int i = 1; i < N; ++i) {
      x[i] = (C * x[i - 1] + D * y[i - 1] + E1) % F;
      y[i] = (D * x[i - 1] + C * y[i - 1] + E2) % F;
    }

    int[] r = new int[N];
    int[] s = new int[N];
    for (int i = 1; i < N; ++i) {
      r[i] = (C * r[i - 1] + D * s[i - 1] + E1) % 2;
      s[i] = (D * r[i - 1] + C * s[i - 1] + E2) % 2;
    }

    int[] A = new int[N];
    int[] B = new int[N];
    A[0] = A1;
    B[0] = B1;
    for (int i = 1; i < N; ++i) {
      A[i] = ((r[i] == 0) ? 1 : -1) * x[i];
      B[i] = ((s[i] == 0) ? 1 : -1) * y[i];
    }

    int[] aMaxRangeSums = findMaxRangeSums(A, K);
    int[] aMinRangeSums =
        Arrays.stream(negate(findMaxRangeSums(negate(A), K)))
            .boxed()
            .sorted()
            .mapToInt(p -> p)
            .toArray();
    int[] bMaxRangeSums = findMaxRangeSums(B, K);
    int[] bMinRangeSums =
        Arrays.stream(negate(findMaxRangeSums(negate(B), K)))
            .boxed()
            .sorted()
            .mapToInt(p -> p)
            .toArray();

    long totalCount = N * (N + 1L) / 2;
    if (aMaxRangeSums.length + aMinRangeSums.length > totalCount) {
      int excess = (int) (aMaxRangeSums.length + aMinRangeSums.length - totalCount);

      aMinRangeSums =
          Arrays.copyOfRange(aMinRangeSums, 0, Math.max(0, aMinRangeSums.length - excess));
      bMinRangeSums =
          Arrays.copyOfRange(bMinRangeSums, 0, Math.max(0, bMinRangeSums.length - excess));
    }

    return computeKthMaxProduct(aMaxRangeSums, aMinRangeSums, bMaxRangeSums, bMinRangeSums, K);
  }

  static int[] negate(int[] values) {
    return Arrays.stream(values).map(x -> -x).toArray();
  }

  static int[] findMaxRangeSums(int[] values, int K) {
    int kthValue = -RANGE_SUM_LIMIT;
    int lower = -RANGE_SUM_LIMIT;
    int upper = RANGE_SUM_LIMIT;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (computeRangeSumGENum(values, middle, K) >= K) {
        kthValue = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return findGERangeSums(values, kthValue).stream().sorted().mapToInt(x -> x).toArray();
  }

  static List<Integer> findGERangeSums(int[] values, int minRangeSum) {
    int prefixSum = 0;
    NavigableMap<Integer, Integer> prefixSumToCount = new TreeMap<>();
    updateMap(prefixSumToCount, prefixSum, 1);
    for (int value : values) {
      prefixSum += value;
      updateMap(prefixSumToCount, prefixSum, 1);
    }

    List<Integer> result = new ArrayList<>();
    prefixSum = 0;
    for (int value : values) {
      updateMap(prefixSumToCount, prefixSum, -1);

      for (Entry<Integer, Integer> entry :
          prefixSumToCount.tailMap(prefixSum + minRangeSum).entrySet()) {
        for (int i = 0; i < entry.getValue(); ++i) {
          result.add(entry.getKey() - prefixSum);
        }
      }

      prefixSum += value;
    }

    return result;
  }

  static long computeRangeSumGENum(int[] values, int target, int K) {
    int prefixSum = 0;
    NavigableMap<Integer, Integer> prefixSumToCount = new TreeMap<>();
    updateMap(prefixSumToCount, prefixSum, 1);
    for (int value : values) {
      prefixSum += value;
      updateMap(prefixSumToCount, prefixSum, 1);
    }

    long result = 0;
    prefixSum = 0;
    for (int value : values) {
      updateMap(prefixSumToCount, prefixSum, -1);

      for (int count : prefixSumToCount.tailMap(prefixSum + target).values()) {
        result += count;

        if (result >= K) {
          return result;
        }
      }

      prefixSum += value;
    }

    return result;
  }

  static void updateMap(
      NavigableMap<Integer, Integer> prefixSumToCount, int prefixSum, int deltaCount) {
    prefixSumToCount.put(prefixSum, prefixSumToCount.getOrDefault(prefixSum, 0) + deltaCount);
    prefixSumToCount.remove(prefixSum, 0);
  }

  static long computeKthMaxProduct(
      int[] aMaxRangeSums, int[] aMinRangeSums, int[] bMaxRangeSums, int[] bMinRangeSums, int K) {
    int[] aRangeSums =
        IntStream.concat(Arrays.stream(aMaxRangeSums), Arrays.stream(aMinRangeSums)).toArray();

    long result = Long.MIN_VALUE;
    long lower = -PRODUCT_LIMIT;
    long upper = PRODUCT_LIMIT;
    while (lower <= upper) {
      long middle = (lower + upper) / 2;

      long count = 0;
      for (int aRangeSum : aRangeSums) {
        if (aRangeSum == 0) {
          if (middle <= 0) {
            count += bMaxRangeSums.length + bMinRangeSums.length;
          }
        } else {
          count +=
              computeGENum(aRangeSum, bMaxRangeSums, middle)
                  + computeGENum(aRangeSum, bMinRangeSums, middle);
        }
      }

      if (count >= K) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static int computeGENum(int factor, int[] sorted, long target) {
    if (factor < 0) {
      int index = -1;
      int lower = 0;
      int upper = sorted.length - 1;
      while (lower <= upper) {
        int middle = (lower + upper) / 2;
        if ((long) factor * sorted[middle] >= target) {
          index = middle;
          lower = middle + 1;
        } else {
          upper = middle - 1;
        }
      }

      return index + 1;
    } else {
      int index = sorted.length;
      int lower = 0;
      int upper = sorted.length - 1;
      while (lower <= upper) {
        int middle = (lower + upper) / 2;
        if ((long) factor * sorted[middle] >= target) {
          index = middle;
          upper = middle - 1;
        } else {
          lower = middle + 1;
        }
      }

      return sorted.length - index;
    }
  }
}
