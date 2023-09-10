import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      char[][] grid = new char[N][N];
      for (int r = 0; r < grid.length; ++r) {
        String line = sc.next();
        for (int c = 0; c < grid[r].length; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(grid)));
    }

    sc.close();
  }

  static int solve(char[][] grid) {
    return computeMinFlipNum(grid, 0, 0) + computeMinFlipNum(grid, 0, 1);
  }

  static int computeMinFlipNum(char[][] grid, int beginR, int beginC) {
    int result = computeFlipNum(clone(grid), beginR, beginC);

    flip(grid, beginR, beginC, 1, 1);
    int flipNum = computeFlipNum(grid, beginR, beginC);
    if (flipNum != Integer.MAX_VALUE) {
      result = Math.min(result, flipNum + 1);
    }

    return result;
  }

  static int computeFlipNum(char[][] grid, int beginR, int beginC) {
    int N = grid.length;

    int result = 0;
    int r = beginR;
    int c = beginC;
    while (isInGrid(N, r, c)) {
      if (grid[r][c] == '.') {
        flip(grid, r, c, 1, -1);
        flip(grid, r - 1, c + 1, -1, 1);
        ++result;
      }

      ++r;
      ++c;
    }

    r = beginR;
    c = beginC;
    while (isInGrid(N, r, c)) {
      if (grid[r][c] == '.') {
        if (!isAllSame(grid, '.', r, c, 1, 1)) {
          return Integer.MAX_VALUE;
        }

        flip(grid, r, c, 1, 1);
        ++result;
      } else if (!isAllSame(grid, '#', r, c, 1, 1)) {
        return Integer.MAX_VALUE;
      }

      ++r;
      --c;
      if (!isInGrid(N, r, c)) {
        c += 2;
      }
    }

    r = beginR;
    c = beginC;
    while (isInGrid(N, r, c)) {
      if (grid[r][c] == '.') {
        if (!isAllSame(grid, '.', r, c, 1, 1)) {
          return Integer.MAX_VALUE;
        }

        flip(grid, r, c, 1, 1);
        ++result;
      } else if (!isAllSame(grid, '#', r, c, 1, 1)) {
        return Integer.MAX_VALUE;
      }

      --r;
      ++c;
      if (!isInGrid(N, r, c)) {
        r += 2;
      }
    }

    return result;
  }

  static char[][] clone(char[][] grid) {
    int N = grid.length;

    char[][] result = new char[N][N];
    for (int r = 0; r < result.length; ++r) {
      result[r] = grid[r].clone();
    }

    return result;
  }

  static boolean isInGrid(int N, int r, int c) {
    return r >= 0 && r < N && c >= 0 && c < N;
  }

  static void flip(char[][] grid, int beginR, int beginC, int offsetR, int offsetC) {
    int N = grid.length;

    int r = beginR;
    int c = beginC;
    while (isInGrid(N, r, c)) {
      grid[r][c] = (char) ('.' + '#' - grid[r][c]);

      r += offsetR;
      c += offsetC;
    }
  }

  static boolean isAllSame(
      char[][] grid, char target, int beginR, int beginC, int offsetR, int offsetC) {
    int N = grid.length;

    int r = beginR;
    int c = beginC;
    while (r >= 0 && r < N && c >= 0 && c < N) {
      if (grid[r][c] != target) {
        return false;
      }

      r += offsetR;
      c += offsetC;
    }

    return true;
  }
}
