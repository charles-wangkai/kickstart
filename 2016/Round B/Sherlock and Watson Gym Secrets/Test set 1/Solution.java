import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int A = sc.nextInt();
      int B = sc.nextInt();
      int N = sc.nextInt();
      int K = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, N, K)));
    }

    sc.close();
  }

  static int solve(int A, int B, int N, int K) {
    int result = 0;
    for (int i = 1; i <= N; ++i) {
      for (int j = 1; j <= N; ++j) {
        if (j != i && addMod(powMod(i, A, K), powMod(j, B, K), K) == 0) {
          ++result;
        }
      }
    }

    return result;
  }

  static int addMod(int x, int y, int m) {
    return (x + y) % m;
  }

  static int multiplyMod(int x, int y, int m) {
    return x * y % m;
  }

  static int powMod(int base, int exponent, int m) {
    int result = 1;
    while (exponent != 0) {
      if ((exponent & 1) != 0) {
        result = multiplyMod(result, base, m);
      }

      base = multiplyMod(base, base, m);
      exponent >>= 1;
    }

    return result;
  }
}
