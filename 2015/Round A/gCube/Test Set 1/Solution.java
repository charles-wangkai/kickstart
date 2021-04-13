import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
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
    double[] prefixProducts = new double[a.length + 1];
    prefixProducts[0] = 1;
    for (int i = 1; i < prefixProducts.length; ++i) {
      prefixProducts[i] = prefixProducts[i - 1] * a[i - 1];
    }

    return IntStream.range(0, L.length)
        .mapToObj(
            i ->
                String.format(
                    "%.9f",
                    Math.pow(
                        prefixProducts[R[i] + 1] / prefixProducts[L[i]], 1.0 / (R[i] - L[i] + 1))))
        .collect(Collectors.joining("\n"));
  }
}
