import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      long D = sc.nextLong();
      long[] X = new long[N];
      for (int i = 0; i < X.length; ++i) {
        X[i] = sc.nextLong();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, D)));
    }

    sc.close();
  }

  static long solve(long[] X, long D) {
    long result = D;
    for (int i = X.length - 1; i >= 0; --i) {
      result = result / X[i] * X[i];
    }

    return result;
  }
}