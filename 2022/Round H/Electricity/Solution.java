import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] X = new int[N - 1];
      int[] Y = new int[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        X[i] = sc.nextInt() - 1;
        Y[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, X, Y)));
    }

    sc.close();
  }

  static int solve(int[] A, int[] X, int[] Y) {
    int N = A.length;

    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[N];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < X.length; ++i) {
      adjLists[X[i]].add(Y[i]);
      adjLists[Y[i]].add(X[i]);
    }

    int[] sortedNodes =
        IntStream.range(0, N)
            .boxed()
            .sorted(Comparator.comparing(i -> A[i]))
            .mapToInt(x -> x)
            .toArray();
    int[] sizes = new int[N];
    for (int node : sortedNodes) {
      sizes[node] = 1;
      for (int adj : adjLists[node]) {
        if (A[adj] < A[node]) {
          sizes[node] += sizes[adj];
        }
      }
    }

    return Arrays.stream(sizes).max().getAsInt();
  }
}
