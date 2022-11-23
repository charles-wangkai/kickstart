import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(L)));
    }

    sc.close();
  }

  static int solve(int L) {
    int[] dp = new int[L + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    int min = 0;
    for (int i = 1; i < dp.length; ++i) {
      dp[i] = Math.min(dp[i], min + i);
      min = Math.min(min, dp[i] - i);

      for (int j = i + i; j < dp.length; j += i) {
        dp[j] = Math.min(dp[j], dp[i] + 4 + (j / i - 1) * 2);
      }
    }

    return dp[dp.length - 1];
  }
}
