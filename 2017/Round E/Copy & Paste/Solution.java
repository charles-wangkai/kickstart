import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static int solve(String S) {
    int[][][] dp = new int[S.length()][S.length()][S.length()];
    for (int i = 0; i < dp.length; ++i) {
      for (int j = 0; j < dp[i].length; ++j) {
        Arrays.fill(dp[i][j], Integer.MAX_VALUE);
      }
    }

    for (int i = 0; i < dp.length; ++i) {
      int minOperNum = i + 1;
      for (int j = 0; j <= i; ++j) {
        for (int k = j; k <= i; ++k) {
          minOperNum = Math.min(minOperNum, dp[i][j][k]);
        }
      }
      for (int j = 0; j <= i; ++j) {
        for (int k = j; k <= i; ++k) {
          dp[i][j][k] = Math.min(dp[i][j][k], minOperNum + 1);
        }
      }

      if (i != dp.length - 1) {
        for (int j = 0; j <= i; ++j) {
          for (int k = j; k <= i; ++k) {
            dp[i + 1][j][k] = Math.min(dp[i + 1][j][k], dp[i][j][k] + 1);
          }
        }
      }

      for (int j = 0; j <= i; ++j) {
        for (int k = j; k <= i; ++k) {
          int length = k - j + 1;
          if (i + length < S.length() && isSame(S, j, k, i + 1)) {
            dp[i + length][j][k] = Math.min(dp[i + length][j][k], dp[i][j][k] + 1);
          }
        }
      }
    }

    int result = S.length();
    for (int j = 0; j < S.length(); ++j) {
      for (int k = j; k < S.length(); ++k) {
        result = Math.min(result, dp[S.length() - 1][j][k]);
      }
    }

    return result;
  }

  static boolean isSame(String S, int clipboardBeginIndex, int clipboardEndIndex, int fromIndex) {
    for (int i = 0; i < clipboardEndIndex - clipboardBeginIndex + 1; ++i) {
      if (S.charAt(fromIndex + i) != S.charAt(clipboardBeginIndex + i)) {
        return false;
      }
    }

    return true;
  }
}
