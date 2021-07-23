import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int P = sc.nextInt();
      int[] U = new int[M];
      int[] V = new int[M];
      int[] D = new int[M];
      for (int i = 0; i < M; ++i) {
        U[i] = sc.nextInt() - 1;
        V[i] = sc.nextInt() - 1;
        D[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N, U, V, D, P)));
    }

    sc.close();
  }

  static double solve(int N, int[] U, int[] V, int[] D, int P) {
    int[][] distances = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < U.length; ++i) {
      distances[U[i]][V[i]] = D[i];
      distances[V[i]][U[i]] = D[i];
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

    double result = 0;
    double[] probs = new double[N];
    probs[0] = 1;
    for (int p = 0; p < P; ++p) {
      double[] nextProbs = new double[N];

      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
          if (j != i) {
            result += probs[i] / (N - 1) * distances[i][j];
            nextProbs[j] += probs[i] / (N - 1);
          }
        }
      }

      probs = nextProbs;
    }

    return result;
  }
}
