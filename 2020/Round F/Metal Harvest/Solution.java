import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] S = new int[N];
      int[] E = new int[N];
      for (int i = 0; i < N; ++i) {
        S[i] = sc.nextInt();
        E[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, E, K)));
    }

    sc.close();
  }

  static int solve(int[] S, int[] E, int K) {
    int[] sortedIndices =
        IntStream.range(0, S.length)
            .boxed()
            .sorted(Comparator.comparing(i -> S[i]))
            .mapToInt(x -> x)
            .toArray();

    int result = 0;
    int begin = -K;
    for (int index : sortedIndices) {
      if (E[index] - begin > K) {
        int start = Math.max(begin + K, S[index]);
        int delta = (E[index] - start + K - 1) / K;
        result += delta;
        begin = start + (delta - 1) * K;
      }
    }

    return result;
  }
}