import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Solution {
  static final int DECK_SIZE = 8;
  static final int POWER_MAP_MAX_SIZE = 5;

  static long maxPower;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int M = sc.nextInt();
      int N = sc.nextInt();
      int[] K = new int[N];
      int[] L = new int[N];
      int[][] A = new int[N][];
      int[][] C = new int[N][];
      for (int i = 0; i < N; ++i) {
        K[i] = sc.nextInt();
        L[i] = sc.nextInt() - 1;
        A[i] = new int[K[i]];
        for (int j = 0; j < A[i].length; ++j) {
          A[i][j] = sc.nextInt();
        }
        C[i] = new int[K[i] - 1];
        for (int j = 0; j < C[i].length; ++j) {
          C[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(M, K, L, A, C)));
    }

    sc.close();
  }

  static long solve(int M, int[] K, int[] L, int[][] A, int[][] C) {
    int N = K.length;

    maxPower = 0;
    int powerMapSize = Math.min(POWER_MAP_MAX_SIZE, (N + 1) / 2);
    NavigableMap<Integer, Long>[] powerMaps = buildPowerMaps(M, K, L, A, C, 0, powerMapSize);
    search2(powerMaps, M, K, L, A, C, powerMapSize, N, powerMapSize, 0);

    return maxPower;
  }

  static NavigableMap<Integer, Long>[] buildPowerMaps(
      int M, int[] K, int[] L, int[][] A, int[][] C, int beginIndex, int endIndex) {
    @SuppressWarnings("unchecked")
    NavigableMap<Integer, Long>[] powerMaps = new NavigableMap[endIndex - beginIndex + 1];
    for (int i = 0; i < powerMaps.length; ++i) {
      powerMaps[i] = new TreeMap<>();
    }

    search1(powerMaps, M, K, L, A, C, beginIndex, endIndex, beginIndex, 0);

    for (int i = 0; i < powerMaps.length; ++i) {
      long prevPower = -1;
      List<Integer> costs = new ArrayList<>(powerMaps[i].keySet());
      for (int cost : costs) {
        if (powerMaps[i].get(cost) <= prevPower) {
          powerMaps[i].remove(cost);
        } else {
          prevPower = powerMaps[i].get(cost);
        }
      }
    }

    return powerMaps;
  }

  static void search1(
      NavigableMap<Integer, Long>[] powerMaps,
      int M,
      int[] K,
      int[] L,
      int[][] A,
      int[][] C,
      int beginIndex,
      int endIndex,
      int index,
      int cost) {
    if (index == endIndex) {
      int[] sorted = new int[endIndex - beginIndex];
      for (int i = 0; i < sorted.length; ++i) {
        sorted[i] = A[beginIndex + i][L[beginIndex + i]];
      }
      Arrays.sort(sorted);

      long power = 0;
      for (int i = 0; i < powerMaps.length; ++i) {
        if (i != 0) {
          power += sorted[sorted.length - i];
        }

        powerMaps[i].put(cost, Math.max(powerMaps[i].getOrDefault(cost, 0L), power));
      }

      return;
    }

    search1(powerMaps, M, K, L, A, C, beginIndex, endIndex, index + 1, cost);
    if (L[index] != K[index] - 1 && cost + C[index][L[index]] <= M) {
      ++L[index];

      search1(powerMaps, M, K, L, A, C, beginIndex, endIndex, index, cost + C[index][L[index] - 1]);

      --L[index];
    }
  }

  static void search2(
      NavigableMap<Integer, Long>[] powerMaps,
      int M,
      int[] K,
      int[] L,
      int[][] A,
      int[][] C,
      int beginIndex,
      int endIndex,
      int index,
      int cost) {
    if (index == endIndex) {
      int[] sorted = new int[endIndex - beginIndex];
      for (int i = 0; i < sorted.length; ++i) {
        sorted[i] = A[beginIndex + i][L[beginIndex + i]];
      }
      Arrays.sort(sorted);

      long power = 0;
      for (int i = 0; i <= sorted.length; ++i) {
        if (i != 0) {
          power += sorted[sorted.length - i];
        }

        int other = DECK_SIZE - i;
        if (other < powerMaps.length) {
          Integer key = powerMaps[other].floorKey(M - cost);
          if (key != null) {
            maxPower = Math.max(maxPower, power + powerMaps[other].get(key));
          }
        }
      }

      return;
    }

    search2(powerMaps, M, K, L, A, C, beginIndex, endIndex, index + 1, cost);
    if (L[index] != K[index] - 1 && cost + C[index][L[index]] <= M) {
      ++L[index];

      search2(powerMaps, M, K, L, A, C, beginIndex, endIndex, index, cost + C[index][L[index] - 1]);

      --L[index];
    }
  }
}
