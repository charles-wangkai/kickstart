import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      int[] B = new int[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }
      int P = sc.nextInt();
      int[] C = new int[P];
      for (int i = 0; i < C.length; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, B, C)));
    }

    sc.close();
  }

  static String solve(int[] A, int[] B, int[] C) {
    return Arrays.stream(C)
        .mapToObj(
            ci ->
                String.valueOf(
                    IntStream.range(0, A.length).filter(i -> A[i] <= ci && B[i] >= ci).count()))
        .collect(Collectors.joining(" "));
  }
}
