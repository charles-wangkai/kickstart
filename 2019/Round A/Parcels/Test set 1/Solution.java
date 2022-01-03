import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] grid = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(grid)));
    }

    sc.close();
  }

  static int solve(char[][] grid) {
    int R = grid.length;
    int C = grid[0].length;

    int result = computeMaxDistance(grid);
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (grid[r][c] == '0') {
          grid[r][c] = '1';
          result = Math.min(result, computeMaxDistance(grid));

          grid[r][c] = '0';
        }
      }
    }

    return result;
  }

  static int computeMaxDistance(char[][] grid) {
    int R = grid.length;
    int C = grid[0].length;

    int result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < R; ++i) {
          for (int j = 0; j < C; ++j) {
            if (grid[i][j] == '1') {
              distance = Math.min(distance, Math.abs(r - i) + Math.abs(c - j));
            }
          }
        }

        result = Math.max(result, distance);
      }
    }

    return result;
  }
}
