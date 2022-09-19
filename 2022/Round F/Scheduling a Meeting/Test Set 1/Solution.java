import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int X = sc.nextInt();
      int D = sc.nextInt();
      int M = sc.nextInt();
      int[] P = new int[M];
      int[] L = new int[M];
      int[] R = new int[M];
      for (int i = 0; i < M; ++i) {
        P[i] = sc.nextInt() - 1;
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, K, X, D, P, L, R)));
    }

    sc.close();
  }

  static int solve(int N, int K, int X, int D, int[] P, int[] L, int[] R) {
    int result = Integer.MAX_VALUE;
    for (int beginTime = 0; beginTime + X <= D; ++beginTime) {
      int endTime = beginTime + X;
      for (int mask = 0; mask < 1 << N; ++mask) {
        if (Integer.bitCount(mask) >= K) {
          int beginTime_ = beginTime;
          int mask_ = mask;
          result =
              Math.min(
                  result,
                  (int)
                      IntStream.range(0, L.length)
                          .filter(
                              i ->
                                  (mask_ & (1 << P[i])) != 0
                                      && !(L[i] >= endTime || R[i] <= beginTime_))
                          .count());
        }
      }
    }

    return result;
  }
}