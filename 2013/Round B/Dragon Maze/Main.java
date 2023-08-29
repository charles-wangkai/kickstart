import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int enX = sc.nextInt();
      int enY = sc.nextInt();
      int exX = sc.nextInt();
      int exY = sc.nextInt();
      int[][] cells = new int[N][M];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < M; ++c) {
          cells[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(cells, enX, enY, exX, exY)));
    }

    sc.close();
  }

  static String solve(int[][] cells, int enX, int enY, int exX, int exY) {
    int N = cells.length;
    int M = cells[0].length;

    int[][] distances = new int[N][M];
    for (int r = 0; r < N; ++r) {
      Arrays.fill(distances[r], Integer.MAX_VALUE);
    }
    distances[enX][enY] = 0;

    int[][] powers = new int[N][M];
    for (int r = 0; r < N; ++r) {
      Arrays.fill(powers[r], -1);
    }

    Queue<Point> queue = new ArrayDeque<>();
    queue.offer(new Point(enX, enY));

    while (!queue.isEmpty()) {
      Point head = queue.poll();

      powers[head.r][head.c] = cells[head.r][head.c];
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];
        if (adjR >= 0
            && adjR < N
            && adjC >= 0
            && adjC < M
            && distances[adjR][adjC] == distances[head.r][head.c] - 1) {
          powers[head.r][head.c] =
              Math.max(powers[head.r][head.c], powers[adjR][adjC] + cells[head.r][head.c]);
        }
      }

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];
        if (adjR >= 0
            && adjR < N
            && adjC >= 0
            && adjC < M
            && cells[adjR][adjC] != -1
            && distances[adjR][adjC] == Integer.MAX_VALUE) {
          distances[adjR][adjC] = distances[head.r][head.c] + 1;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    return (distances[exX][exY] == Integer.MAX_VALUE)
        ? "Mission Impossible."
        : String.valueOf(powers[exX][exY]);
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
