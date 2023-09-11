import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
  static final long LIMIT = 1_000_000_000_000L;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int S = sc.nextInt();
      int R = sc.nextInt();
      int[] U = new int[M];
      int[] V = new int[M];
      for (int i = 0; i < M; ++i) {
        U[i] = sc.nextInt() - 1;
        V[i] = sc.nextInt() - 1;
      }
      int[][] stones = new int[N][];
      for (int i = 0; i < stones.length; ++i) {
        int C = sc.nextInt();
        stones[i] = new int[C];
        for (int j = 0; j < stones[i].length; ++j) {
          stones[i][j] = sc.nextInt() - 1;
        }
      }
      int[][] recipes = new int[R][];
      for (int i = 0; i < recipes.length; ++i) {
        int K = sc.nextInt();
        recipes[i] = new int[K + 1];
        for (int j = 0; j < recipes[i].length; ++j) {
          recipes[i][j] = sc.nextInt() - 1;
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(U, V, S, stones, recipes)));
    }

    sc.close();
  }

  static long solve(int[] U, int[] V, int S, int[][] stones, int[][] recipes) {
    int N = stones.length;

    int[][] distances = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < U.length; ++i) {
      distances[U[i]][V[i]] = 1;
      distances[V[i]][U[i]] = 1;
    }
    for (int k = 0; k < N; ++k) {
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    Map<Integer, List<Integer>> stoneToRecipeIndices = new HashMap<>();
    for (int i = 0; i < recipes.length; ++i) {
      for (int j = 0; j < recipes[i].length - 1; ++j) {
        if (!stoneToRecipeIndices.containsKey(recipes[i][j])) {
          stoneToRecipeIndices.put(recipes[i][j], new ArrayList<>());
        }
        stoneToRecipeIndices.get(recipes[i][j]).add(i);
      }
    }

    long[][] costs = new long[S][N];
    for (int i = 0; i < costs.length; ++i) {
      Arrays.fill(costs[i], LIMIT);
    }
    boolean[][] visited = new boolean[S][N];
    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.cost));
    for (int i = 0; i < stones.length; ++i) {
      for (int stone : stones[i]) {
        costs[stone][i] = 0;
        pq.offer(new Element(stone, i, 0));
      }
    }
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (!visited[head.stone][head.node]) {
        visited[head.stone][head.node] = true;

        if (head.stone == 0) {
          return head.cost;
        }

        for (int i = 0; i < N; ++i) {
          if (distances[head.node][i] != Integer.MAX_VALUE
              && head.cost + distances[head.node][i] < costs[head.stone][i]) {
            costs[head.stone][i] = head.cost + distances[head.node][i];
            pq.offer(new Element(head.stone, i, head.cost + distances[head.node][i]));
          }
        }

        for (int recipeIndex : stoneToRecipeIndices.getOrDefault(head.stone, List.of())) {
          boolean ready = true;
          long sum = 0;
          for (int i = 0; i < recipes[recipeIndex].length - 1; ++i) {
            if (costs[recipes[recipeIndex][i]][head.node] == LIMIT) {
              ready = false;

              break;
            }
            sum += costs[recipes[recipeIndex][i]][head.node];
          }

          if (ready
              && sum < costs[recipes[recipeIndex][recipes[recipeIndex].length - 1]][head.node]) {
            costs[recipes[recipeIndex][recipes[recipeIndex].length - 1]][head.node] = sum;
            pq.offer(
                new Element(recipes[recipeIndex][recipes[recipeIndex].length - 1], head.node, sum));
          }
        }
      }
    }

    return -1;
  }
}

class Element {
  int stone;
  int node;
  long cost;

  Element(int stone, int node, long cost) {
    this.stone = stone;
    this.node = node;
    this.cost = cost;
  }
}
