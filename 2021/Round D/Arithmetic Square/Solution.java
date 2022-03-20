import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  static final int SIZE = 3;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int[][] G = new int[SIZE][SIZE];
      for (int r = 0; r < SIZE; ++r) {
        for (int c = 0; c < SIZE; ++c) {
          if (r != 1 || c != 1) {
            G[r][c] = sc.nextInt();
          }
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(G)));
    }

    sc.close();
  }

  static int solve(int[][] G) {
    Map<Integer, Integer> middleToCount = new HashMap<>();
    update(middleToCount, G[1][0] + G[1][2]);
    update(middleToCount, G[0][1] + G[2][1]);
    update(middleToCount, G[0][0] + G[2][2]);
    update(middleToCount, G[0][2] + G[2][0]);

    return middleToCount.values().stream().mapToInt(x -> x).max().orElse(0)
        + (isArithmetic(G[0][0], G[0][1], G[0][2]) ? 1 : 0)
        + (isArithmetic(G[2][0], G[2][1], G[2][2]) ? 1 : 0)
        + (isArithmetic(G[0][0], G[1][0], G[2][0]) ? 1 : 0)
        + (isArithmetic(G[0][2], G[1][2], G[2][2]) ? 1 : 0);
  }

  static boolean isArithmetic(int a, int b, int c) {
    return a + c == 2 * b;
  }

  static void update(Map<Integer, Integer> middleToCount, int sum) {
    if (sum % 2 == 0) {
      middleToCount.put(sum / 2, middleToCount.getOrDefault(sum / 2, 0) + 1);
    }
  }
}