import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] values1 = new int[4];
      int[] values2 = new int[4];
      int[] A = new int[4];
      int[] B = new int[4];
      int[] C = new int[4];
      int[] M = new int[4];
      for (int i = 0; i < 4; ++i) {
        values1[i] = sc.nextInt();
        values2[i] = sc.nextInt();
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
        C[i] = sc.nextInt();
        M[i] = sc.nextInt();
      }

      System.out.println(
          String.format("Case #%d: %d", tc, solve(N, K, values1, values2, A, B, C, M)));
    }

    sc.close();
  }

  static int solve(int N, int K, int[] values1, int[] values2, int[] A, int[] B, int[] C, int[] M) {
    int[] p = buildValues(N, values1[0], values2[0], A[0], B[0], C[0], M[0]);
    int[] h = buildValues(N, values1[1], values2[1], A[1], B[1], C[1], M[1]);
    int[] x = buildValues(K, values1[2], values2[2], A[2], B[2], C[2], M[2]);
    int[] y = buildValues(K, values1[3], values2[3], A[3], B[3], C[3], M[3]);

    return (int)
        IntStream.range(0, K)
            .filter(i -> IntStream.range(0, N).anyMatch(j -> h[j] - Math.abs(x[i] - p[j]) >= y[i]))
            .count();
  }

  static int[] buildValues(int size, int value1, int value2, int a, int b, int c, int m) {
    int[] values = new int[size];
    values[0] = value1;
    values[1] = value2;
    for (int i = 2; i < values.length; ++i) {
      values[i] = (int) (((long) a * values[i - 1] + (long) b * values[i - 2] + c) % m) + 1;
    }

    return values;
  }
}
