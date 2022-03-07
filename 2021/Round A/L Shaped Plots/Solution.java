import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int[][] grid = new int[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          grid[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(grid)));
    }

    sc.close();
  }

  static long solve(int[][] grid) {
    int R = grid.length;
    int C = grid[0].length;

    int[][] upLengths = new int[R][C];
    for (int c = 0; c < C; ++c) {
      for (int r = 0; r < R; ++r) {
        if (grid[r][c] == 1) {
          upLengths[r][c] = ((r == 0) ? 0 : upLengths[r - 1][c]) + 1;
        }
      }
    }

    int[][] downLengths = new int[R][C];
    for (int c = 0; c < C; ++c) {
      for (int r = R - 1; r >= 0; --r) {
        if (grid[r][c] == 1) {
          downLengths[r][c] = ((r == R - 1) ? 0 : downLengths[r + 1][c]) + 1;
        }
      }
    }

    int[][] leftLengths = new int[R][C];
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (grid[r][c] == 1) {
          leftLengths[r][c] = ((c == 0) ? 0 : leftLengths[r][c - 1]) + 1;
        }
      }
    }

    int[][] rightLengths = new int[R][C];
    for (int r = 0; r < R; ++r) {
      for (int c = C - 1; c >= 0; --c) {
        if (grid[r][c] == 1) {
          rightLengths[r][c] = ((c == C - 1) ? 0 : rightLengths[r][c + 1]) + 1;
        }
      }
    }

    return IntStream.range(0, R)
        .map(
            r ->
                IntStream.range(0, C)
                    .map(
                        c ->
                            computeLShapeNum(upLengths[r][c], rightLengths[r][c])
                                + computeLShapeNum(rightLengths[r][c], downLengths[r][c])
                                + computeLShapeNum(downLengths[r][c], leftLengths[r][c])
                                + computeLShapeNum(leftLengths[r][c], upLengths[r][c]))
                    .sum())
        .asLongStream()
        .sum();
  }

  static int computeLShapeNum(int length1, int length2) {
    return Math.max(0, Math.min(length1, length2 / 2) - 1)
        + Math.max(0, Math.min(length1 / 2, length2) - 1);
  }
}