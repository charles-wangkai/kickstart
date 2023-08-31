import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] values = new int[N];
      for (int i = 0; i < values.length; ++i) {
        values[i] = sc.nextInt();
      }
      long[] L = new long[Q];
      long[] R = new long[Q];
      for (int i = 0; i < Q; ++i) {
        L[i] = sc.nextLong();
        R[i] = sc.nextLong();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(values, L, R)));
    }

    sc.close();
  }

  static String solve(int[] values, long[] L, long[] R) {
    int[] prefixSums = new int[values.length];
    for (int i = 0; i < prefixSums.length; ++i) {
      prefixSums[i] = ((i == 0) ? 0 : prefixSums[i - 1]) + values[i];
    }

    long[] prefixSumOfSums = new long[prefixSums.length];
    for (int i = 0; i < prefixSumOfSums.length; ++i) {
      prefixSumOfSums[i] = ((i == 0) ? 0 : prefixSumOfSums[i - 1]) + prefixSums[i];
    }

    return IntStream.range(0, L.length)
        .mapToObj(
            i ->
                String.valueOf(
                    computePrefixSumOfSum(prefixSums, prefixSumOfSums, R[i])
                        - computePrefixSumOfSum(prefixSums, prefixSumOfSums, L[i] - 1)))
        .collect(Collectors.joining("\n"));
  }

  static long computePrefixSumOfSum(int[] prefixSums, long[] prefixSumOfSums, long id) {
    if (id == 0) {
      return 0;
    }

    int lastValue = findValue(prefixSums, id);
    long lessNum = computePrefixNum(prefixSums, x -> x < lastValue);

    return computeLessPrefixSum(prefixSums, prefixSumOfSums, lastValue)
        + lastValue * (id - lessNum);
  }

  static long computeLessPrefixSum(int[] prefixSums, long[] prefixSumOfSums, int lastValue) {
    long result = 0;
    for (int beginIndex = 0; beginIndex < prefixSums.length; ++beginIndex) {
      int endIndex = beginIndex - 1;
      int lower = beginIndex;
      int upper = prefixSums.length - 1;
      while (lower <= upper) {
        int middle = (lower + upper) / 2;
        if (prefixSums[middle] - ((beginIndex == 0) ? 0 : prefixSums[beginIndex - 1]) < lastValue) {
          endIndex = middle;
          lower = middle + 1;
        } else {
          upper = middle - 1;
        }
      }

      if (endIndex != beginIndex - 1) {
        result +=
            prefixSumOfSums[endIndex]
                - ((beginIndex == 0) ? 0 : prefixSumOfSums[beginIndex - 1])
                - (endIndex - beginIndex + 1L)
                    * ((beginIndex == 0) ? 0 : prefixSums[beginIndex - 1]);
      }
    }

    return result;
  }

  static int findValue(int[] prefixSums, long id) {
    int result = -1;
    int lower =
        IntStream.range(0, prefixSums.length - 1)
            .map(i -> prefixSums[i + 1] - prefixSums[i])
            .min()
            .getAsInt();
    int upper = prefixSums[prefixSums.length - 1];
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (computePrefixNum(prefixSums, x -> x <= middle) >= id) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }

  static long computePrefixNum(int[] prefixSums, Checker checker) {
    long result = 0;
    for (int beginIndex = 0; beginIndex < prefixSums.length; ++beginIndex) {
      int endIndex = beginIndex - 1;
      int lower = beginIndex;
      int upper = prefixSums.length - 1;
      while (lower <= upper) {
        int middle = (lower + upper) / 2;
        if (checker.check(
            prefixSums[middle] - ((beginIndex == 0) ? 0 : prefixSums[beginIndex - 1]))) {
          endIndex = middle;
          lower = middle + 1;
        } else {
          upper = middle - 1;
        }
      }

      result += endIndex - beginIndex + 1;
    }

    return result;
  }
}

interface Checker {
  boolean check(int x);
}
