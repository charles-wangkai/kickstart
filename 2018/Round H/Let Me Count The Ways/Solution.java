import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;
  static final int N_LIMIT = 100000;
  static final int M_LIMIT = 100000;

  static int[] factorialMods;
  static int[] factorialModInvs;
  static int[] pow2s;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, M)));
    }

    sc.close();
  }

  static void precompute() {
    factorialMods = new int[N_LIMIT * 2 + 1];
    factorialMods[0] = 1;
    factorialModInvs = new int[N_LIMIT * 2 + 1];
    factorialModInvs[0] = 1;
    for (int i = 1; i <= N_LIMIT * 2; ++i) {
      factorialMods[i] = multiplyMod(factorialMods[i - 1], i);
      factorialModInvs[i] =
          BigInteger.valueOf(factorialMods[i]).modInverse(BigInteger.valueOf(MODULUS)).intValue();
    }

    pow2s = new int[M_LIMIT + 1];
    pow2s[0] = 1;
    for (int i = 1; i < pow2s.length; ++i) {
      pow2s[i] = multiplyMod(pow2s[i - 1], 2);
    }
  }

  static int solve(int N, int M) {
    int result = 0;
    for (int i = 0; i <= M; ++i) {
      int delta = multiplyMod(multiplyMod(factorialMods[N * 2 - i], CMod(M, i)), pow2s[i]);

      if (i % 2 == 0) {
        result = addMod(result, delta);
      } else {
        result = subtractMod(result, delta);
      }
    }

    return result;
  }

  static int CMod(int n, int r) {
    return multiplyMod(factorialMods[n], multiplyMod(factorialModInvs[r], factorialModInvs[n - r]));
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