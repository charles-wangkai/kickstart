import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String pattern1 = sc.next();
      String pattern2 = sc.next();

      System.out.println(
          String.format("Case #%d: %s", tc, solve(pattern1, pattern2) ? "TRUE" : "FALSE"));
    }

    sc.close();
  }

  static boolean solve(String pattern1, String pattern2) {
    int[] prevs1 = buildPrevs(pattern1);
    int[] prevs2 = buildPrevs(pattern2);

    boolean[][] dp = new boolean[pattern1.length() + 1][pattern2.length() + 1];
    for (int i = 0; i <= pattern1.length(); ++i) {
      for (int j = 0; j <= pattern2.length(); ++j) {
        if (i == 0 && j == 0) {
          dp[i][j] = true;
        } else {
          if (i != 0 && pattern1.charAt(i - 1) == '*') {
            dp[i][j] |= dp[i - 1][j];

            int prev2 = prevs2[j];
            for (int k = 1; k <= 4 && prev2 != -1; ++k) {
              dp[i][j] |= dp[i - 1][prev2 - 1];

              prev2 = prevs2[prev2 - 1];
            }
          }
          if (j != 0 && pattern2.charAt(j - 1) == '*') {
            dp[i][j] |= dp[i][j - 1];

            int prev1 = prevs1[i];
            for (int k = 1; k <= 4 && prev1 != -1; ++k) {
              dp[i][j] |= dp[prev1 - 1][j - 1];

              prev1 = prevs1[prev1 - 1];
            }
          }
          if (i != 0 && j != 0 && pattern1.charAt(i - 1) == pattern2.charAt(j - 1)) {
            dp[i][j] |= dp[i - 1][j - 1];
          }
        }
      }
    }

    return dp[pattern1.length()][pattern2.length()];
  }

  static int[] buildPrevs(String pattern) {
    int[] prevs = new int[pattern.length() + 1];
    int prev = -1;
    for (int i = 0; i < prevs.length; ++i) {
      if (i != 0 && pattern.charAt(i - 1) != '*') {
        prev = i;
      }

      prevs[i] = prev;
    }

    return prevs;
  }
}
