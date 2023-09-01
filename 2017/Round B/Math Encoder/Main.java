import java.util.Scanner;

public class Main {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] K = new int[N];
      for (int i = 0; i < K.length; ++i) {
        K[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(K)));
    }

    sc.close();
  }

  static int solve(int[] K) {
    int[] twoPowers = new int[K.length];
    twoPowers[0] = 1;
    for (int i = 1; i < twoPowers.length; ++i) {
      twoPowers[i] = multiplyMod(twoPowers[i - 1], 2);
    }

    int result = 0;
    for (int i = 0; i < K.length; ++i) {
      result =
          addMod(result, multiplyMod(K[i], subtractMod(twoPowers[i], twoPowers[K.length - 1 - i])));
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
