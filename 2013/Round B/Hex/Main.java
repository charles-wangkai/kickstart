import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int[] R_OFFSETS = {-1, -1, 0, 0, 1, 1};
  static final int[] C_OFFSETS = {0, 1, -1, 1, -1, 0};

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

      System.out.println(String.format("Case #%d: %s", tc, solve(board)));
    }

    sc.close();
  }

  static String solve(char[][] board) {
    int N = board.length;

    int bCount = 0;
    int rCount = 0;
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (board[r][c] == 'B') {
          ++bCount;
        } else if (board[r][c] == 'R') {
          ++rCount;
        }
      }
    }

    boolean bConnected = isBConnected(board);
    boolean bCuttable = isBCuttable(board);
    boolean rConnected = isRConnected(board);
    boolean rCuttable = isRCuttable(board);

    if (Math.abs(bCount - rCount) >= 2
        || (bConnected && rConnected)
        || (bConnected && (!bCuttable || bCount < rCount))
        || (rConnected && (!rCuttable || rCount < bCount))) {
      return "Impossible";
    } else if (bConnected) {
      return "Blue wins";
    } else if (rConnected) {
      return "Red wins";
    }

    return "Nobody wins";
  }

  static boolean isBCuttable(char[][] board) {
    int N = board.length;

    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (board[r][c] == 'B') {
          board[r][c] = '.';
          boolean bConnected = isBConnected(board);
          board[r][c] = 'B';

          if (!bConnected) {
            return true;
          }
        }
      }
    }

    return false;
  }

  static boolean isRCuttable(char[][] board) {
    int N = board.length;

    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (board[r][c] == 'R') {
          board[r][c] = '.';
          boolean rConnected = isRConnected(board);
          board[r][c] = 'R';

          if (!rConnected) {
            return true;
          }
        }
      }
    }

    return false;
  }

  static boolean isBConnected(char[][] board) {
    int N = board.length;

    boolean[][] visited = new boolean[N][N];
    Queue<Point> queue = new ArrayDeque<>();
    for (int r = 0; r < N; ++r) {
      if (board[r][0] == 'B') {
        visited[r][0] = true;
        queue.offer(new Point(r, 0));
      }
    }

    while (!queue.isEmpty()) {
      Point head = queue.poll();

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];

        if (adjR >= 0
            && adjR < N
            && adjC >= 0
            && adjC < N
            && board[adjR][adjC] == 'B'
            && !visited[adjR][adjC]) {
          visited[adjR][adjC] = true;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    return IntStream.range(0, N).anyMatch(r -> visited[r][N - 1]);
  }

  static boolean isRConnected(char[][] board) {
    int N = board.length;

    boolean[][] visited = new boolean[N][N];
    Queue<Point> queue = new ArrayDeque<>();
    for (int c = 0; c < N; ++c) {
      if (board[0][c] == 'R') {
        visited[0][c] = true;
        queue.offer(new Point(0, c));
      }
    }

    while (!queue.isEmpty()) {
      Point head = queue.poll();

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];

        if (adjR >= 0
            && adjR < N
            && adjC >= 0
            && adjC < N
            && board[adjR][adjC] == 'R'
            && !visited[adjR][adjC]) {
          visited[adjR][adjC] = true;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    return IntStream.range(0, N).anyMatch(c -> visited[N - 1][c]);
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
