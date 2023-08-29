import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
      String[] colors = new String[N];
      for (int i = 0; i < colors.length; ++i) {
        colors[i] = sc.next();
      }
      int M = sc.nextInt();
      int[] a = new int[M];
      int[] b = new int[M];
      int[] t = new int[M];
      for (int i = 0; i < M; ++i) {
        a[i] = sc.nextInt() - 1;
        b[i] = sc.nextInt() - 1;
        t[i] = sc.nextInt();
      }
      int S = sc.nextInt();
      int[] p = new int[S];
      int[] q = new int[S];
      for (int i = 0; i < S; ++i) {
        p[i] = sc.nextInt() - 1;
        q[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(colors, a, b, t, p, q)));
    }

    sc.close();
  }

  static String solve(String[] colors, int[] a, int[] b, int[] t, int[] p, int[] q) {
    String[] allColors = Arrays.stream(colors).distinct().toArray(String[]::new);

    Map<String, Integer> colorToIndex =
        IntStream.range(0, allColors.length)
            .boxed()
            .collect(Collectors.toMap(i -> allColors[i], i -> i));

    @SuppressWarnings("unchecked")
    Map<Integer, Integer>[] adjMaps = new Map[allColors.length];
    for (int i = 0; i < adjMaps.length; ++i) {
      adjMaps[i] = new HashMap<>();
    }

    for (int i = 0; i < a.length; ++i) {
      int fromIndex = colorToIndex.get(colors[a[i]]);
      int toIndex = colorToIndex.get(colors[b[i]]);

      adjMaps[fromIndex].put(
          toIndex, Math.min(adjMaps[fromIndex].getOrDefault(toIndex, Integer.MAX_VALUE), t[i]));
    }

    return IntStream.range(0, p.length)
        .mapToObj(
            i ->
                String.valueOf(
                    computeMinDistance(
                        adjMaps, colorToIndex.get(colors[p[i]]), colorToIndex.get(colors[q[i]]))))
        .collect(Collectors.joining("\n"));
  }

  static int computeMinDistance(Map<Integer, Integer>[] adjMaps, int fromIndex, int toIndex) {
    int[] distances = new int[adjMaps.length];
    Arrays.fill(distances, Integer.MAX_VALUE);

    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.distance));
    pq.offer(new Element(fromIndex, 0));

    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (head.index == toIndex) {
        return head.distance;
      }
      if (distances[head.index] == Integer.MAX_VALUE) {
        distances[head.index] = head.distance;

        for (int adj : adjMaps[head.index].keySet()) {
          pq.offer(new Element(adj, distances[head.index] + adjMaps[head.index].get(adj)));
        }
      }
    }

    return -1;
  }
}

class Element {
  int index;
  int distance;

  Element(int index, int distance) {
    this.index = index;
    this.distance = distance;
  }
}
