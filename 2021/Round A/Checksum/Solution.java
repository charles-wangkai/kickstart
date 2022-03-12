// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000436140/000000000068c2c3#analysis

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[][] A = new int[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          A[r][c] = sc.nextInt();
        }
      }
      int[][] B = new int[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          B[r][c] = sc.nextInt();
        }
      }
      int[] R = new int[N];
      for (int i = 0; i < R.length; ++i) {
        R[i] = sc.nextInt();
      }
      int[] C = new int[N];
      for (int i = 0; i < C.length; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, R, C)));
    }

    sc.close();
  }

  static int solve(int[][] A, int[][] B, int[] R, int[] C) {
    int N = A.length;

    List<Edge> edges = new ArrayList<>();
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        edges.add(new Edge(r, c + N, B[r][c]));
      }
    }
    Collections.sort(edges, Comparator.comparing((Edge e) -> e.cost).reversed());

    int costSum = 0;
    int[] parents = new int[2 * N];
    Arrays.fill(parents, -1);
    for (Edge edge : edges) {
      int root1 = findRoot(parents, edge.v1);
      int root2 = findRoot(parents, edge.v2);
      if (root1 != root2) {
        parents[root2] = root1;
        costSum += edge.cost;
      }
    }

    return Arrays.stream(B).mapToInt(line -> Arrays.stream(line).sum()).sum() - costSum;
  }

  static int findRoot(int[] parents, int v) {
    int root = v;
    while (parents[root] != -1) {
      root = parents[root];
    }

    int p = v;
    while (p != root) {
      int next = parents[p];
      parents[p] = root;

      p = next;
    }

    return root;
  }
}

class Edge {
  int v1;
  int v2;
  int cost;

  Edge(int v1, int v2, int cost) {
    this.v1 = v1;
    this.v2 = v2;
    this.cost = cost;
  }
}