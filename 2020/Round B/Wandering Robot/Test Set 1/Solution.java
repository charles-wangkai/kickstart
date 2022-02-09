import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int H = sc.nextInt();
      int L = sc.nextInt() - 1;
      int U = sc.nextInt() - 1;
      int R = sc.nextInt() - 1;
      int D = sc.nextInt() - 1;

      System.out.println(String.format("Case #%d: %.9f", tc, solve(W, H, L, U, R, D)));
    }

    sc.close();
  }

  static double solve(int W, int H, int L, int U, int R, int D) {
    double[][] p = new double[H][W];
    p[0][0] = 1;
    for (int r = 0; r < H; ++r) {
      for (int c = 0; c < W; ++c) {
        if (c == W - 1) {
          if (r != H - 1 && !isInHole(L, U, R, D, r + 1, c)) {
            p[r + 1][c] += p[r][c];
          }
        } else if (r == H - 1) {
          if (!isInHole(L, U, R, D, r, c + 1)) {
            p[r][c + 1] += p[r][c];
          }
        } else {
          if (!isInHole(L, U, R, D, r + 1, c)) {
            p[r + 1][c] += p[r][c] / 2;
          }
          if (!isInHole(L, U, R, D, r, c + 1)) {
            p[r][c + 1] += p[r][c] / 2;
          }
        }
      }
    }

    return p[H - 1][W - 1];
  }

  static boolean isInHole(int L, int U, int R, int D, int r, int c) {
    return r >= U && r <= D && c >= L && c <= R;
  }
}