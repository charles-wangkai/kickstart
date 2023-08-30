import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int LIMIT = 30;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, M)));
    }

    sc.close();
  }

  static int solve(int[] S, int M) {
    S = Arrays.stream(S).boxed().sorted(Comparator.reverseOrder()).mapToInt(x -> x).toArray();

    long[] counts = buildCounts(M);

    int result = 0;
    long[] currents = new long[LIMIT + 1];
    for (int exponent : S) {
      if (currents[exponent] == 0) {
        if (IntStream.range(exponent + 1, currents.length).allMatch(i -> currents[i] == 0)) {
          for (int i = 0; i <= LIMIT; ++i) {
            currents[i] += counts[i];
          }

          ++result;
        }

        if (currents[exponent] == 0) {
          int splitExponent = exponent + 1;
          while (currents[splitExponent] == 0) {
            ++splitExponent;
          }

          while (splitExponent != exponent) {
            --currents[splitExponent];
            currents[splitExponent - 1] += 4;
            --splitExponent;
          }
        }
      }

      --currents[exponent];
    }

    return result;
  }

  static long[] buildCounts(int M) {
    long[] counts = new long[LIMIT + 1];
    search(counts, M, 0);

    return counts;
  }

  static void search(long[] counts, int size, int baseIndex) {
    if (size % 2 == 0) {
      if (size != 0) {
        search(counts, size / 2, baseIndex + 1);
      }
    } else {
      counts[baseIndex] += size * 2L - 1;
      search(counts, size - 1, baseIndex);
    }
  }
}
