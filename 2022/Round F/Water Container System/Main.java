import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Runnable {
  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 26).start();
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] u = new int[N - 1];
      int[] v = new int[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        u[i] = sc.nextInt() - 1;
        v[i] = sc.nextInt() - 1;
      }
      int[] added = new int[Q];
      for (int i = 0; i < added.length; ++i) {
        added[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(u, v, added)));
    }

    sc.close();
  }

  static int solve(int[] u, int[] v, int[] added) {
    int N = u.length + 1;

    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[N];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < u.length; ++i) {
      adjLists[u[i]].add(v[i]);
      adjLists[v[i]].add(u[i]);
    }

    List<Integer> counts = new ArrayList<>();
    search(counts, adjLists, -1, 0, 0);

    int result = 0;
    for (int count : counts) {
      if (result + count > added.length) {
        break;
      }

      result += count;
    }

    return result;
  }

  static void search(
      List<Integer> counts, List<Integer>[] adjLists, int parent, int node, int level) {
    if (counts.size() == level) {
      counts.add(0);
    }
    counts.set(level, counts.get(level) + 1);

    for (int adj : adjLists[node]) {
      if (adj != parent) {
        search(counts, adjLists, node, adj, level + 1);
      }
    }
  }
}
