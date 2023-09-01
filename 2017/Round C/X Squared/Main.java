import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      char[][] grid = new char[N][N];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < N; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(grid)));
    }

    sc.close();
  }

  static String solve(char[][] grid) {
    int N = grid.length;

    List<Point> xPoints = new ArrayList<>();
    int[] rowCounts = new int[N];
    int[] colCounts = new int[N];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (grid[r][c] == 'X') {
          xPoints.add(new Point(r, c));
          ++rowCounts[r];
          ++colCounts[c];
        }
      }
    }

    if (!Arrays.stream(rowCounts).allMatch(x -> x == 1 || x == 2)
        || !Arrays.stream(colCounts).allMatch(x -> x == 1 || x == 2)) {
      return "IMPOSSIBLE";
    }

    int[] oneRows = IntStream.range(0, N).filter(i -> rowCounts[i] == 1).toArray();
    int[] oneCols = IntStream.range(0, N).filter(i -> colCounts[i] == 1).toArray();
    if (!(oneRows.length == 1 && oneCols.length == 1 && grid[oneRows[0]][oneCols[0]] == 'X')) {
      return "IMPOSSIBLE";
    }

    for (int i = 0; i < xPoints.size(); ++i) {
      Point p1 = xPoints.get(i);
      for (int j = i + 1; j < xPoints.size(); ++j) {
        Point p2 = xPoints.get(j);

        if (grid[p1.r][p2.c] != grid[p2.r][p1.c]) {
          return "IMPOSSIBLE";
        }
      }
    }

    return "POSSIBLE";
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
