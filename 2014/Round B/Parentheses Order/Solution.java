import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int n = sc.nextInt();
      long k = sc.nextLong();

      System.out.println(String.format("Case #%d: %s", tc, solve(n, k)));
    }

    sc.close();
  }

  static String solve(int n, long k) {
    long inf = k + 1;

    long[][] wayNums = new long[n + 1][n + 1];
    wayNums[0][0] = 1;
    for (int i = 0; i <= n; ++i) {
      for (int j = Math.max(1, i); j <= n; ++j) {
        if (i != 0) {
          wayNums[i][j] += wayNums[i - 1][j];
        }
        if (j != i) {
          wayNums[i][j] += wayNums[i][j - 1];
        }

        wayNums[i][j] = Math.min(inf, wayNums[i][j]);
      }
    }

    if (k > wayNums[n][n]) {
      return "Doesn't Exist!";
    }

    StringBuilder result = new StringBuilder();
    int leftNum = n;
    for (int i = 0; i < n; ++i) {
      int nextLeftNum = 0;
      while (k > wayNums[nextLeftNum][n - 1 - i]) {
        k -= wayNums[nextLeftNum][n - 1 - i];
        ++nextLeftNum;
      }

      result.append("(".repeat(leftNum - nextLeftNum)).append(")");

      leftNum = nextLeftNum;
    }

    return result.toString();
  }
}
