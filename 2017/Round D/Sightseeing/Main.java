import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Ts = sc.nextInt();
      int Tf = sc.nextInt();
      int[] S = new int[N - 1];
      int[] F = new int[N - 1];
      int[] D = new int[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        S[i] = sc.nextInt();
        F[i] = sc.nextInt();
        D[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(Ts, Tf, S, F, D)));
    }

    sc.close();
  }

  static String solve(int Ts, int Tf, int[] S, int[] F, int[] D) {
    int N = S.length + 1;

    long[] minTimes = new long[N];
    Arrays.fill(minTimes, Long.MAX_VALUE);
    minTimes[0] = 0;

    for (int i = 0; i < S.length; ++i) {
      long[] nextMinTimes = new long[N];
      Arrays.fill(nextMinTimes, Long.MAX_VALUE);

      for (int j = 0; j < minTimes.length; ++j) {
        if (minTimes[j] != Long.MAX_VALUE) {
          nextMinTimes[j] =
              Math.min(nextMinTimes[j], computeArrivalTime(S[i], F[i], D[i], minTimes[j]));
          nextMinTimes[j + 1] =
              Math.min(nextMinTimes[j + 1], computeArrivalTime(S[i], F[i], D[i], minTimes[j] + Ts));
        }
      }

      minTimes = nextMinTimes;
    }

    long[] minTimes_ = minTimes;
    return IntStream.range(0, minTimes.length)
        .filter(i -> minTimes_[i] <= Tf)
        .boxed()
        .max(Comparator.naturalOrder())
        .map(String::valueOf)
        .orElse("IMPOSSIBLE");
  }

  static long computeArrivalTime(int startTime, int frequency, int duration, long fromTime) {
    return Math.max(0, Math.floorDiv(fromTime - startTime + frequency - 1, frequency)) * frequency
        + startTime
        + duration;
  }
}
