import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

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
    int result = 0;
    for (int i = 1; i < R && i < C; ++i) {
      result = addMod(result, multiplyMod(R - i, C - i));
    }
    for (int i = 1; i < R; ++i) {
      for (int j = 1; j < C; ++j) {
        int sum = i + j;
        if (sum < R && sum < C) {
          result = addMod(result, multiplyMod(R - sum, C - sum));
        }
      }
    }

    return result;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }
}
