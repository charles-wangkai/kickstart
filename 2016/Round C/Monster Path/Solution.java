import java.util.Scanner;

public class Solution {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int Rs = sc.nextInt();
      int Cs = sc.nextInt();
      int S = sc.nextInt();
      double P = sc.nextDouble();
      double Q = sc.nextDouble();
      char[][] grid = new char[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          grid[r][c] = sc.next().charAt(0);
        }
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(grid, Rs, Cs, S, P, Q)));
    }

    sc.close();
  }

  static double solve(char[][] grid, int Rs, int Cs, int S, double P, double Q) {
    int R = grid.length;
    int C = grid[0].length;

    double[][] probs = new double[R][C];
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        probs[r][c] = (grid[r][c] == 'A') ? P : Q;
      }
    }

    return search(grid, probs, P, Q, Rs, Cs, S);
  }

  static double search(
      char[][] grid, double[][] probs, double P, double Q, int r, int c, int rest) {
    int R = grid.length;
    int C = grid[0].length;

    if (rest == 0) {
      return 0;
    }

    double result = 0;
    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int adjR = r + R_OFFSETS[i];
      int adjC = c + C_OFFSETS[i];
      if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
        double old = probs[adjR][adjC];
        probs[adjR][adjC] *= 1 - ((grid[adjR][adjC] == 'A') ? P : Q);

        result = Math.max(result, old + search(grid, probs, P, Q, adjR, adjC, rest - 1));

        probs[adjR][adjC] = old;
      }
    }

    return result;
  }
}
