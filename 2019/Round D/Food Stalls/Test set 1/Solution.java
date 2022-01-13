import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int K = sc.nextInt();
      int N = sc.nextInt();
      int[] X = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
      }
      int[] C = new int[N];
      for (int i = 0; i < N; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(K, X, C)));
    }

    sc.close();
  }

  static long solve(int K, int[] X, int[] C) {
    return IntStream.range(0, X.length)
        .mapToLong(
            i ->
                C[i]
                    + IntStream.range(0, X.length)
                        .filter(j -> j != i)
                        .map(j -> C[j] + Math.abs(X[j] - X[i]))
                        .boxed()
                        .sorted()
                        .limit(K)
                        .mapToLong(x -> x)
                        .sum())
        .min()
        .getAsLong();
  }
}