import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      char[][] board = new char[N][N];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < N; ++c) {
          board[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(board)));
    }

    sc.close();
  }

  static int solve(char[][] board) {
    int N = board.length;

    int[][] values = new int[N][N];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (board[r][c] == '*') {
          for (int dr = -1; dr <= 1; ++dr) {
            for (int dc = -1; dc <= 1; ++dc) {
              int adjR = r + dr;
              int adjC = c + dc;
              if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < N) {
                ++values[adjR][adjC];
              }
            }
          }
        }
      }
    }

    int result = 0;
    boolean[][] visited = new boolean[N][N];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (values[r][c] == 0 && !visited[r][c]) {
          search(values, visited, r, c);
          ++result;
        }
      }
    }
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (board[r][c] == '.' && !visited[r][c]) {
          ++result;
        }
      }
    }

    return result;
  }

  static void search(int[][] values, boolean[][] visited, int r, int c) {
    int N = values.length;

    visited[r][c] = true;
    for (int dr = -1; dr <= 1; ++dr) {
      for (int dc = -1; dc <= 1; ++dc) {
        int adjR = r + dr;
        int adjC = c + dc;
        if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < N && !visited[adjR][adjC]) {
          if (values[adjR][adjC] == 0) {
            search(values, visited, adjR, adjC);
          } else {
            visited[adjR][adjC] = true;
          }
        }
      }
    }
  }
}
