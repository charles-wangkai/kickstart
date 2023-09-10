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
      int P = sc.nextInt();
      int[][] B = new int[N][K];
      for (int i = 0; i < B.length; ++i) {
        for (int j = 0; j < B[i].length; ++j) {
          B[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(B, P)));
    }

    sc.close();
  }

  static int solve(int[][] B, int P) {
    Map<Integer, Integer> countToMaxSum = Map.of(0, 0);
    for (int[] stack : B) {
      Map<Integer, Integer> nextCountToMaxSum = new HashMap<>(countToMaxSum);
      int sum = 0;
      for (int i = 0; i < stack.length; ++i) {
        sum += stack[i];
        for (int count : countToMaxSum.keySet()) {
          nextCountToMaxSum.put(
              count + i + 1,
              Math.max(
                  nextCountToMaxSum.getOrDefault(count + i + 1, 0),
                  countToMaxSum.get(count) + sum));
        }
      }

      countToMaxSum = nextCountToMaxSum;
    }

    return countToMaxSum.get(P);
  }
}
