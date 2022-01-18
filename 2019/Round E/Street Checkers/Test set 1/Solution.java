import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();
      int R = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R)));
    }

    sc.close();
  }

  static int solve(int L, int R) {
    int result = 0;
    for (int X = L; X <= R; ++X) {
      int[] counts = new int[2];
      for (int i = 1; i * i <= X; ++i) {
        if (X % i == 0) {
          ++counts[i % 2];
          if (X / i != i) {
            ++counts[X / i % 2];
          }
        }
      }

      if (Math.abs(counts[0] - counts[1]) <= 2) {
        ++result;
      }
    }

    return result;
  }
}