import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] P = new int[N];
      for (int i = 0; i < P.length; ++i) {
        P[i] = sc.nextInt();
      }
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(P, A, K)));
    }

    sc.close();
  }

  static int solve(int[] P, int[] A, int K) {
    int N = P.length;

    Map<Integer, List<Integer>> colorToPositions = new HashMap<>();
    for (int i = 0; i < N; ++i) {
      if (!colorToPositions.containsKey(A[i])) {
        colorToPositions.put(A[i], new ArrayList<>());
      }
      colorToPositions.get(A[i]).add(P[i]);
    }
    for (List<Integer> positions : colorToPositions.values()) {
      Collections.sort(positions);
    }

    int[][] dp = new int[N + 1][2];
    for (int i = 0; i < dp.length; ++i) {
      Arrays.fill(dp[i], Integer.MAX_VALUE);
    }
    dp[0][0] = 0;
    for (List<Integer> positions : colorToPositions.values()) {
      int[][] nextDp = new int[N + 1][2];
      for (int i = 0; i < nextDp.length; ++i) {
        for (int j = 0; j < nextDp[i].length; ++j) {
          nextDp[i][j] = dp[i][j];
        }
      }

      for (int i = 0; i < dp.length; ++i) {
        for (int p = 0; p < positions.size(); ++p) {
          if (dp[i][0] != Integer.MAX_VALUE) {
            nextDp[i + p + 1][0] = Math.min(nextDp[i + p + 1][0], dp[i][0] + 2 * positions.get(p));
            nextDp[i + p + 1][1] = Math.min(nextDp[i + p + 1][1], dp[i][0] + positions.get(p));
          }
          if (dp[i][1] != Integer.MAX_VALUE) {
            nextDp[i + p + 1][1] = Math.min(nextDp[i + p + 1][1], dp[i][1] + 2 * positions.get(p));
          }
        }
      }

      dp = nextDp;
    }

    return dp[K][1];
  }
}