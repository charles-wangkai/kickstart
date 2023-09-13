// https://en.wikipedia.org/wiki/Harmonic_series_(mathematics)
// https://en.wikipedia.org/wiki/Euler%27s_constant

import java.util.Scanner;

public class Main {
  static final int EXACT_LIMIT = 100_000_000;
  static final double EULER_CONSTANT = 0.577215664901532;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long N = sc.nextLong();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N)));
    }

    sc.close();
  }

  static double solve(long N) {
    return (N <= EXACT_LIMIT) ? computeExactSore((int) N) : (Math.log(N) + EULER_CONSTANT);
  }

  static double computeExactSore(int N) {
    double result = 0;
    for (int i = 1; i <= N; ++i) {
      result += 1.0 / i;
    }

    return result;
  }
}
