import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  static final int LIMIT = 500;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int E = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      int[] C = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, Y, C, E)));
    }

    sc.close();
  }

  static long solve(int[] X, int[] Y, int[] C, int E) {
    int[][] energies = new int[LIMIT + 1][LIMIT + 1];
    for (int i = 0; i < X.length; ++i) {
      energies[Y[i]][X[i]] = C[i];
    }

    long[][] dp = new long[2][LIMIT + 1];
    Arrays.fill(dp[1], -E);
    for (int i = energies.length - 1; i >= 0; --i) {
      long[][] nextDp = new long[2][LIMIT + 1];

      for (int j = 0; j <= LIMIT; ++j) {
        nextDp[0][j] =
            Math.max((j == 0) ? Long.MIN_VALUE : nextDp[0][j - 1], dp[0][j]) + energies[i][j];
      }
      for (int j = LIMIT; j >= 0; --j) {
        nextDp[1][j] =
            Math.max((j == LIMIT) ? Long.MIN_VALUE : nextDp[1][j + 1], dp[1][j]) + energies[i][j];
      }

      long maxLeft = Arrays.stream(dp[1]).max().getAsLong();
      long rightSum = 0;
      for (int j = 0; j <= LIMIT; ++j) {
        rightSum += energies[i][j];
        nextDp[0][j] = Math.max(nextDp[0][j], maxLeft - E + rightSum);
      }

      long maxRight = Arrays.stream(dp[0]).max().getAsLong();
      long leftSum = 0;
      for (int j = LIMIT; j >= 0; --j) {
        leftSum += energies[i][j];
        nextDp[1][j] = Math.max(nextDp[1][j], maxRight - E + leftSum);
      }

      dp = nextDp;
    }

    return Arrays.stream(dp)
        .mapToLong(line -> Arrays.stream(line).max().getAsLong())
        .max()
        .getAsLong();
  }
}
