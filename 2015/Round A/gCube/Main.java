import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final double LIMIT = 1e9;
  static final int ITERATION_NUM = 100;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] a = new int[N];
      for (int i = 0; i < a.length; ++i) {
        a[i] = sc.nextInt();
      }
      int[] L = new int[M];
      int[] R = new int[M];
      for (int i = 0; i < M; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(a, L, R)));
    }

    sc.close();
  }

  static String solve(int[] a, int[] L, int[] R) {
    double[] prefixProductLogs = new double[a.length + 1];
    for (int i = 1; i < prefixProductLogs.length; ++i) {
      prefixProductLogs[i] = prefixProductLogs[i - 1] + Math.log(a[i - 1]);
    }

    return IntStream.range(0, L.length)
        .mapToObj(
            i ->
                String.format(
                    "%.9f",
                    computeRoot(
                        prefixProductLogs[R[i] + 1] - prefixProductLogs[L[i]], R[i] - L[i] + 1)))
        .collect(Collectors.joining("\n"));
  }

  static double computeRoot(double productLog, int exponent) {
    double lower = 0;
    double upper = LIMIT;
    for (int i = 0; i < ITERATION_NUM; ++i) {
      double middle = (lower + upper) / 2;
      if (Math.log(middle) * exponent < productLog) {
        lower = middle;
      } else {
        upper = middle;
      }
    }

    return lower;
  }
}
