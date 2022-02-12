import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int K = sc.nextInt();
      int[] A = new int[K];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static int solve(int[] A) {
    int[] dp = new int[4];
    for (int i = 1; i < A.length; ++i) {
      int[] nextDp = new int[dp.length];
      Arrays.fill(nextDp, Integer.MAX_VALUE);
      for (int j = 0; j < nextDp.length; ++j) {
        for (int k = 0; k < dp.length; ++k) {
          nextDp[j] =
              Math.min(
                  nextDp[j],
                  dp[k] + ((Integer.compare(j, k) == Integer.compare(A[i], A[i - 1])) ? 0 : 1));
        }
      }

      dp = nextDp;
    }

    return Arrays.stream(dp).min().getAsInt();
  }
}