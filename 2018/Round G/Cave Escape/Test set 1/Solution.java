import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int E = sc.nextInt();
      int Sr = sc.nextInt() - 1;
      int Sc = sc.nextInt() - 1;
      int Tr = sc.nextInt() - 1;
      int Tc = sc.nextInt() - 1;
      int[][] V = new int[N][M];
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
          V[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, E, Sr, Sc, Tr, Tc)));
    }

    sc.close();
  }

  static int solve(int[][] V, int E, int Sr, int Sc, int Tr, int Tc) {
    int N = V.length;
    int M = V[0].length;

    @SuppressWarnings("unchecked")
    List<Edge>[] edgeLists = new List[N * M];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        for (int i = 0; i < R_OFFSETS.length; ++i) {
          int adjR = r + R_OFFSETS[i];
          int adjC = c + C_OFFSETS[i];
          if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < M && V[adjR][adjC] != -100000) {
            edgeLists[r * M + c].add(new Edge(adjR * M + adjC, -V[adjR][adjC]));
          }
        }
      }
    }

    int minDistance = computeMinDistance(edgeLists, Sr * M + Sc, Tr * M + Tc);

    return (minDistance >= 0 && minDistance <= E) ? (E - minDistance) : -1;
  }

  static int computeMinDistance(List<Edge>[] edgeLists, int source, int target) {
    int[] distances = new int[edgeLists.length];
    Arrays.fill(distances, -1);

    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.distance));
    pq.offer(new Element(source, 0));
    while (!pq.isEmpty()) {
      Element element = pq.poll();
      if (distances[element.to] == -1) {
        distances[element.to] = element.distance;

        for (Edge edge : edgeLists[element.to]) {
          if (distances[edge.to] == -1) {
            pq.offer(new Element(edge.to, element.distance + edge.cost));
          }
        }
      }
    }

    return distances[target];
  }
}

class Edge {
  int to;
  int cost;

  Edge(int to, int cost) {
    this.to = to;
    this.cost = cost;
  }
}

class Element {
  int to;
  int distance;

  Element(int to, int distance) {
    this.to = to;
    this.distance = distance;
  }
}