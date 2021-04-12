import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  static final int SIZE = 8;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] pieces = new String[N];
      for (int i = 0; i < pieces.length; ++i) {
        pieces[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(pieces)));
    }

    sc.close();
  }

  static int solve(String[] pieces) {
    Piece[] ps = Arrays.stream(pieces).map(Piece::new).toArray(Piece[]::new);

    boolean[][] board = new boolean[SIZE][SIZE];
    for (Piece p : ps) {
      board[p.point.r][p.point.c] = true;
    }

    int result = 0;
    for (Piece p : ps) {
      if (p.type == 'K') {
        for (int dr = -1; dr <= 1; ++dr) {
          for (int dc = -1; dc <= 1; ++dc) {
            if (dr != 0 || dc != 0) {
              int r = p.point.r + dr;
              int c = p.point.c + dc;
              if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c]) {
                ++result;
              }
            }
          }
        }
      } else if (p.type == 'Q') {
        for (int dr = -1; dr <= 1; ++dr) {
          for (int dc = -1; dc <= 1; ++dc) {
            if (dr != 0 || dc != 0) {
              int r = p.point.r + dr;
              int c = p.point.c + dc;
              while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                if (board[r][c]) {
                  ++result;

                  break;
                }

                r += dr;
                c += dc;
              }
            }
          }
        }
      } else if (p.type == 'R') {
        for (int dr = -1; dr <= 1; ++dr) {
          for (int dc = -1; dc <= 1; ++dc) {
            if (Math.abs(dr + dc) == 1) {
              int r = p.point.r + dr;
              int c = p.point.c + dc;
              while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                if (board[r][c]) {
                  ++result;

                  break;
                }

                r += dr;
                c += dc;
              }
            }
          }
        }
      } else if (p.type == 'B') {
        for (int dr : new int[] {-1, 1}) {
          for (int dc : new int[] {-1, 1}) {
            int r = p.point.r + dr;
            int c = p.point.c + dc;
            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
              if (board[r][c]) {
                ++result;

                break;
              }

              r += dr;
              c += dc;
            }
          }
        }
      } else if (p.type == 'N') {
        for (int dr = -2; dr <= 2; ++dr) {
          for (int dc = -2; dc <= 2; ++dc) {
            if (Math.abs(dr * dc) == 2) {
              int r = p.point.r + dr;
              int c = p.point.c + dc;
              if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c]) {
                ++result;
              }
            }
          }
        }
      } else if (p.type == 'P') {
        for (int dr : new int[] {-1, 1}) {
          int r = p.point.r + dr;
          int c = p.point.c - 1;
          if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c]) {
            ++result;
          }
        }
      }
    }

    return result;
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

class Piece {
  Point point;
  char type;

  Piece(String s) {
    point = new Point(s.charAt(1) - '1', 'H' - s.charAt(0));
    type = s.charAt(3);
  }
}
