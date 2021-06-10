import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      char[][] grid = new char[N][M];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < M; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(grid, K)));
    }

    sc.close();
  }

  static int solve(char[][] grid, int K) {
    int N = grid.length;
    int M = grid[0].length;

    int result = 0;
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        result = Math.max(result, findMaxTriangle(grid, r, c));
      }
    }

    return result;
  }

  static int findMaxTriangle(char[][] grid, int topR, int topC) {
    int N = grid.length;
    int M = grid[0].length;

    int result = 0;
    for (int r = topR; r < N; ++r) {
      int minC = topC - (r - topR);
      int maxC = topC + (r - topR);
      if (!(minC >= 0 && maxC < M && isAllGreen(grid[r], minC, maxC))) {
        break;
      }

      result += maxC - minC + 1;
    }

    return result;
  }

  static boolean isAllGreen(char[] line, int minC, int maxC) {
    for (int c = minC; c <= maxC; ++c) {
      if (line[c] == '.') {
        return false;
      }
    }

    return true;
  }
}
