import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Solution {
  static int[] R_OFFSETS = {-1, 0, 1, 0};
  static int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] grid = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(grid)));
    }

    sc.close();
  }

  static int solve(char[][] grid) {
    int R = grid.length;
    int C = grid[0].length;

    int[][] distances = new int[R][C];
    for (int r = 0; r < R; ++r) {
      Arrays.fill(distances[r], -1);
    }

    Queue<Point> queue = new ArrayDeque<>();
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (grid[r][c] == '1') {
          distances[r][c] = 0;
          queue.offer(new Point(r, c));
        }
      }
    }
    while (!queue.isEmpty()) {
      Point head = queue.poll();
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];
        if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C && distances[adjR][adjC] == -1) {
          distances[adjR][adjC] = distances[head.r][head.c] + 1;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    SortedMap<Integer, Integer> coordSumToCount = new TreeMap<>();
    SortedMap<Integer, Integer> coordDiffToCount = new TreeMap<>();
    Map<Integer, List<Point>> distanceToPoints = new HashMap<>();
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        updateMap(coordSumToCount, r + c, 1);
        updateMap(coordDiffToCount, r - c, 1);

        if (!distanceToPoints.containsKey(distances[r][c])) {
          distanceToPoints.put(distances[r][c], new ArrayList<>());
        }
        distanceToPoints.get(distances[r][c]).add(new Point(r, c));
      }
    }

    for (int distance = 0; ; ++distance) {
      for (Point point : distanceToPoints.getOrDefault(distance, List.of())) {
        updateMap(coordSumToCount, point.r + point.c, -1);
        updateMap(coordDiffToCount, point.r - point.c, -1);
      }

      if (computeNeededDistance(coordSumToCount, coordDiffToCount) <= distance) {
        return distance;
      }
    }
  }

  static void updateMap(SortedMap<Integer, Integer> map, int key, int delta) {
    map.put(key, map.getOrDefault(key, 0) + delta);
    map.remove(key, 0);
  }

  static int computeNeededDistance(
      SortedMap<Integer, Integer> coordSumToCount, SortedMap<Integer, Integer> coordDiffToCount) {
    return coordSumToCount.isEmpty()
        ? 0
        : Math.max(
                (coordSumToCount.lastKey() - coordSumToCount.firstKey() + 1) / 2,
                (coordDiffToCount.lastKey() - coordDiffToCount.firstKey() + 1) / 2)
            + (((coordSumToCount.lastKey() + coordSumToCount.firstKey()) % 2 == 0
                    && (coordDiffToCount.lastKey() + coordDiffToCount.firstKey()) % 2 == 0
                    && Math.floorMod(
                            (coordSumToCount.lastKey() + coordSumToCount.firstKey()) / 2, 2)
                        != Math.floorMod(
                            (coordDiffToCount.lastKey() + coordDiffToCount.firstKey()) / 2, 2)
                    && coordSumToCount.lastKey() - coordSumToCount.firstKey()
                        == coordDiffToCount.lastKey() - coordDiffToCount.firstKey())
                ? 1
                : 0);
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