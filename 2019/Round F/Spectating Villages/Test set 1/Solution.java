import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int V = sc.nextInt();
      int[] B = new int[V];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }
      int[] X = new int[V - 1];
      int[] Y = new int[V - 1];
      for (int i = 0; i < V - 1; ++i) {
        X[i] = sc.nextInt() - 1;
        Y[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(B, X, Y)));
    }

    sc.close();
  }

  static int solve(int[] B, int[] X, int[] Y) {
    int V = B.length;

    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[V];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < X.length; ++i) {
      adjLists[X[i]].add(Y[i]);
      adjLists[Y[i]].add(X[i]);
    }

    int result = Integer.MIN_VALUE;
    for (int mask = 0; mask < 1 << V; ++mask) {
      int mask_ = mask;
      result =
          Math.max(
              result,
              IntStream.range(0, V)
                  .filter(
                      i ->
                          (mask_ & (1 << i)) != 0
                              || adjLists[i].stream().anyMatch(adj -> (mask_ & (1 << adj)) != 0))
                  .map(i -> B[i])
                  .sum());
    }

    return result;
  }
}