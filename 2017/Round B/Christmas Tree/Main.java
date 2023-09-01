import java.util.Arrays;
import java.util.Scanner;

public class Main {
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

    int[][] rightGreenNums = new int[N][M];
    for (int r = 0; r < N; ++r) {
      for (int c = M - 1; c >= 0; --c) {
        if (grid[r][c] == '#') {
          rightGreenNums[r][c] = 1 + ((c + 1 == M) ? 0 : rightGreenNums[r][c + 1]);
        }
      }
    }

    int[][][] dp = new int[K + 1][N + 1][M];
    for (int i = 0; i <= K; ++i) {
      for (int j = 0; j <= N; ++j) {
        Arrays.fill(dp[i][j], -1);
      }
    }

    int result = -1;
    for (int r = 0; r < N; ++r) {
      int[][][] nextDp = new int[K + 1][N + 1][M];
      for (int i = 0; i <= K; ++i) {
        for (int j = 0; j <= N; ++j) {
          Arrays.fill(nextDp[i][j], -1);
        }
      }

      for (int c = 0; c < M; ++c) {
        for (int i = 1; i <= K; ++i) {
          for (int j = 1; j <= r + 1; ++j) {
            if (rightGreenNums[r][c] >= 2 * j - 1) {
              if (j == 1) {
                if (i == 1) {
                  nextDp[i][j][c] = 1;
                } else {
                  for (int prevJ = 1; prevJ <= r; ++prevJ) {
                    for (int prevC = Math.max(0, c - (2 * prevJ - 1) + 1); prevC <= c; ++prevC) {
                      if (dp[i - 1][prevJ][prevC] != -1) {
                        nextDp[i][j][c] = Math.max(nextDp[i][j][c], dp[i - 1][prevJ][prevC] + 1);
                      }
                    }
                  }
                }
              } else if (dp[i][j - 1][c + 1] != -1) {
                nextDp[i][j][c] = Math.max(nextDp[i][j][c], dp[i][j - 1][c + 1] + 2 * j - 1);
              }
            }
          }
        }
      }

      dp = nextDp;
      for (int j = 0; j <= N; ++j) {
        for (int c = 0; c < M; ++c) {
          result = Math.max(result, dp[K][j][c]);
        }
      }
    }

    return (result == -1) ? 0 : result;
  }
}
