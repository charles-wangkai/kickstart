import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int A1 = sc.nextInt();
      int B1 = sc.nextInt();
      int C = sc.nextInt();
      int D = sc.nextInt();
      int E1 = sc.nextInt();
      int E2 = sc.nextInt();
      int F = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, K, A1, B1, C, D, E1, E2, F)));
    }

    sc.close();
  }

  static long solve(int N, int K, int A1, int B1, int C, int D, int E1, int E2, int F) {
    int[] x = new int[N];
    int[] y = new int[N];
    x[0] = A1;
    y[0] = B1;
    for (int i = 1; i < N; ++i) {
      x[i] = (C * x[i - 1] + D * y[i - 1] + E1) % F;
      y[i] = (D * x[i - 1] + C * y[i - 1] + E2) % F;
    }

    int[] r = new int[N];
    int[] s = new int[N];
    for (int i = 1; i < N; ++i) {
      r[i] = (C * r[i - 1] + D * s[i - 1] + E1) % 2;
      s[i] = (D * r[i - 1] + C * s[i - 1] + E2) % 2;
    }

    int[] A = new int[N];
    int[] B = new int[N];
    A[0] = A1;
    B[0] = B1;
    for (int i = 1; i < N; ++i) {
      A[i] = ((r[i] == 0) ? 1 : -1) * x[i];
      B[i] = ((s[i] == 0) ? 1 : -1) * y[i];
    }

    int[][] m = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        m[i][j] = A[i] * B[j];
      }
    }

    long[][] prefixSums = new long[N + 1][N + 1];
    for (int i = 1; i <= N; ++i) {
      for (int j = 1; j <= N; ++j) {
        prefixSums[i][j] =
            prefixSums[i - 1][j]
                + prefixSums[i][j - 1]
                - prefixSums[i - 1][j - 1]
                + m[i - 1][j - 1];
      }
    }

    PriorityQueue<Long> maxSums = new PriorityQueue<>();
    for (int minR = 0; minR < N; ++minR) {
      for (int minC = 0; minC < N; ++minC) {
        for (int maxR = minR; maxR < N; ++maxR) {
          for (int maxC = minC; maxC < N; ++maxC) {
            long sum =
                prefixSums[maxR + 1][maxC + 1]
                    - prefixSums[minR][maxC + 1]
                    - prefixSums[maxR + 1][minC]
                    + prefixSums[minR][minC];

            if (maxSums.size() < K || sum > maxSums.peek()) {
              maxSums.offer(sum);

              if (maxSums.size() == K + 1) {
                maxSums.poll();
              }
            }
          }
        }
      }
    }

    return maxSums.peek();
  }
}
