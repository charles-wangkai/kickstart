import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[][] seeds = new int[3][6];
      for (int i = 0; i < seeds.length; ++i) {
        for (int j = 0; j < seeds[i].length; ++j) {
          seeds[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, Q, seeds)));
    }

    sc.close();
  }

  static int solve(int N, int Q, int[][] seeds) {
    int[] X = new int[N];
    for (int i = 0; i < X.length; ++i) {
      X[i] =
          (i <= 1)
              ? seeds[0][i]
              : (int)
                  (((long) seeds[0][2] * X[i - 1] + (long) seeds[0][3] * X[i - 2] + seeds[0][4])
                      % seeds[0][5]);
    }

    int[] Y = new int[N];
    for (int i = 0; i < Y.length; ++i) {
      Y[i] =
          (i <= 1)
              ? seeds[1][i]
              : (int)
                  (((long) seeds[1][2] * Y[i - 1] + (long) seeds[1][3] * Y[i - 2] + seeds[1][4])
                      % seeds[1][5]);
    }

    int[] Z = new int[Q];
    for (int i = 0; i < Z.length; ++i) {
      Z[i] =
          (i <= 1)
              ? seeds[2][i]
              : (int)
                  (((long) seeds[2][2] * Z[i - 1] + (long) seeds[2][3] * Z[i - 2] + seeds[2][4])
                      % seeds[2][5]);
    }

    int[] L = new int[N];
    int[] R = new int[N];
    for (int i = 0; i < N; ++i) {
      L[i] = Math.min(X[i], Y[i]) + 1;
      R[i] = Math.max(X[i], Y[i]) + 1;
    }
    int[] K = new int[Q];
    for (int i = 0; i < K.length; ++i) {
      K[i] = Z[i] + 1;
    }

    int result = 0;
    for (int i = 0; i < K.length; ++i) {
      result += (i + 1) * computeScore(L, R, K[i]);
    }

    return result;
  }

  static int computeScore(int[] L, int[] R, int Ki) {
    if (Ki > IntStream.range(0, L.length).map(i -> R[i] - L[i] + 1).asLongStream().sum()) {
      return 0;
    }

    int result = -1;
    int lower = Arrays.stream(L).min().getAsInt();
    int upper = Arrays.stream(R).max().getAsInt();
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (computeGENum(L, R, middle) >= Ki) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static long computeGENum(int[] L, int[] R, int minScore) {
    return IntStream.range(0, L.length)
        .map(i -> Math.max(0, R[i] - Math.max(L[i], minScore) + 1))
        .asLongStream()
        .sum();
  }
}