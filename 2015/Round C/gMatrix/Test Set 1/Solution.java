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
      int K = sc.nextInt();
      int C = sc.nextInt();
      int X = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, K, C, X)));
    }

    sc.close();
  }

  static long solve(int[] A, int[] B, int K, int C, int X) {
    int N = A.length;

    int[][] matrix = new int[N][N];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        matrix[r][c] =
            addMod(addMod(multiplyMod(A[r], r + 1, X), multiplyMod(B[c], c + 1, X), X), C, X);
      }
    }

    @SuppressWarnings("unchecked")
    SortedMap<Integer, Integer>[] columnValueToCountMaps = new SortedMap[N];
    for (int c = 0; c < columnValueToCountMaps.length; ++c) {
      columnValueToCountMaps[c] = new TreeMap<>();
      for (int r = 0; r < K - 1; ++r) {
        updateMap(columnValueToCountMaps[c], matrix[r][c], 1);
      }
    }

    long result = 0;
    for (int endR = K - 1; endR < N; ++endR) {
      for (int c = 0; c < N; ++c) {
        updateMap(columnValueToCountMaps[c], matrix[endR][c], 1);
      }
      if (endR != K - 1) {
        for (int c = 0; c < N; ++c) {
          updateMap(columnValueToCountMaps[c], matrix[endR - K][c], -1);
        }
      }

      SortedMap<Integer, Integer> subMatrixValueToCount = new TreeMap<>();
      for (int c = 0; c < K - 1; ++c) {
        updateMap(subMatrixValueToCount, columnValueToCountMaps[c].lastKey(), 1);
      }

      for (int endC = K - 1; endC < N; ++endC) {
        updateMap(subMatrixValueToCount, columnValueToCountMaps[endC].lastKey(), 1);
        if (endC != K - 1) {
          updateMap(subMatrixValueToCount, columnValueToCountMaps[endC - K].lastKey(), -1);
        }

        result += subMatrixValueToCount.lastKey();
      }
    }

    return result;
  }

  static void updateMap(Map<Integer, Integer> map, int key, int deltaValue) {
    map.put(key, map.getOrDefault(key, 0) + deltaValue);
    map.remove(key, 0);
  }

  static int addMod(int x, int y, int m) {
    return (x + y) % m;
  }

  static int multiplyMod(int x, int y, int m) {
    return (int) ((long) x * y % m);
  }
}
