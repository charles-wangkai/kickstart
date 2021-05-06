import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int K = sc.nextInt();
      int[][] grid = new int[R][C];
      for (int i = 0; i < K; ++i) {
        int r = sc.nextInt();
        int c = sc.nextInt();
        grid[r][c] = 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(grid)));
    }

    sc.close();
  }

  static long solve(int[][] grid) {
    int R = grid.length;
    int C = grid[0].length;

    int[][] prefixSums = new int[R + 1][C + 1];
    for (int r = 1; r <= R; ++r) {
      for (int c = 1; c <= C; ++c) {
        prefixSums[r][c] =
            prefixSums[r - 1][c]
                + prefixSums[r][c - 1]
                - prefixSums[r - 1][c - 1]
                + grid[r - 1][c - 1];
      }
    }

    long result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        int size = -1;
        int lower = 0;
        int upper = Math.min(R, C);
        while (lower <= upper) {
          int middle = (lower + upper) / 2;
          if (check(prefixSums, r, c, middle)) {
            size = middle;
            lower = middle + 1;
          } else {
            upper = middle - 1;
          }
        }

        result += size;
      }
    }

    return result;
  }

  static boolean check(int[][] prefixSums, int minR, int minC, int size) {
    int R = prefixSums.length - 1;
    int C = prefixSums[0].length - 1;

    int maxR = minR + size - 1;
    int maxC = minC + size - 1;

    return maxR < R && maxC < C && computeRangeSum(prefixSums, minR, minC, maxR, maxC) == 0;
  }

  static int computeRangeSum(int[][] prefixSums, int minR, int minC, int maxR, int maxC) {
    return prefixSums[maxR + 1][maxC + 1]
        - prefixSums[minR][maxC + 1]
        - prefixSums[maxR + 1][minC]
        + prefixSums[minR][minC];
  }
}
