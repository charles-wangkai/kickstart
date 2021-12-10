import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[][] seeds = new int[3][6];
      for (int i = 0; i < seeds.length; ++i) {
        for (int j = 0; j < seeds[i].length; ++j) {
          seeds[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, Q, seeds)));
    }

    sc.close();
  }

  static BigInteger solve(int N, int Q, int[][] seeds) {
    int[] X = new int[N];
    for (int i = 0; i < X.length; ++i) {
      X[i] =
          (i <= 1)
              ? seeds[0][i]
              : (int)
                  (((long) seeds[0][2] * X[i - 1] + (long) seeds[0][3] * X[i - 2] + seeds[0][4])
                      % seeds[0][5]);
    }

    int[] Y = new int[N];
    for (int i = 0; i < Y.length; ++i) {
      Y[i] =
          (i <= 1)
              ? seeds[1][i]
              : (int)
                  (((long) seeds[1][2] * Y[i - 1] + (long) seeds[1][3] * Y[i - 2] + seeds[1][4])
                      % seeds[1][5]);
    }

    int[] Z = new int[Q];
    for (int i = 0; i < Z.length; ++i) {
      Z[i] =
          (i <= 1)
              ? seeds[2][i]
              : (int)
                  (((long) seeds[2][2] * Z[i - 1] + (long) seeds[2][3] * Z[i - 2] + seeds[2][4])
                      % seeds[2][5]);
    }

    int[] L = new int[N];
    int[] R = new int[N];
    for (int i = 0; i < N; ++i) {
      L[i] = Math.min(X[i], Y[i]) + 1;
      R[i] = Math.max(X[i], Y[i]) + 1;
    }
    int[] K = new int[Q];
    for (int i = 0; i < K.length; ++i) {
      K[i] = Z[i] + 1;
    }

    long totalNum = IntStream.range(0, N).map(i -> R[i] - L[i] + 1).asLongStream().sum();
    int[] values =
        IntStream.range(0, N)
            .flatMap(i -> IntStream.of(L[i], R[i] + 1))
            .boxed()
            .sorted()
            .mapToInt(x -> x)
            .distinct()
            .toArray();
    Map<Integer, Integer> valueToIndex =
        IntStream.range(0, values.length).boxed().collect(Collectors.toMap(i -> values[i], i -> i));
    int[] deltas = new int[values.length];
    for (int i = 0; i < N; ++i) {
      ++deltas[valueToIndex.get(L[i])];
      --deltas[valueToIndex.get(R[i] + 1)];
    }

    List<Range> ranges = new ArrayList<>();
    int count = 0;
    for (int i = 0; i < values.length - 1; ++i) {
      count += deltas[i];

      if (count != 0) {
        ranges.add(new Range(values[i], values[i + 1] - 1, count));
      }
    }

    long[] greaterNums = new long[ranges.size()];
    long greaterNum = 0;
    for (int i = greaterNums.length - 1; i >= 0; --i) {
      greaterNums[i] = greaterNum;

      Range range = ranges.get(i);
      greaterNum += (range.upper - range.lower + 1L) * range.count;
    }

    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < K.length; ++i) {
      result =
          result.add(
              BigInteger.valueOf((i + 1L) * computeKthScore(totalNum, ranges, greaterNums, K[i])));
    }

    return result;
  }

  static int computeKthScore(long totalNum, List<Range> ranges, long[] greaterNums, int Ki) {
    if (Ki > totalNum) {
      return 0;
    }

    int index = -1;
    int lowerIndex = 0;
    int upperIndex = ranges.size() - 1;
    while (lowerIndex <= upperIndex) {
      int middleIndex = (lowerIndex + upperIndex) / 2;
      Range range = ranges.get(middleIndex);
      if (greaterNums[middleIndex] + (range.upper - range.lower + 1L) * range.count >= Ki) {
        index = middleIndex;
        lowerIndex = middleIndex + 1;
      } else {
        upperIndex = middleIndex - 1;
      }
    }

    Range range = ranges.get(index);

    return (int) (range.upper - (Ki - greaterNums[index] - 1) / range.count);
  }
}

class Range {
  int lower;
  int upper;
  int count;

  Range(int lower, int upper, int count) {
    this.lower = lower;
    this.upper = upper;
    this.count = count;
  }
}