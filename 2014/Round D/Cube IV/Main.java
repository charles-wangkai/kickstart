import java.util.Scanner;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int S = sc.nextInt();
      int[][] maze = new int[S][S];
      for (int r = 0; r < S; ++r) {
        for (int c = 0; c < S; ++c) {
          maze[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(maze)));
    }

    sc.close();
  }

  static String solve(int[][] maze) {
    int S = maze.length;

    int[][] moveNums = new int[S][S];
    int maxMoveNum = -1;
    int roomWithMaxMoveNum = -1;
    for (int r = 0; r < S; ++r) {
      for (int c = 0; c < S; ++c) {
        computeMoveNum(moveNums, maze, r, c);

        if (moveNums[r][c] > maxMoveNum
            || (moveNums[r][c] == maxMoveNum && maze[r][c] < roomWithMaxMoveNum)) {
          maxMoveNum = moveNums[r][c];
          roomWithMaxMoveNum = maze[r][c];
        }
      }
    }

    return String.format("%d %d", roomWithMaxMoveNum, maxMoveNum);
  }

  static int computeMoveNum(int[][] moveNums, int[][] maze, int r, int c) {
    int S = moveNums.length;

    if (moveNums[r][c] == 0) {
      moveNums[r][c] = 1;
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = r + R_OFFSETS[i];
        int adjC = c + C_OFFSETS[i];
        if (adjR >= 0 && adjR < S && adjC >= 0 && adjC < S && maze[adjR][adjC] - maze[r][c] == 1) {
          moveNums[r][c] = Math.max(moveNums[r][c], 1 + computeMoveNum(moveNums, maze, adjR, adjC));
        }
      }
    }

    return moveNums[r][c];
  }
}
