import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int T = Integer.parseInt(st.nextToken());
    for (int tc = 1; tc <= T; ++tc) {
      st = new StringTokenizer(br.readLine());
      int V = Integer.parseInt(st.nextToken());
      st = new StringTokenizer(br.readLine());
      int[] B = new int[V];
      for (int i = 0; i < B.length; ++i) {
        B[i] = Integer.parseInt(st.nextToken());
      }
      int[] X = new int[V - 1];
      int[] Y = new int[V - 1];
      for (int i = 0; i < V - 1; ++i) {
        st = new StringTokenizer(br.readLine());
        X[i] = Integer.parseInt(st.nextToken()) - 1;
        Y[i] = Integer.parseInt(st.nextToken()) - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(B, X, Y)));
    }
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

    int[] parents = new int[V];
    parents[0] = -1;

    long[] notIlluminatedSums = new long[V];
    long[] indirectIlluminatedSums = new long[V];
    long[] lighthouseSums = new long[V];

    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(0);
    while (!stack.isEmpty()) {
      int top = stack.pop();
      if (top >= 0) {
        int node = top;

        stack.push(-1 - node);

        for (int adj : adjLists[node]) {
          if (adj != parents[node]) {
            parents[adj] = node;
            stack.push(adj);
          }
        }
      } else {
        int node = -1 - top;

        long total = 0;
        for (int adj : adjLists[node]) {
          if (adj != parents[node]) {
            total +=
                computeMaxSum(notIlluminatedSums, indirectIlluminatedSums, lighthouseSums, adj);
          }
        }

        indirectIlluminatedSums[node] = Long.MIN_VALUE;
        lighthouseSums[node] = B[node];
        for (int adj : adjLists[node]) {
          if (adj != parents[node]) {
            notIlluminatedSums[node] +=
                Math.max(notIlluminatedSums[adj], indirectIlluminatedSums[adj]);

            indirectIlluminatedSums[node] =
                Math.max(
                    indirectIlluminatedSums[node],
                    B[node]
                        + lighthouseSums[adj]
                        + (total
                            - computeMaxSum(
                                notIlluminatedSums, indirectIlluminatedSums, lighthouseSums, adj)));

            lighthouseSums[node] +=
                Math.max(
                    Math.max(notIlluminatedSums[adj] + B[adj], indirectIlluminatedSums[adj]),
                    lighthouseSums[adj]);
          }
        }
      }
    }

    return computeMaxSum(notIlluminatedSums, indirectIlluminatedSums, lighthouseSums, 0);
  }

  static long computeMaxSum(
      long[] notIlluminatedSums, long[] indirectIlluminatedSums, long[] lighthouseSums, int node) {
    return Math.max(
        Math.max(notIlluminatedSums[node], indirectIlluminatedSums[node]), lighthouseSums[node]);
  }
}
