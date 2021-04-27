import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int[][] grid = new int[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          grid[r][c] = line.charAt(c) - '0';
        }
      }
      int N = sc.nextInt();
      sc.nextLine();
      String[] operations = new String[N];
      for (int i = 0; i < operations.length; ++i) {
        operations[i] = sc.nextLine();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(grid, operations)));
    }

    sc.close();
  }

  static String solve(int[][] grid, String[] operations) {
    List<Integer> result = new ArrayList<>();
    for (String operation : operations) {
      String[] parts = operation.split(" ");
      if (parts[0].equals("M")) {
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);

        grid[x][y] = z;
      } else {
        result.add(computeRegionNum(grid));
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
  }

  static int computeRegionNum(int[][] grid) {
    int R = grid.length;
    int C = grid[0].length;

    int result = 0;
    boolean[][] visited = new boolean[R][C];
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (grid[r][c] == 1 && !visited[r][c]) {
          search(grid, visited, r, c);
          ++result;
        }
      }
    }

    return result;
  }

  static void search(int[][] grid, boolean[][] visited, int r, int c) {
    int R = grid.length;
    int C = grid[0].length;

    visited[r][c] = true;

    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int adjR = r + R_OFFSETS[i];
      int adjC = c + C_OFFSETS[i];
      if (adjR >= 0
          && adjR < R
          && adjC >= 0
          && adjC < C
          && grid[adjR][adjC] == 1
          && !visited[adjR][adjC]) {
        search(grid, visited, adjR, adjC);
      }
    }
  }
}
