import java.util.Arrays;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int O = sc.nextInt();
      long D = sc.nextLong();
      int X1 = sc.nextInt();
      int X2 = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();
      int M = sc.nextInt();
      int L = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(N, O, D, X1, X2, A, B, C, M, L)));
    }

    sc.close();
  }

  static String solve(int N, int O, long D, int X1, int X2, int A, int B, int C, int M, int L) {
    int[] X = new int[N];
    X[0] = X1;
    X[1] = X2;
    for (int i = 2; i < X.length; ++i) {
      X[i] = (int) (((long) A * X[i - 1] + (long) B * X[i - 2] + C) % M);
    }

    int[] S = Arrays.stream(X).map(x -> x + L).toArray();

    long[] prefixSums = new long[S.length];
    for (int i = 0; i < prefixSums.length; ++i) {
      prefixSums[i] = ((i == 0) ? 0 : prefixSums[i - 1]) + S[i];
    }

    NavigableMap<Long, Integer> prefixSumToCount = new TreeMap<>();
    long maxSum = Long.MIN_VALUE;
    int endIndex = -1;
    int oddCount = 0;
    for (int beginIndex = 0; beginIndex < S.length; ++beginIndex) {
      while (endIndex != S.length - 1
          && (endIndex + 1 < beginIndex || oddCount + ((S[endIndex + 1] % 2 == 0) ? 0 : 1) <= O)) {
        ++endIndex;
        updateMap(prefixSumToCount, prefixSums[endIndex], 1);
        oddCount += (S[endIndex] % 2 == 0) ? 0 : 1;
      }

      if (endIndex >= beginIndex) {
        long limit = ((beginIndex == 0) ? 0 : prefixSums[beginIndex - 1]) + D;
        Long endPrefixSum = prefixSumToCount.floorKey(limit);
        if (endPrefixSum != null) {
          maxSum =
              Math.max(maxSum, endPrefixSum - ((beginIndex == 0) ? 0 : prefixSums[beginIndex - 1]));
        }
      }

      updateMap(prefixSumToCount, prefixSums[beginIndex], -1);
      oddCount -= (S[beginIndex] % 2 == 0) ? 0 : 1;
    }

    return (maxSum == Long.MIN_VALUE) ? "IMPOSSIBLE" : String.valueOf(maxSum);
  }

  static void updateMap(NavigableMap<Long, Integer> prefixSumToCount, long prefixSum, int delta) {
    prefixSumToCount.put(prefixSum, prefixSumToCount.getOrDefault(prefixSum, 0) + delta);
    prefixSumToCount.remove(prefixSum, 0);
  }
}
