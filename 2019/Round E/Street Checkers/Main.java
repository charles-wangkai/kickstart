import java.math.BigInteger;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();
      int R = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R)));
    }

    sc.close();
  }

  static int solve(int L, int R) {
    return computeNum(L, R, 1)
        + computeNumForOddPrime(L, R)
        + computeNumForTwoByOdd(L, R)
        + computeNum(L, R, 4)
        + computeNumForFourByOddPrime(L, R)
        + computeNum(L, R, 8);
  }

  static int computeNumForFourByOddPrime(int L, int R) {
    int result = 0;
    for (int i = L; i <= R; ++i) {
      if (i % 4 == 0 && i / 4 % 2 != 0 && isPrime(i / 4)) {
        ++result;
      }
    }

    return result;
  }

  static int computeNumForTwoByOdd(int L, int R) {
    int result = 0;
    for (int i = L; i <= R; ++i) {
      if (i % 2 == 0 && i / 2 % 2 != 0) {
        ++result;
      }
    }

    return result;
  }

  static int computeNum(int L, int R, int value) {
    return (value >= L && value <= R) ? 1 : 0;
  }

  static int computeNumForOddPrime(int L, int R) {
    int result = 0;
    for (int i = L; i <= R; ++i) {
      if (i % 2 != 0 && isPrime(i)) {
        ++result;
      }
    }

    return result;
  }

  static boolean isPrime(int x) {
    return BigInteger.valueOf(x).isProbablePrime(10);
  }
}
