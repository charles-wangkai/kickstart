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
      String[] S = new String[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.next();
      }
      int[] X = new int[Q];
      int[] Y = new int[Q];
      for (int i = 0; i < Q; ++i) {
        X[i] = sc.nextInt() - 1;
        Y[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(S, X, Y)));
    }

    sc.close();
  }

  static String solve(String[] S, int[] X, int[] Y) {
    int N = S.length;

    int[][] distances = new int[N][N];
    for (int i = 0; i < N; ++i) {
      distances[i][i] = 0;
      for (int j = i + 1; j < N; ++j) {
        if (isFriend(S[i], S[j])) {
          distances[i][j] = 1;
          distances[j][i] = 1;
        } else {
          distances[i][j] = Integer.MAX_VALUE;
          distances[j][i] = Integer.MAX_VALUE;
        }
      }
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

    return IntStream.range(0, X.length)
        .map(i -> (distances[X[i]][Y[i]] == Integer.MAX_VALUE) ? -1 : (1 + (distances[X[i]][Y[i]])))
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  static boolean isFriend(String s1, String s2) {
    return IntStream.rangeClosed('A', 'Z')
        .anyMatch(c -> s1.indexOf(c) != -1 && s2.indexOf(c) != -1);
  }
}