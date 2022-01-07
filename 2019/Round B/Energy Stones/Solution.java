import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  static final int TIME_LIMIT = 100;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] S = new int[N];
      int[] E = new int[N];
      int[] L = new int[N];
      for (int i = 0; i < N; ++i) {
        S[i] = sc.nextInt();
        E[i] = sc.nextInt();
        L[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, E, L)));
    }

    sc.close();
  }

  static int solve(int[] S, int[] E, int[] L) {
    int N = S.length;

    int[] sortedIndices =
        IntStream.range(0, N)
            .boxed()
            .sorted((i1, i2) -> -Integer.compare(L[i1] * S[i2], L[i2] * S[i1]))
            .mapToInt(x -> x)
            .toArray();

    int[] dp = new int[N * TIME_LIMIT + 1];
    for (int index : sortedIndices) {
      for (int i = dp.length - 1; i >= S[index]; --i) {
        dp[i] =
            Math.max(dp[i], dp[i - S[index]] + Math.max(0, E[index] - L[index] * (i - S[index])));
      }
    }

    return Arrays.stream(dp).max().getAsInt();
  }
}
