import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[][] C = new int[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          C[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(C)));
    }

    sc.close();
  }

  static long solve(int[][] C) {
    int N = C.length;

    return Math.max(
        IntStream.range(0, N).mapToLong(c -> computeSum(C, 0, c)).max().getAsLong(),
        IntStream.range(0, N).mapToLong(r -> computeSum(C, r, 0)).max().getAsLong());
  }

  static long computeSum(int[][] C, int beginR, int beginC) {
    int N = C.length;

    long result = 0;
    for (int r = beginR, c = beginC; r < N && c < N; ++r, ++c) {
      result += C[r][c];
    }

    return result;
  }
}