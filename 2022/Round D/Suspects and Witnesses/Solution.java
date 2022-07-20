import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[M];
      int[] B = new int[M];
      for (int i = 0; i < M; ++i) {
        A[i] = sc.nextInt() - 1;
        B[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, A, B, K)));
    }

    sc.close();
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