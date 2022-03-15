import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] X = new int[N - 1];
      int[] Y = new int[N - 1];
      int[] L = new int[N - 1];
      long[] A = new long[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        X[i] = sc.nextInt() - 1;
        Y[i] = sc.nextInt() - 1;
        L[i] = sc.nextInt();
        A[i] = sc.nextLong();
      }
      int[] C = new int[Q];
      int[] W = new int[Q];
      for (int i = 0; i < Q; ++i) {
        C[i] = sc.nextInt() - 1;
        W[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(X, Y, L, A, C, W)));
    }

    sc.close();
  }

  static String solve(int[] X, int[] Y, int[] L, long[] A, int[] C, int[] W) {
    int N = X.length + 1;

    @SuppressWarnings("unchecked")
    List<Integer>[] edgeIndexLists = new List[N];
    for (int i = 0; i < edgeIndexLists.length; ++i) {
      edgeIndexLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < X.length; ++i) {
      edgeIndexLists[X[i]].add(i);
      edgeIndexLists[Y[i]].add(i);
    }

    int[] paths = new int[N];
    Arrays.fill(paths, -1);
    search(edgeIndexLists, X, Y, paths, 0);

    return IntStream.range(0, C.length)
        .mapToLong(
            i -> {
              long result = 0;
              int node = C[i];
              while (node != 0) {
                int edgeIndex = paths[node];
                if (W[i] >= L[edgeIndex]) {
                  if (result == 0) {
                    result = A[edgeIndex];
                  } else {
                    result = gcd(result, A[edgeIndex]);
                  }
                }

                node = (node == X[edgeIndex]) ? Y[edgeIndex] : X[edgeIndex];
              }

              return result;
            })
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  static void search(List<Integer>[] edgeIndexLists, int[] X, int[] Y, int[] paths, int node) {
    for (int edgeIndex : edgeIndexLists[node]) {
      int other = (X[edgeIndex] == node) ? Y[edgeIndex] : X[edgeIndex];
      if (paths[other] == -1) {
        paths[other] = edgeIndex;
        search(edgeIndexLists, X, Y, paths, other);
      }
    }
  }
}