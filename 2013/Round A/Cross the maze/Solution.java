import java.util.Scanner;

public class Solution {
  static final int STEP_LIMIT = 10000;
  static final char[] MOVES = {'N', 'E', 'S', 'W'};
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      char[][] cells = new char[N][N];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < N; ++c) {
          cells[r][c] = line.charAt(c);
        }
      }
      int sx = sc.nextInt() - 1;
      int sy = sc.nextInt() - 1;
      int ex = sc.nextInt() - 1;
      int ey = sc.nextInt() - 1;

      System.out.println(String.format("Case #%d: %s", tc, solve(cells, sx, sy, ex, ey)));
    }

    sc.close();
  }

  static String solve(char[][] cells, int sx, int sy, int ex, int ey) {
    int N = cells.length;

    StringBuilder path = new StringBuilder();
    int direction = findDirection(N, sx, sy);
    int x = sx;
    int y = sy;

    while (true) {
      direction = findNextDirection(cells, x, y, direction);
      if (path.length() > STEP_LIMIT || direction == -1) {
        return "Edison ran out of energy.";
      }

      path.append(MOVES[direction]);
      x += R_OFFSETS[direction];
      y += C_OFFSETS[direction];

      if (x == ex && y == ey) {
        return String.format("%d\n%s", path.length(), path);
      }
    }
  }

  static int findNextDirection(char[][] cells, int x, int y, int direction) {
    if (isWall(cells, x + R_OFFSETS[(direction + 3) % 4], y + C_OFFSETS[(direction + 3) % 4])) {
      if (isEmpty(cells, x + R_OFFSETS[direction], y + C_OFFSETS[direction])) {
        return direction;
      } else if (isEmpty(
          cells, x + R_OFFSETS[(direction + 1) % 4], y + C_OFFSETS[(direction + 1) % 4])) {
        return (direction + 1) % 4;
      } else if (isEmpty(
          cells, x + R_OFFSETS[(direction + 2) % 4], y + C_OFFSETS[(direction + 2) % 4])) {
        return (direction + 2) % 4;
      }
    } else if (isEmpty(
        cells, x + R_OFFSETS[(direction + 3) % 4], y + C_OFFSETS[(direction + 3) % 4])) {
      return (direction + 3) % 4;
    }

    return -1;
  }

  static boolean isWall(char[][] cells, int x, int y) {
    int N = cells.length;

    return x == -1 || x == N || y == -1 || y == N || cells[x][y] == '#';
  }

  static boolean isEmpty(char[][] cells, int x, int y) {
    int N = cells.length;

    return x >= 0 && x < N && y >= 0 && y < N && cells[x][y] == '.';
  }

  static int findDirection(int N, int sx, int sy) {
    if (sx == 0) {
      if (sy == 0) {
        return 1;
      } else {
        return 3;
      }
    } else {
      if (sy == 0) {
        return 0;
      } else {
        return 2;
      }
    }
  }
}
