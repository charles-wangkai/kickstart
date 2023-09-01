import java.math.BigInteger;
import java.util.Scanner;

public class Main {
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
    if (R > C) {
      return solve(C, R);
    }

    return addMod(
        subtractMod(
            multiplyMod(multiplyMod(R, C), computeRangeSum(R - 1)),
            multiplyMod(addMod(R, C), computeRangeSquareSum(R - 1))),
        computeRangeCubeSum(R - 1));
  }

  static int computeRangeSum(int n) {
    return mod(n * (n + 1L) / 2);
  }

  static int computeRangeSquareSum(int n) {
    return BigInteger.valueOf(n)
        .multiply(BigInteger.valueOf(n + 1))
        .multiply(BigInteger.valueOf(2 * n + 1))
        .divide(BigInteger.valueOf(6))
        .mod(BigInteger.valueOf(MODULUS))
        .intValue();
  }

  static int computeRangeCubeSum(int n) {
    int r = mod(n * (n + 1L) / 2);

    return multiplyMod(r, r);
  }

  static int mod(long x) {
    return (int) (x % MODULUS + MODULUS) % MODULUS;
  }

  static int addMod(int x, int y) {
    return mod(x + y);
  }

  static int subtractMod(int x, int y) {
    return mod(x - y);
  }

  static int multiplyMod(int x, int y) {
    return mod((long) x * y);
  }
}
