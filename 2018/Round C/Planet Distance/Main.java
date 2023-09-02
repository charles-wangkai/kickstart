import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] x = new int[N];
      int[] y = new int[N];
      for (int i = 0; i < N; ++i) {
        x[i] = sc.nextInt() - 1;
        y[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(x, y)));
    }

    sc.close();
  }

  static String solve(int[] x, int[] y) {
    int[] circleNodes = findCircle(x, y);

    int[] result = new int[x.length];
    Arrays.fill(result, -1);

    Set<Integer>[] adjSets = buildAdjSets(x, y);
    Queue<Integer> queue = new ArrayDeque<>();
    for (int circleNode : circleNodes) {
      result[circleNode] = 0;
      queue.offer(circleNode);
    }

    while (!queue.isEmpty()) {
      int head = queue.poll();
      for (int adj : adjSets[head]) {
        if (result[adj] == -1) {
          result[adj] = result[head] + 1;
          queue.offer(adj);
        }
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static int[] findCircle(int[] x, int[] y) {
    Set<Integer>[] adjSets = buildAdjSets(x, y);

    while (true) {
      boolean changed = false;
      for (int i = 0; i < adjSets.length; ++i) {
        if (adjSets[i].size() == 1) {
          int other = adjSets[i].iterator().next();

          adjSets[i].remove(other);
          adjSets[other].remove(i);
          changed = true;
        }
      }

      if (!changed) {
        break;
      }
    }

    return IntStream.range(0, adjSets.length).filter(i -> !adjSets[i].isEmpty()).toArray();
  }

  static Set<Integer>[] buildAdjSets(int[] x, int[] y) {
    @SuppressWarnings("unchecked")
    Set<Integer>[] adjSets = new Set[x.length];
    for (int i = 0; i < adjSets.length; ++i) {
      adjSets[i] = new HashSet<>();
    }

    for (int i = 0; i < x.length; ++i) {
      adjSets[x[i]].add(y[i]);
      adjSets[y[i]].add(x[i]);
    }

    return adjSets;
  }
}
