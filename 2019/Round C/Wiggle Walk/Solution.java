import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      int R = sc.nextInt();
      int C = sc.nextInt();
      int Sr = sc.nextInt();
      int Sc = sc.nextInt();
      String instructions = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(instructions, R, C, Sr, Sc)));
    }

    sc.close();
  }

  static String solve(String instructions, int R, int C, int Sr, int Sc) {
    Set<Point> visited = new HashSet<>();
    int r = Sr;
    int c = Sc;
    visited.add(new Point(r, c));
    for (char instruction : instructions.toCharArray()) {
      while (true) {
        if (instruction == 'N') {
          --r;
        } else if (instruction == 'S') {
          ++r;
        } else if (instruction == 'E') {
          ++c;
        } else {
          --c;
        }

        Point point = new Point(r, c);
        if (!visited.contains(point)) {
          visited.add(point);

          break;
        }
      }
    }

    return String.format("%d %d", r, c);
  }
}

class Point {
  int r;
  int c;

  Point(int r, int c) {
    this.r = r;
    this.c = c;
  }

  @Override
  public int hashCode() {
    return Objects.hash(r, c);
  }

  @Override
  public boolean equals(Object obj) {
    Point other = (Point) obj;

    return r == other.r && c == other.c;
  }
}