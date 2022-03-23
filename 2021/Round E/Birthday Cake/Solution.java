// https://codingcompetitions.withgoogle.com/kickstart/round/000000000043585c/000000000085a285#analysis

import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int K = sc.nextInt();
      int r1 = sc.nextInt();
      int c1 = sc.nextInt();
      int r2 = sc.nextInt();
      int c2 = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(R, C, K, r1, c1, r2, c2)));
    }

    sc.close();
  }

  static long solve(int R, int C, int K, int r1, int c1, int r2, int c2) {
    return Math.min(
            Math.min(
                computeLeftInBorderCutNum(R, C, K, r1, c1, r2, c2),
                computeRightInBorderCutNum(R, C, K, r1, c1, r2, c2)),
            Math.min(
                computeTopInBorderCutNum(R, C, K, r1, c1, r2, c2),
                computeBottomInBorderCutNum(R, C, K, r1, c1, r2, c2)))
        + computeInternalCutNum(K, r2 - r1 + 1, c2 - c1 + 1);
  }

  static long computeInternalCutNum(int K, int n, int m) {
    return (long) n * m - 1 + ((n - 1L) / K) * ((m - 1) / K);
  }

  static int computeLeftInBorderCutNum(int R, int C, int K, int r1, int c1, int r2, int c2) {
    return Math.min(
            ((r1 == 1) ? 0 : ((c2 + K - 1) / K)) + ((r2 == R) ? 0 : ((c2 - c1 + K) / K)),
            ((r1 == 1) ? 0 : ((c2 - c1 + K) / K)) + ((r2 == R) ? 0 : ((c2 + K - 1) / K)))
        + ((c1 == 1) ? 0 : ((r2 - r1 + K) / K))
        + ((c2 == C) ? 0 : ((r2 - r1 + K) / K));
  }

  static int computeRightInBorderCutNum(int R, int C, int K, int r1, int c1, int r2, int c2) {
    return Math.min(
            ((r1 == 1) ? 0 : ((C - c1 + K) / K)) + ((r2 == R) ? 0 : ((c2 - c1 + K) / K)),
            ((r1 == 1) ? 0 : ((c2 - c1 + K) / K)) + ((r2 == R) ? 0 : ((C - c1 + K) / K)))
        + ((c1 == 1) ? 0 : ((r2 - r1 + K) / K))
        + ((c2 == C) ? 0 : ((r2 - r1 + K) / K));
  }

  static int computeTopInBorderCutNum(int R, int C, int K, int r1, int c1, int r2, int c2) {
    return Math.min(
            ((c1 == 1) ? 0 : ((r2 + K - 1) / K)) + ((c2 == C) ? 0 : ((r2 - r1 + K) / K)),
            ((c1 == 1) ? 0 : ((r2 - r1 + K) / K)) + ((c2 == C) ? 0 : ((r2 + K - 1) / K)))
        + ((r1 == 1) ? 0 : ((c2 - c1 + K) / K))
        + ((r2 == R) ? 0 : ((c2 - c1 + K) / K));
  }

  static int computeBottomInBorderCutNum(int R, int C, int K, int r1, int c1, int r2, int c2) {
    return Math.min(
            ((c1 == 1) ? 0 : ((R - r1 + K) / K)) + ((c2 == C) ? 0 : ((r2 - r1 + K) / K)),
            ((c1 == 1) ? 0 : ((r2 - r1 + K) / K)) + ((c2 == C) ? 0 : ((R - r1 + K) / K)))
        + ((r1 == 1) ? 0 : ((c2 - c1 + K) / K))
        + ((r2 == R) ? 0 : ((c2 - c1 + K) / K));
  }
}