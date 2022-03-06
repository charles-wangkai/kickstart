import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      int K = sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S, K)));
    }

    sc.close();
  }

  static int solve(String S, int K) {
    int diffCount = 0;
    for (int i = 0, j = S.length() - 1; i < j; ++i, --j) {
      if (S.charAt(i) != S.charAt(j)) {
        ++diffCount;
      }
    }

    return Math.abs(diffCount - K);
  }
}