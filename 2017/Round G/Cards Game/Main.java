import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] R = new int[N];
      for (int i = 0; i < R.length; ++i) {
        R[i] = sc.nextInt();
      }
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(R, B)));
    }

    sc.close();
  }

  static long solve(int[] R, int[] B) {
    int N = R.length;

    int[] parents = new int[N];
    Arrays.fill(parents, -1);

    List<Edge> edges = new ArrayList<>();
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (j != i) {
          edges.add(new Edge(i, j, R[i] ^ B[j]));
        }
      }
    }
    Collections.sort(edges, Comparator.comparing(e -> e.distance));

    long result = 0;
    for (Edge edge : edges) {
      int root1 = findRoot(parents, edge.node1);
      int root2 = findRoot(parents, edge.node2);
      if (root1 != root2) {
        result += edge.distance;
        parents[root2] = root1;
      }
    }

    return result;
  }

  static int findRoot(int[] parents, int node) {
    int root = node;
    while (parents[root] != -1) {
      root = parents[root];
    }

    int p = node;
    while (p != root) {
      int next = parents[p];
      parents[p] = root;

      p = next;
    }

    return root;
  }
}

class Edge {
  int node1;
  int node2;
  int distance;

  Edge(int node1, int node2, int distance) {
    this.node1 = node1;
    this.node2 = node2;
    this.distance = distance;
  }
}
