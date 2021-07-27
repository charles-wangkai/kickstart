import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[][] matrix = new int[N][M];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < M; ++c) {
          matrix[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(matrix)));
    }

    sc.close();
  }

  static int solve(int[][] matrix) {
    int N = matrix.length;
    int M = matrix[0].length;

    int[][][][] mins = new int[N][M][N][M];
    for (int beginR = 0; beginR < N; ++beginR) {
      for (int beginC = 0; beginC < M; ++beginC) {
        for (int endR = beginR; endR < N; ++endR) {
          for (int endC = beginC; endC < M; ++endC) {
            mins[beginR][beginC][endR][endC] =
                Math.min(
                    Math.min(
                        getMin(mins, beginR, beginC, endR - 1, endC),
                        getMin(mins, beginR, beginC, endR, endC - 1)),
                    matrix[endR][endC]);
          }
        }
      }
    }

    int[][][][] coinNums = new int[N][M][N][M];
    for (int height = 1; height <= N; ++height) {
      for (int width = 1; width <= M; ++width) {
        for (int beginR = 0; beginR + height - 1 < N; ++beginR) {
          int endR = beginR + height - 1;
          for (int beginC = 0; beginC + width - 1 < M; ++beginC) {
            int endC = beginC + width - 1;

            for (int middleR = beginR; middleR < endR; ++middleR) {
              coinNums[beginR][beginC][endR][endC] =
                  Math.max(
                      coinNums[beginR][beginC][endR][endC],
                      mins[beginR][beginC][endR][endC]
                          + coinNums[beginR][beginC][middleR][endC]
                          + coinNums[middleR + 1][beginC][endR][endC]);
            }
            for (int middleC = beginC; middleC < endC; ++middleC) {
              coinNums[beginR][beginC][endR][endC] =
                  Math.max(
                      coinNums[beginR][beginC][endR][endC],
                      mins[beginR][beginC][endR][endC]
                          + coinNums[beginR][beginC][endR][middleC]
                          + coinNums[beginR][middleC + 1][endR][endC]);
            }
          }
        }
      }
    }

    return coinNums[0][0][N - 1][M - 1];
  }

  static int getMin(int[][][][] mins, int beginR, int beginC, int endR, int endC) {
    return (beginR <= endR && beginC <= endC)
        ? mins[beginR][beginC][endR][endC]
        : Integer.MAX_VALUE;
  }
}
