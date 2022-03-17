import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    int X = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int E = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(X, W, E)));
    }

    sc.close();
  }

  static String solve(int X, int W, int E) {
    return "RSP".repeat(20);
  }
}