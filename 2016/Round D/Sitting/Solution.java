import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(R, C)));
    }

    sc.close();
  }

  static int solve(int R, int C) {
    return Math.max(computePlayerNum1(R, C), computePlayerNum2(R, C));
  }

  static int computePlayerNum1(int R, int C) {
    int result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if ((r % 3 == 2) == (c % 3 == 2)) {
          ++result;
        }
      }
    }

    return result;
  }

  static int computePlayerNum2(int R, int C) {
    int result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if ((r + c) % 3 != 2) {
          ++result;
        }
      }
    }

    return result;
  }
}
