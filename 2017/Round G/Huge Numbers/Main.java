import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int N_CUT = 10;

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
    if (N <= N_CUT) {
      return powMod(A, factorial(N), P);
    }

    int exponentBegin = factorial(N_CUT);
    int remainderBegin = powMod(A, exponentBegin, P);

    int exponent = exponentBegin;
    int remainder = remainderBegin;
    Map<Integer, Integer> remainderToExponent = new HashMap<>();
    while (!remainderToExponent.containsKey(remainder)) {
      remainderToExponent.put(remainder, exponent);

      ++exponent;
      remainder = multiplyMod(remainder, A, P);
    }
    int cycle = remainderToExponent.size();

    int exponentRemainder =
        IntStream.rangeClosed(1, N).reduce(1, (x, y) -> multiplyMod(x, y, cycle));

    int diff = (exponentRemainder - exponentBegin % cycle + cycle) % cycle;

    int result = remainderBegin;
    for (int i = 0; i < diff; ++i) {
      result = multiplyMod(result, A, P);
    }

    return result;
  }

  static int factorial(int a) {
    return IntStream.rangeClosed(1, a).reduce(1, (x, y) -> x * y);
  }

  static int multiplyMod(int x, int y, int m) {
    return (int) ((long) x * y % m);
  }

  static int powMod(int base, int exponent, int m) {
    return IntStream.range(0, exponent).reduce(1, (x, y) -> multiplyMod(x, base, m));
  }
}
