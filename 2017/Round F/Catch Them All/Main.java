import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
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

    double[] state = new double[N * 2];
    state[N] = 1;

    double[][] transition = new double[N * 2][N * 2];
    for (int j = 0; j < N; ++j) {
      transition[j][j] = 1;

      for (int i = 0; i < N; ++i) {
        if (i != j) {
          transition[N + i][j] = distances[i][j] / (N - 1.0);

          transition[N + i][N + j] = 1.0 / (N - 1);
        }
      }
    }

    state = multiply(state, pow(transition, P));

    double[] state_ = state;
    return IntStream.range(0, N).mapToDouble(i -> state_[i]).sum();
  }

  static double[][] pow(double[][] base, int exponent) {
    int size = base.length;

    double[][] result = new double[size][size];
    for (int i = 0; i < size; ++i) {
      result[i][i] = 1;
    }

    while (exponent != 0) {
      if ((exponent & 1) != 0) {
        result = multiply(result, base);
      }

      base = multiply(base, base);
      exponent >>= 1;
    }

    return result;
  }

  static double[] multiply(double[] v, double[][] m) {
    int size = v.length;

    double[] result = new double[size];
    for (int i = 0; i < result.length; ++i) {
      for (int j = 0; j < size; ++j) {
        result[i] += v[j] * m[j][i];
      }
    }

    return result;
  }

  static double[][] multiply(double[][] m1, double[][] m2) {
    int size = m1.length;

    double[][] result = new double[size][size];
    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        for (int k = 0; k < size; ++k) {
          result[i][j] += m1[i][k] * m2[k][j];
        }
      }
    }

    return result;
  }
}
