import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] V = new int[N];
      for (int i = 0; i < V.length; ++i) {
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(V, K)));
    }

    sc.close();
  }

  static double solve(int[] V, int K) {
    int N = V.length;

    int[] sorted =
        Arrays.stream(V).boxed().sorted(Comparator.reverseOrder()).mapToInt(x -> x).toArray();

    double result = Arrays.stream(V).average().getAsDouble();
    for (int i = 0; i < K; ++i) {
      double next = -1;
      long sum = 0;
      for (int j = 0; j < sorted.length; ++j) {
        sum += sorted[j];
        if (sum / (j + 1.0) < result) {
          break;
        }

        next = Math.max(next, (double) sum / N + (1 - (j + 1.0) / N) * result);
      }

      result = next;
    }

    return result;
  }
}
