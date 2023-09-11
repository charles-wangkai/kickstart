import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int[] P = new int[N - 1];
      for (int i = 0; i < P.length; ++i) {
        P[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(P, A, B)));
    }

    sc.close();
  }

  static double solve(int[] P, int A, int B) {
    int N = P.length + 1;

    int[] parents = new int[N];
    parents[0] = -1;
    for (int i = 1; i < parents.length; ++i) {
      parents[i] = P[i - 1] - 1;
    }

    int[] aSubCounts = buildSubCounts(parents, A);
    int[] bSubCounts = buildSubCounts(parents, B);

    return IntStream.range(0, N)
        .mapToDouble(
            i ->
                (double) aSubCounts[i] / N
                    + (double) bSubCounts[i] / N
                    - ((double) aSubCounts[i] / N) * ((double) bSubCounts[i] / N))
        .sum();
  }

  static int[] buildSubCounts(int[] parents, int step) {
    int[] stepParents = IntStream.range(0, parents.length).toArray();
    while (step != 0) {
      if (step % 2 != 0) {
        stepParents = combine(stepParents, parents);
      }

      parents = combine(parents, parents);
      step /= 2;
    }

    int[] result = new int[parents.length];
    Arrays.fill(result, 1);
    for (int i = result.length - 1; i >= 0; --i) {
      if (stepParents[i] != -1) {
        result[stepParents[i]] += result[i];
      }
    }

    return result;
  }

  static int[] combine(int[] parents1, int[] parents2) {
    return IntStream.range(0, parents1.length)
        .map(i -> (parents1[i] == -1) ? -1 : parents2[parents1[i]])
        .toArray();
  }
}
