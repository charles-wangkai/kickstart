import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int N = sc.nextInt();
      int[] X = new int[W];
      for (int i = 0; i < X.length; ++i) {
        X[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, N)));
    }

    sc.close();
  }

  static long solve(int[] X, int N) {
    return Arrays.stream(X)
        .mapToLong(
            target ->
                Arrays.stream(X)
                    .map(xi -> Math.min(Math.abs(xi - target), N - Math.abs(xi - target)))
                    .asLongStream()
                    .sum())
        .min()
        .getAsLong();
  }
}