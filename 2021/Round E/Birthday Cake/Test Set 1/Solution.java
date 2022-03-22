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

  static int solve(int R, int C, int K, int r1, int c1, int r2, int c2) {
    return (r2 - r1 + ((r1 == 1) ? 0 : 1) + ((r2 == R) ? 0 : 1)) * (c2 - c1 + 1)
        + (c2 - c1 + ((c1 == 1) ? 0 : 1) + ((c2 == C) ? 0 : 1)) * (r2 - r1 + 1)
        + Math.min(Math.min(r1 - 1, c1 - 1), Math.min(R - r2, C - c2));
  }
}