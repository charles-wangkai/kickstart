import java.math.BigInteger;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final int LIMIT_N = 400_000;

  static int[] lowers = new int[LIMIT_N * 2];
  static int[] uppers = new int[LIMIT_N * 2];
  static int[] counts = new int[LIMIT_N * 2];

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
            .mapToInt(Integer::intValue)
            .distinct()
            .toArray();
    Map<Integer, Integer> valueToIndex =
        IntStream.range(0, values.length).boxed().collect(Collectors.toMap(i -> values[i], i -> i));
    int[] deltas = new int[values.length];
    for (int i = 0; i < N; ++i) {
      ++deltas[valueToIndex.get(L[i])];
      --deltas[valueToIndex.get(R[i] + 1)];
    }

    int rangeCount = 0;
    int count = 0;
    for (int i = 0; i < values.length - 1; ++i) {
      count += deltas[i];

      if (count != 0) {
        lowers[rangeCount] = values[i];
        uppers[rangeCount] = values[i + 1] - 1;
        counts[rangeCount] = count;

        ++rangeCount;
      }
    }

    long[] greaterNums = new long[rangeCount];
    long greaterNum = 0;
    for (int i = greaterNums.length - 1; i >= 0; --i) {
      greaterNums[i] = greaterNum;

      greaterNum += (uppers[i] - lowers[i] + 1L) * counts[i];
    }

    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < K.length; ++i) {
      result =
          result.add(
              BigInteger.valueOf(
                  (i + 1L) * computeKthScore(totalNum, rangeCount, greaterNums, K[i])));
    }

    return result;
  }

  static int computeKthScore(long totalNum, int rangeCount, long[] greaterNums, int Ki) {
    if (Ki > totalNum) {
      return 0;
    }

    int index = -1;
    int lowerIndex = 0;
    int upperIndex = rangeCount - 1;
    while (lowerIndex <= upperIndex) {
      int middleIndex = (lowerIndex + upperIndex) / 2;
      if (greaterNums[middleIndex]
              + (uppers[middleIndex] - lowers[middleIndex] + 1L) * counts[middleIndex]
          >= Ki) {
        index = middleIndex;
        lowerIndex = middleIndex + 1;
      } else {
        upperIndex = middleIndex - 1;
      }
    }

    return (int) (uppers[index] - (Ki - greaterNums[index] - 1) / counts[index]);
  }
}
