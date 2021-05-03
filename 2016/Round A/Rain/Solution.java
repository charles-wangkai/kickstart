import java.util.Comparator;
import java.util.PriorityQueue;
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
      int[][] H = new int[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          H[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(H)));
    }

    sc.close();
  }

  static int solve(int[][] H) {
    int R = H.length;
    int C = H[0].length;

    int[][] W = new int[R][C];
    PriorityQueue<Point> outers =
        new PriorityQueue<>(Comparator.comparing(outer -> W[outer.r][outer.c]));
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (r == 0 || r == R - 1 || c == 0 || c == C - 1) {
          W[r][c] = H[r][c];
          outers.add(new Point(r, c));
        }
      }
    }

    while (!outers.isEmpty()) {
      Point outer = outers.poll();
      fill(W, H, outers, W[outer.r][outer.c], outer.r, outer.c);
    }

    int result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        result += W[r][c] - H[r][c];
      }
    }

    return result;
  }

  static void fill(int[][] W, int[][] H, PriorityQueue<Point> outers, int height, int r, int c) {
    int R = H.length;
    int C = H[0].length;

    if (H[r][c] > height) {
      W[r][c] = H[r][c];
      outers.add(new Point(r, c));
    } else {
      W[r][c] = height;

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = r + R_OFFSETS[i];
        int adjC = c + C_OFFSETS[i];
        if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C && W[adjR][adjC] == 0) {
          fill(W, H, outers, height, adjR, adjC);
        }
      }
    }
  }
}

class Point {
  int r;
  int c;

  Point(int r, int c) {
    this.r = r;
    this.c = c;
  }
}
