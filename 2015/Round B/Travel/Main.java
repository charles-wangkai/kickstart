import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      Edge[] edges = new Edge[M];
      for (int i = 0; i < edges.length; ++i) {
        int city1 = sc.nextInt() - 1;
        int city2 = sc.nextInt() - 1;
        int[] costs = new int[24];
        for (int j = 0; j < costs.length; ++j) {
          costs[j] = sc.nextInt();
        }

        edges[i] = new Edge(city1, city2, costs);
      }
      int[] D = new int[K];
      int[] S = new int[K];
      for (int i = 0; i < K; ++i) {
        D[i] = sc.nextInt() - 1;
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, edges, D, S)));
    }

    sc.close();
  }

  static String solve(int N, Edge[] edges, int[] D, int[] S) {
    @SuppressWarnings("unchecked")
    List<Edge>[] edgeLists = new List[N];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (Edge edge : edges) {
      edgeLists[edge.city1].add(edge);
      edgeLists[edge.city2].add(edge);
    }

    return IntStream.range(0, D.length)
        .mapToObj(i -> String.valueOf(computeDistance(edgeLists, D[i], S[i])))
        .collect(Collectors.joining(" "));
  }

  static int computeDistance(List<Edge>[] edgeLists, int destination, int startHour) {
    int N = edgeLists.length;

    int[] distances = new int[N];
    Arrays.fill(distances, Integer.MAX_VALUE);
    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.distance));
    pq.offer(new Element(0, 0));

    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (distances[head.city] == Integer.MAX_VALUE) {
        distances[head.city] = head.distance;

        for (Edge edge : edgeLists[head.city]) {
          int other = (head.city == edge.city1) ? edge.city2 : edge.city1;
          if (distances[other] == Integer.MAX_VALUE) {
            pq.offer(
                new Element(other, head.distance + edge.costs[(startHour + head.distance) % 24]));
          }
        }
      }
    }

    return (distances[destination] == Integer.MAX_VALUE) ? -1 : distances[destination];
  }
}

class Edge {
  int city1;
  int city2;
  int[] costs;

  Edge(int city1, int city2, int[] costs) {
    this.city1 = city1;
    this.city2 = city2;
    this.costs = costs;
  }
}

class Element {
  int city;
  int distance;

  Element(int city, int distance) {
    this.city = city;
    this.distance = distance;
  }
}
