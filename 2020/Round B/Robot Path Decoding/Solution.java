import java.util.Scanner;

public class Solution {
  static final int SIZE = 1_000_000_000;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String program = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(program)));
    }

    sc.close();
  }

  static String solve(String program) {
    Offset offset = move(program);

    return String.format("%d %d", 1 + offset.dx, 1 + offset.dy);
  }

  static Offset move(String s) {
    int depth = 0;
    int beginIndex = -1;
    int dx = 0;
    int dy = 0;
    int factor = -1;
    for (int i = 0; i < s.length(); ++i) {
      char c = s.charAt(i);
      if (depth == 0) {
        if (c == 'N') {
          dy = addMod(dy, -1);
        } else if (c == 'S') {
          dy = addMod(dy, 1);
        } else if (c == 'E') {
          dx = addMod(dx, 1);
        } else if (c == 'W') {
          dx = addMod(dx, -1);
        } else if (c == '(') {
          ++depth;
          beginIndex = i + 1;
        } else {
          factor = c - '0';
        }
      } else if (c == '(') {
        ++depth;
      } else if (c == ')') {
        --depth;

        if (depth == 0) {
          Offset offset = move(s.substring(beginIndex, i));

          dx = addMod(dx, multiplyMod(offset.dx, factor));
          dy = addMod(dy, multiplyMod(offset.dy, factor));
        }
      }
    }

    return new Offset(dx, dy);
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, SIZE);
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % SIZE);
  }
}

class Offset {
  int dx;
  int dy;

  Offset(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }
}