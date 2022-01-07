import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
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
            .sorted(Comparator.comparing((Integer i) -> L[i]).reversed())
            .mapToInt(x -> x)
            .toArray();

    int[] dp = new int[N + 1];
    for (int index : sortedIndices) {
      for (int i = dp.length - 1; i >= 1; --i) {
        dp[i] = Math.max(dp[i], dp[i - 1] + Math.max(0, E[index] - L[index] * S[0] * (i - 1)));
      }
    }

    return Arrays.stream(dp).max().getAsInt();
  }
}
