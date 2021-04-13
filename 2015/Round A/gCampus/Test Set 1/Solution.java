import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] U = new int[M];
      int[] V = new int[M];
      int[] C = new int[M];
      for (int i = 0; i < M; ++i) {
        U[i] = sc.nextInt();
        V[i] = sc.nextInt();
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(N, U, V, C)));
    }

    sc.close();
  }

  static String solve(int N, int[] U, int[] V, int[] C) {
    int[][] distances = new int[N][N];
    for (int i = 0; i < distances.length; ++i) {
      Arrays.fill(distances[i], Integer.MAX_VALUE);
    }
    for (int i = 0; i < U.length; ++i) {
      distances[U[i]][V[i]] = Math.min(distances[U[i]][V[i]], C[i]);
      distances[V[i]][U[i]] = Math.min(distances[V[i]][U[i]], C[i]);
    }

    for (int k = 0; k < N; ++k) {
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    return IntStream.range(0, U.length)
        .filter(
            i ->
                IntStream.range(0, N)
                    .allMatch(
                        from -> Math.abs(distances[from][U[i]] - distances[from][V[i]]) != C[i]))
        .mapToObj(String::valueOf)
        .collect(Collectors.joining("\n"));
  }
}
