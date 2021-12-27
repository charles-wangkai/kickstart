import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, M)));
    }

    sc.close();
  }

  static int solve(int N, int M) {
    int result = 0;
    for (int i = 0; i <= M; ++i) {
      int delta = multiplyMod(multiplyMod(computeFactorialMod(N * 2 - i), CMod(M, i)), pow2Mod(i));

      if (i % 2 == 0) {
        result = addMod(result, delta);
      } else {
        result = subtractMod(result, delta);
      }
    }

    return result;
  }

  static int computeFactorialMod(int x) {
    int result = 1;
    for (int i = 1; i <= x; ++i) {
      result = multiplyMod(result, i);
    }

    return result;
  }

  static int CMod(int n, int r) {
    BigInteger result = BigInteger.ONE;
    for (int i = 0; i < r; ++i) {
      result = result.multiply(BigInteger.valueOf(n - i)).divide(BigInteger.valueOf(i + 1));
    }

    return result.mod(BigInteger.valueOf(MODULUS)).intValue();
  }

  static int pow2Mod(int exponent) {
    int result = 1;
    for (int i = 0; i < exponent; ++i) {
      result = multiplyMod(result, 2);
    }

    return result;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }

  static int subtractMod(int x, int y) {
    return (x - y + MODULUS) % MODULUS;
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }
}