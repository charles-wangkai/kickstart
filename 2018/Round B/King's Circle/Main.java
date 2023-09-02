// https://en.wikipedia.org/wiki/Fenwick_tree

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  static final int SIZE = 1 + (1 << 20);

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int V1 = sc.nextInt();
      int H1 = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();
      int D = sc.nextInt();
      int E = sc.nextInt();
      int F = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, V1, H1, A, B, C, D, E, F, M)));
    }

    sc.close();
  }

  static long solve(int N, int V1, int H1, int A, int B, int C, int D, int E, int F, int M) {
    int[] V = new int[N];
    int[] H = new int[N];
    V[0] = V1;
    H[0] = H1;
    for (int i = 1; i < N; ++i) {
      V[i] = (int) (((long) A * V[i - 1] + (long) B * H[i - 1] + C) % M);
      H[i] = (int) (((long) D * V[i - 1] + (long) E * H[i - 1] + F) % M);
    }

    Map<Integer, List<Integer>> vToIndices = new HashMap<>();
    for (int i = 0; i < N; ++i) {
      if (!vToIndices.containsKey(V[i])) {
        vToIndices.put(V[i], new ArrayList<>());
      }
      vToIndices.get(V[i]).add(i);
    }

    Map<Integer, List<Integer>> hToIndices = new HashMap<>();
    for (int i = 0; i < N; ++i) {
      if (!hToIndices.containsKey(H[i])) {
        hToIndices.put(H[i], new ArrayList<>());
      }
      hToIndices.get(H[i]).add(i);
    }

    return computeVSameNum(vToIndices)
        - computeHSameNum(hToIndices)
        + computeTwoDistinctVNum(N, vToIndices)
        + computeTurnNum(H, vToIndices);
  }

  static long computeTurnNum(int[] H, Map<Integer, List<Integer>> vToIndices) {
    int[] leftA = new int[SIZE];
    int[] rightA = new int[SIZE];
    for (int hi : H) {
      add(rightA, hi + 1, 1);
    }

    long result = 0;
    int[] sortedV = vToIndices.keySet().stream().sorted().mapToInt(x -> x).toArray();
    for (int v : sortedV) {
      for (int index : vToIndices.get(v)) {
        add(rightA, H[index] + 1, -1);
      }

      for (int index : vToIndices.get(v)) {
        result +=
            (long) prefix_sum(leftA, H[index] + 1) * prefix_sum(rightA, H[index] + 1)
                + (long) range_sum(leftA, H[index], SIZE - 1)
                    * range_sum(rightA, H[index], SIZE - 1);
      }

      for (int index : vToIndices.get(v)) {
        add(leftA, H[index] + 1, 1);
      }
    }

    return result;
  }

  static int range_sum(int[] A, int i, int j) {
    int sum = 0;
    for (; j > i; j -= LSB(j)) {
      sum += A[j];
    }
    for (; i > j; i -= LSB(i)) {
      sum -= A[i];
    }

    return sum;
  }

  static int prefix_sum(int[] A, int i) {
    int sum = A[0];
    for (; i != 0; i -= LSB(i)) {
      sum += A[i];
    }

    return sum;
  }

  static int LSB(int i) {
    return i & -i;
  }

  static void add(int[] A, int i, int delta) {
    if (i == 0) {
      A[0] += delta;

      return;
    }
    for (; i < SIZE; i += LSB(i)) {
      A[i] += delta;
    }
  }

  static long computeTwoDistinctVNum(int N, Map<Integer, List<Integer>> vToIndices) {
    return vToIndices.values().stream()
        .mapToLong(indices -> C(indices.size(), 2) * (N - indices.size()))
        .sum();
  }

  static long computeHSameNum(Map<Integer, List<Integer>> hToIndices) {
    return hToIndices.values().stream().mapToLong(indices -> C(indices.size(), 3)).sum();
  }

  static long computeVSameNum(Map<Integer, List<Integer>> vToIndices) {
    return vToIndices.values().stream().mapToLong(indices -> C(indices.size(), 3)).sum();
  }

  static long C(int n, int r) {
    long result = 1;
    for (int i = 0; i < r; ++i) {
      result = result * (n - i) / (i + 1);
    }

    return result;
  }
}
