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

    double avg = Arrays.stream(V).average().getAsDouble();

    if (K == 0) {
      return avg;
    }

    int[] sorted =
        Arrays.stream(V).boxed().sorted(Comparator.reverseOrder()).mapToInt(x -> x).toArray();

    double result = -1;
    long sum = 0;
    for (int i = 0; i < sorted.length; ++i) {
      sum += sorted[i];
      result = Math.max(result, (double) sum / N + (1 - (i + 1.0) / N) * avg);
    }

    return result;
  }
}
