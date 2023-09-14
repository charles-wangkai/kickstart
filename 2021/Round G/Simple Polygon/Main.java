import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int A = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(N, A)));
    }

    sc.close();
  }

  static String solve(int N, int A) {
    if (N - 2 > A) {
      return "IMPOSSIBLE";
    }

    List<Point> points = new ArrayList<>();
    points.add(new Point(0, 0));
    points.add(new Point(1, 0));
    int index = 0;
    int direction = 0;
    while (points.size() < N) {
      int side = (points.size() == N - 1) ? (A - N + 3) : 1;
      if (direction == 0) {
        points.add(index + 1, new Point(points.get(index).x, points.get(index).y + side));
      } else if (direction == 1) {
        points.add(index + 1, new Point(points.get(index).x - side, points.get(index + 1).y));
        ++index;
      } else if (direction == 2) {
        points.add(index + 1, new Point(points.get(index + 1).x, points.get(index + 1).y + side));
        ++index;
      } else {
        points.add(index + 1, new Point(points.get(index).x + side, points.get(index).y));
      }

      direction = (direction + 1) % 4;
    }

    int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
    int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();

    return String.format(
        "POSSIBLE\n%s",
        IntStream.range(0, N)
            .mapToObj(i -> String.format("%d %d", points.get(i).x - minX, points.get(i).y - minY))
            .collect(Collectors.joining("\n")));
  }
}

class Point {
  int x;
  int y;

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
