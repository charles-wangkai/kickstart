import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int A = sc.nextInt();
      int N = sc.nextInt();
      int P = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, N, P)));
    }

    sc.close();
  }

  static int solve(int A, int N, int P) {
    return powMod(A, factorial(N), P);
  }

  static int factorial(int a) {
    return IntStream.rangeClosed(1, a).reduce(1, (x, y) -> x * y);
  }

  static int powMod(int base, int exponent, int m) {
    int result = 1;
    for (int i = 0; i < exponent; ++i) {
      result = result * base % m;
    }

    return result;
  }
}
