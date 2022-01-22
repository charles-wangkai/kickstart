import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

  static long solve(int[] B, int[] X, int[] Y) {
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

    return computeMaxSum(search(B, adjLists, new boolean[V], 0));
  }

  static Result search(int[] B, List<Integer>[] adjLists, boolean[] visited, int node) {
    visited[node] = true;

    Map<Integer, Result> adjToSubResult = new HashMap<>();
    for (int adj : adjLists[node]) {
      if (!visited[adj]) {
        adjToSubResult.put(adj, search(B, adjLists, visited, adj));
      }
    }

    Result result = new Result();

    result.notIlluminatedSum =
        adjToSubResult.values().stream()
            .mapToLong(r -> Math.max(r.notIlluminatedSum, r.indirectIlluminatedSum))
            .sum();

    long total = adjToSubResult.values().stream().mapToLong(Solution::computeMaxSum).sum();
    result.indirectIlluminatedSum =
        adjToSubResult.values().stream()
            .mapToLong(r -> B[node] + r.lighthouseSum + (total - computeMaxSum(r)))
            .max()
            .orElse(Long.MIN_VALUE);

    result.lighthouseSum =
        B[node]
            + adjToSubResult.keySet().stream()
                .mapToLong(
                    adj -> {
                      Result subResult = adjToSubResult.get(adj);

                      return Math.max(
                          Math.max(
                              subResult.notIlluminatedSum + B[adj],
                              subResult.indirectIlluminatedSum),
                          subResult.lighthouseSum);
                    })
                .sum();

    return result;
  }

  static long computeMaxSum(Result r) {
    return Math.max(Math.max(r.notIlluminatedSum, r.indirectIlluminatedSum), r.lighthouseSum);
  }
}

class Result {
  long notIlluminatedSum;
  long indirectIlluminatedSum;
  long lighthouseSum;
}