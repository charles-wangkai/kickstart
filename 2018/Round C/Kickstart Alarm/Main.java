import java.math.BigInteger;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int x1 = sc.nextInt();
      int y1 = sc.nextInt();
      int C = sc.nextInt();
      int D = sc.nextInt();
      int E1 = sc.nextInt();
      int E2 = sc.nextInt();
      int F = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, K, x1, y1, C, D, E1, E2, F)));
    }

    sc.close();
  }

  static int solve(int N, int K, int x1, int y1, int C, int D, int E1, int E2, int F) {
    int[] x = new int[N];
    int[] y = new int[N];
    x[0] = x1;
    y[0] = y1;
    for (int i = 1; i < N; ++i) {
      x[i] = (int) (((long) C * x[i - 1] + (long) D * y[i - 1] + E1) % F);
      y[i] = (int) (((long) D * x[i - 1] + (long) C * y[i - 1] + E2) % F);
    }

    int[] A = IntStream.range(0, N).map(i -> (x[i] + y[i]) % F).toArray();

    int result = 0;
    int powerSum = 0;
    for (int i = 0; i < A.length; ++i) {
      powerSum = addMod(powerSum, computeGeometricProgression(i + 1, K));
      result = addMod(result, multiplyMod(multiplyMod(A[i], A.length - i), powerSum));
    }

    return result;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }

  static int powMod(int base, int exponent) {
    int result = 1;
    while (exponent != 0) {
      if ((exponent & 1) != 0) {
        result = multiplyMod(result, base);
      }

      base = multiplyMod(base, base);
      exponent >>= 1;
    }

    return result;
  }

  static int modInv(int x) {
    return BigInteger.valueOf(x).modInverse(BigInteger.valueOf(MODULUS)).intValue();
  }

  static int computeGeometricProgression(int base, int K) {
    return (base == 1) ? K : multiplyMod(multiplyMod(base, powMod(base, K) - 1), modInv(base - 1));
  }
}
