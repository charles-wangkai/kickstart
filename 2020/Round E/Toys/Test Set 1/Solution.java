import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] E = new int[N];
      int[] R = new int[N];
      for (int i = 0; i < N; ++i) {
        E[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(E, R)));
    }

    sc.close();
  }

  static String solve(int[] E, int[] R) {
    int N = E.length;

    Outcome bestOutcome = null;
    for (int mask = 1; mask < 1 << N; ++mask) {
      int mask_ = mask;
      Outcome outcome =
          compute(
              IntStream.range(0, N).filter(i -> (mask_ & (1 << i)) != 0).map(i -> E[i]).toArray(),
              IntStream.range(0, N).filter(i -> (mask_ & (1 << i)) != 0).map(i -> R[i]).toArray());

      if (bestOutcome == null
          || outcome.time > bestOutcome.time
          || (outcome.time == bestOutcome.time && outcome.size > bestOutcome.size)) {
        bestOutcome = outcome;
      }
    }

    return String.format(
        "%s %d",
        (bestOutcome.time == Long.MAX_VALUE) ? "INDEFINITELY" : String.valueOf(bestOutcome.time),
        N - bestOutcome.size);
  }

  static Outcome compute(int[] enjoyments, int[] remembrances) {
    int size = enjoyments.length;

    long time = 0;
    long[] lastTimes = new long[size];
    Arrays.fill(lastTimes, -1);
    for (int i = 0; i < 2 * size; ++i) {
      int index = i % size;

      if (lastTimes[index] != -1 && time - lastTimes[index] < remembrances[index]) {
        return new Outcome(time, size);
      }

      time += enjoyments[index];
      lastTimes[index] = time;
    }

    return new Outcome(Long.MAX_VALUE, size);
  }
}

class Outcome {
  long time;
  int size;

  Outcome(long time, int size) {
    this.time = time;
    this.size = size;
  }
}