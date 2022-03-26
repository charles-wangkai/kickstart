import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      char[][] cells = new char[N][M];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < M; ++c) {
          cells[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(cells)));
    }

    sc.close();
  }

  static String solve(char[][] cells) {
    int N = cells.length;
    int M = cells[0].length;

    char[][] filled = new char[N][M];
    for (int r = 0; r < filled.length; ++r) {
      filled[r] = cells[r].clone();
    }

    int[] parents = new int[N * M];
    Arrays.fill(parents, -1);

    for (int r = 0; r < N; ++r) {
      int beginC = 0;
      for (int c = 0; c <= M; ++c) {
        if (c == M || filled[r][c] == '#') {
          for (int c1 = beginC, c2 = c - 1; c1 < c2; ++c1, --c2) {
            int root1 = findRoot(parents, toIndex(M, r, c1));
            int root2 = findRoot(parents, toIndex(M, r, c2));
            if (root1 != root2) {
              parents[root2] = root1;

              Point point1 = toPoint(M, root1);
              Point point2 = toPoint(M, root2);
              if (filled[point1.r][point1.c] == '.') {
                filled[point1.r][point1.c] = filled[point2.r][point2.c];
              }
            }
          }

          beginC = c + 1;
        }
      }
    }

    for (int c = 0; c < M; ++c) {
      int beginR = 0;
      for (int r = 0; r <= N; ++r) {
        if (r == N || filled[r][c] == '#') {
          for (int r1 = beginR, r2 = r - 1; r1 < r2; ++r1, --r2) {
            int root1 = findRoot(parents, toIndex(M, r1, c));
            int root2 = findRoot(parents, toIndex(M, r2, c));
            if (root1 != root2) {
              parents[root2] = root1;

              Point point1 = toPoint(M, root1);
              Point point2 = toPoint(M, root2);
              if (filled[point1.r][point1.c] == '.') {
                filled[point1.r][point1.c] = filled[point2.r][point2.c];
              }
            }
          }

          beginR = r + 1;
        }
      }
    }

    int filledCount = 0;
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        Point point = toPoint(M, findRoot(parents, toIndex(M, r, c)));

        filled[r][c] = filled[point.r][point.c];
        if (filled[r][c] != cells[r][c]) {
          ++filledCount;
        }
      }
    }

    return String.format(
        "%d\n%s",
        filledCount, Arrays.stream(filled).map(String::new).collect(Collectors.joining("\n")));
  }

  static int findRoot(int[] parents, int index) {
    int root = index;
    while (parents[root] != -1) {
      root = parents[root];
    }

    int p = index;
    while (p != root) {
      int next = parents[p];
      parents[p] = root;

      p = next;
    }

    return root;
  }

  static Point toPoint(int M, int index) {
    return new Point(index / M, index % M);
  }

  static int toIndex(int M, int r, int c) {
    return r * M + c;
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