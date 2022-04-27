import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] B = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          B[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(B)));
    }

    sc.close();
  }

  static String solve(char[][] B) {
    int R = B.length;
    int C = B[0].length;

    StringBuilder result = new StringBuilder();

    result.append('E');
    for (int r = 0; r < R; ++r) {
      if (r != 0) {
        result.append('S');
      }

      if (r % 2 == 0) {
        result.append("E".repeat(2 * C - 2)).append('S');

        for (int c = C - 1; c >= 1; --c) {
          if (c % 2 == 0 && r != R - 1) {
            result.append("SSWNNW");
          } else {
            result.append("WW");
          }
        }
      } else {
        result.append('S');
      }
    }
    result.append('W').append("N".repeat(2 * R - 1));

    return result.toString();
  }
}