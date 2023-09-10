import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, K)));
    }

    sc.close();
  }

  static int solve(int[] A, int K) {
    Map<Integer, Integer> valueToCompressed = new HashMap<>();
    for (int value : A) {
      if (!valueToCompressed.containsKey(value)) {
        valueToCompressed.put(value, valueToCompressed.size());
      }
    }

    int[] compressed = Arrays.stream(A).map(valueToCompressed::get).toArray();

    int[][] dp = new int[valueToCompressed.size()][A.length + 1];
    for (int i = 0; i < dp.length; ++i) {
      Arrays.fill(dp[i], Integer.MAX_VALUE);
    }
    for (int i = 0; i < dp.length; ++i) {
      dp[i][0] = (i == compressed[0]) ? 0 : 1;
    }

    for (int i = 1; i < A.length; ++i) {
      int[][] nextDp = new int[valueToCompressed.size()][A.length + 1];
      for (int j = 0; j < nextDp.length; ++j) {
        Arrays.fill(nextDp[j], Integer.MAX_VALUE);
      }

      for (int j = 0; j < nextDp.length; ++j) {
        for (int k = 0; k < nextDp[j].length; ++k) {
          for (int prev = 0; prev < dp.length; ++prev) {
            if (k - ((prev == j) ? 0 : 1) >= 0
                && dp[prev][k - ((prev == j) ? 0 : 1)] != Integer.MAX_VALUE) {
              nextDp[j][k] =
                  Math.min(
                      nextDp[j][k],
                      dp[prev][k - ((prev == j) ? 0 : 1)] + ((j == compressed[i]) ? 0 : 1));
            }
          }
        }
      }

      dp = nextDp;
    }

    int result = Integer.MAX_VALUE;
    for (int i = 0; i < dp.length; ++i) {
      for (int j = 0; j <= K; ++j) {
        result = Math.min(result, dp[i][j]);
      }
    }

    return result;
  }
}
