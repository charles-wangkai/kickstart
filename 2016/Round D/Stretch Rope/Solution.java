// https://blog.csdn.net/sf_zhang26/article/details/55806417

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int L = sc.nextInt();
      int[] A = new int[N];
      int[] B = new int[N];
      int[] P = new int[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
        P[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, B, P, M, L)));
    }

    sc.close();
  }

  static String solve(int[] A, int[] B, int[] P, int M, int L) {
    int N = A.length;

    long[] costs = new long[L + 1];
    Arrays.fill(costs, Long.MAX_VALUE);
    costs[0] = 0;

    for (int i = 0; i < N; ++i) {
      SortedMap<Long, Integer> costToCount = new TreeMap<>();
      int lower = Math.max(0, L - B[i]);
      int upper = L - A[i];
      for (int j = lower; j <= upper; ++j) {
        updateMap(costToCount, costs[j], 1);
      }

      for (int j = L; j >= 0; --j) {
        if (!costToCount.isEmpty() && !costToCount.firstKey().equals(Long.MAX_VALUE)) {
          costs[j] = Math.min(costs[j], costToCount.firstKey() + P[i]);
        }

        if (j - A[i] >= 0) {
          updateMap(costToCount, costs[j - A[i]], -1);
        }
        if (j - B[i] - 1 >= 0) {
          updateMap(costToCount, costs[j - B[i] - 1], 1);
        }
      }
    }

    return (costs[L] <= M) ? String.valueOf(costs[L]) : "IMPOSSIBLE";
  }

  static void updateMap(Map<Long, Integer> map, long key, int delta) {
    map.put(key, map.getOrDefault(key, 0) + delta);
    map.remove(key, 0);
  }
}
