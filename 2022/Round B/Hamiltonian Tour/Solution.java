import java.util.Scanner;

public class Solution {
  static final char[] MOVES = {'E', 'S', 'W', 'N'};
  static final int[] R_OFFSETS = {0, 1, 0, -1};
  static final int[] C_OFFSETS = {1, 0, -1, 0};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] B = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          B[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(B)));
    }

    sc.close();
  }

  static String solve(char[][] B) {
    int R = B.length;
    int C = B[0].length;

    StringBuilder path = new StringBuilder();
    boolean[][] visited = new boolean[R][C];
    search(B, visited, 0, 0, 0, path);

    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (B[r][c] == '*' && !visited[r][c]) {
          return "IMPOSSIBLE";
        }
      }
    }
    path.append('N');

    return path.toString();
  }

  static void search(
      char[][] B, boolean[][] visited, int r, int c, int direction, StringBuilder path) {
    int R = B.length;
    int C = B[0].length;

    visited[r][c] = true;

    for (int i = -1; i <= 1; ++i) {
      int nextDirection = Math.floorMod(direction + i, MOVES.length);
      int nextR = r + R_OFFSETS[nextDirection];
      int nextC = c + C_OFFSETS[nextDirection];
      if (nextR >= 0
          && nextR < R
          && nextC >= 0
          && nextC < C
          && B[nextR][nextC] == '*'
          && !visited[nextR][nextC]) {
        path.append(MOVES[nextDirection]);
        search(B, visited, nextR, nextC, nextDirection, path);
        path.append(MOVES[Math.floorMod(nextDirection + 2, MOVES.length)]);
      } else {
        path.append(MOVES[Math.floorMod(nextDirection + 1, MOVES.length)]);
      }
    }
  }
}