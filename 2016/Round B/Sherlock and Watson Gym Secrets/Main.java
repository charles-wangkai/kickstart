import java.util.Scanner;

public class Main {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int A = sc.nextInt();
      int B = sc.nextInt();
      long N = sc.nextLong();
      int K = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, N, K)));
    }

    sc.close();
  }

  static int solve(int A, int B, long N, int K) {
    int result = 0;
    int[] counts1 = new int[K];
    int[] counts2 = new int[K];
    for (int base = 0; base < K; ++base) {
      long min = (base == 0) ? K : base;
      long max = Math.floorDiv(N - base, K) * K + base;
      int count =
          addMod(
              subtractMod(mod(Math.floorDiv(max, K), MODULUS), mod(min / K, MODULUS), MODULUS),
              1,
              MODULUS);

      int power1 = powMod(base, A, K);
      counts1[power1] = addMod(counts1[power1], count, MODULUS);

      int power2 = powMod(base, B, K);
      counts2[power2] = addMod(counts2[power2], count, MODULUS);

      if (addMod(power1, power2, K) == 0) {
        result = subtractMod(result, count, MODULUS);
      }
    }

    for (int i = 0; i < K; ++i) {
      result = addMod(result, multiplyMod(counts1[i], counts2[(K - i) % K], MODULUS), MODULUS);
    }

    return result;
  }

  static int mod(long x, int m) {
    return (int) (x % m);
  }

  static int addMod(int x, int y, int m) {
    return mod(x + y, m);
  }

  static int subtractMod(int x, int y, int m) {
    return mod(x - y + m, m);
  }

  static int multiplyMod(int x, int y, int m) {
    return mod((long) x * y, m);
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

    return mod(result, m);
  }
}
