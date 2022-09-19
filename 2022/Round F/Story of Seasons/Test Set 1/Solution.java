import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int D = sc.nextInt();
      int N = sc.nextInt();
      int X = sc.nextInt();
      int[] Q = new int[N];
      int[] L = new int[N];
      int[] V = new int[N];
      for (int i = 0; i < N; ++i) {
        Q[i] = sc.nextInt();
        L[i] = sc.nextInt();
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(D, Q, L, V, X)));
    }

    sc.close();
  }

  static int solve(int D, int[] Q, int[] L, int[] V, int X) {
    int N = Q.length;

    int result = 0;
    for (int mask = 0; mask < 1 << N; ++mask) {
      int mask_ = mask;
      int[] sortedIndices =
          IntStream.range(0, N)
              .filter(i -> (mask_ & (1 << i)) != 0)
              .boxed()
              .sorted(
                  Comparator.comparing((Integer i) -> L[i])
                      .reversed()
                      .thenComparing(Comparator.comparing((Integer i) -> V[i]).reversed()))
              .mapToInt(x -> x)
              .toArray();
      if (IntStream.range(0, sortedIndices.length).allMatch(i -> i + L[sortedIndices[i]] < D)) {
        result = Math.max(result, Arrays.stream(sortedIndices).map(i -> V[i]).sum());
      }
    }

    return result;
  }
}