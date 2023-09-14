import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      int[] L = new int[N];
      int[] R = new int[N];
      int[] A = new int[N];
      for (int i = 0; i < N; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
        A[i] = sc.nextInt();
      }
      int[] X = new int[M];
      int[] Y = new int[M];
      for (int i = 0; i < M; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R, A, X, Y, K)));
    }

    sc.close();
  }

  static long solve(int[] L, int[] R, int[] A, int[] X, int[] Y, int K) {
    int N = L.length;

    @SuppressWarnings("unchecked")
    Set<Integer>[] adjSets = new Set[N];
    for (int i = 0; i < adjSets.length; ++i) {
      adjSets[i] = new HashSet<>();
    }
    for (int i = 0; i < X.length; ++i) {
      adjSets[X[i]].add(Y[i]);
      adjSets[Y[i]].add(X[i]);
    }

    long[] points =
        IntStream.range(0, 1 << N)
            .mapToLong(
                mask ->
                    IntStream.range(0, N)
                        .filter(i -> (mask & (1 << i)) != 0)
                        .map(i -> A[i])
                        .asLongStream()
                        .sum())
            .toArray();

    long[] dp = new long[1 << N];
    dp[0] = 1;
    for (int mask = 0; mask < dp.length; ++mask) {
      for (int i = 0; i < N; ++i) {
        int prevMask = mask - (1 << i);
        if ((mask & (1 << i)) != 0
            && (prevMask == 0
                || (points[prevMask] >= L[i]
                    && points[prevMask] <= R[i]
                    && adjSets[i].stream().anyMatch(prev -> (prevMask & (1 << prev)) != 0)))) {
          dp[mask] += dp[prevMask];
        }
      }
    }

    return IntStream.range(0, dp.length).filter(i -> points[i] == K).mapToLong(i -> dp[i]).sum();
  }
}
