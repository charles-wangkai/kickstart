import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[][] matrix = new int[N * N][N * N];
      for (int r = 0; r < N * N; ++r) {
        for (int c = 0; c < N * N; ++c) {
          matrix[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, matrix) ? "Yes" : "No"));
    }

    sc.close();
  }

  static boolean solve(int N, int[][] matrix) {
    return Arrays.stream(matrix).allMatch(Solution::isPermutation)
        && IntStream.range(0, N * N)
            .allMatch(
                c -> isPermutation(IntStream.range(0, N * N).map(r -> matrix[r][c]).toArray()))
        && IntStream.range(0, N)
            .allMatch(
                i ->
                    IntStream.range(0, N)
                        .allMatch(
                            j ->
                                isPermutation(
                                    IntStream.range(0, N)
                                        .flatMap(
                                            p ->
                                                IntStream.range(0, N)
                                                    .map(q -> matrix[i * N + p][j * N + q]))
                                        .toArray())));
  }

  static boolean isPermutation(int[] values) {
    return Arrays.stream(values).allMatch(x -> x <= values.length)
        && Arrays.stream(values).distinct().count() == values.length;
  }
}
