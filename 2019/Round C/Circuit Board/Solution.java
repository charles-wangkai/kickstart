import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int K = sc.nextInt();
      int[][] V = new int[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          V[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, K)));
    }

    sc.close();
  }

  static int solve(int[][] V, int K) {
    int R = V.length;
    int C = V[0].length;

    int result = 0;
    for (int beginC = 0; beginC < C; ++beginC) {
      int[] mins = new int[R];
      Arrays.fill(mins, Integer.MAX_VALUE);

      int[] maxs = new int[R];
      Arrays.fill(maxs, Integer.MIN_VALUE);

      for (int endC = beginC; endC < C; ++endC) {
        int count = 0;
        for (int r = 0; r < R; ++r) {
          mins[r] = Math.min(mins[r], V[r][endC]);
          maxs[r] = Math.max(maxs[r], V[r][endC]);

          if (maxs[r] - mins[r] <= K) {
            ++count;
            result = Math.max(result, count * (endC - beginC + 1));
          } else {
            count = 0;
          }
        }
      }
    }

    return result;
  }
}
