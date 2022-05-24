import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static int solve(String S) {
    int N = S.length();

    int[] factorials = new int[N + 1];
    factorials[0] = 1;
    for (int i = 1; i < factorials.length; ++i) {
      factorials[i] = multiplyMod(factorials[i - 1], i);
    }

    int result = factorials[N];
    int[][][] dp = new int[N][N][N];
    for (int length = 1; length < N; ++length) {
      for (int step = length; step <= N; ++step) {
        for (int beginIndex = 0; beginIndex + step - 1 < N; ++beginIndex) {
          int endIndex = beginIndex + step - 1;

          if (beginIndex == endIndex) {
            dp[length][beginIndex][endIndex] = 1;
          } else {
            dp[length][beginIndex][endIndex] =
                addMod(
                    addMod(
                        addMod(
                            dp[length][beginIndex + 1][endIndex],
                            dp[length][beginIndex][endIndex - 1]),
                        -((beginIndex + 1 <= endIndex - 1)
                            ? dp[length][beginIndex + 1][endIndex - 1]
                            : 0)),
                    (length != 1 && S.charAt(beginIndex) == S.charAt(endIndex))
                        ? ((length == 2) ? 1 : dp[length - 2][beginIndex + 1][endIndex - 1])
                        : 0);
          }
        }
      }

      result =
          addMod(
              result,
              multiplyMod(
                  multiplyMod(factorials[N - length], factorials[length]), dp[length][0][N - 1]));
    }
    result = divideMod(result, factorials[N]);

    return result;
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }

  static int divideMod(int x, int y) {
    return multiplyMod(x, modInv(y));
  }

  static int modInv(int x) {
    return BigInteger.valueOf(x).modInverse(BigInteger.valueOf(MODULUS)).intValue();
  }
}