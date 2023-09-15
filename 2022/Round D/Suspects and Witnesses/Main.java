import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int T = Integer.parseInt(st.nextToken());
    for (int tc = 1; tc <= T; ++tc) {
      st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int M = Integer.parseInt(st.nextToken());
      int K = Integer.parseInt(st.nextToken());
      int[] A = new int[M];
      int[] B = new int[M];
      for (int i = 0; i < M; ++i) {
        st = new StringTokenizer(br.readLine());
        A[i] = Integer.parseInt(st.nextToken()) - 1;
        B[i] = Integer.parseInt(st.nextToken()) - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, A, B, K)));
    }
  }

  static int solve(int N, int[] A, int[] B, int K) {
    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[N];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < A.length; ++i) {
      adjLists[B[i]].add(A[i]);
    }

    return (int) IntStream.range(0, N).filter(i -> search(adjLists, K, i)).count();
  }

  static boolean search(List<Integer>[] adjLists, int K, int begin) {
    List<Integer> reachables = new ArrayList<>();
    reachables.add(begin);
    for (int i = 0; i < reachables.size(); ++i) {
      for (int adj : adjLists[reachables.get(i)]) {
        if (!reachables.contains(adj)) {
          reachables.add(adj);

          if (reachables.size() == K + 1) {
            return true;
          }
        }
      }
    }

    return false;
  }
}
