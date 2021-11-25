import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int V = sc.nextInt();
      int E = sc.nextInt();
      int[] A = new int[E];
      int[] B = new int[E];
      int[] L = new int[E];
      for (int i = 0; i < E; ++i) {
        A[i] = sc.nextInt() - 1;
        B[i] = sc.nextInt() - 1;
        L[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, A, B, L)));
    }

    sc.close();
  }

  static int solve(int V, int[] A, int[] B, int[] L) {
    int[][] distances = new int[V][V];
    for (int i = 0; i < V; ++i) {
      for (int j = 0; j < V; ++j) {
        distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < A.length; ++i) {
      distances[A[i]][B[i]] = Math.min(distances[A[i]][B[i]], L[i]);
      distances[B[i]][A[i]] = Math.min(distances[B[i]][A[i]], L[i]);
    }
    for (int k = 0; k < V; ++k) {
      for (int i = 0; i < V; ++i) {
        for (int j = 0; j < V; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    int result = 0;
    int minDistanceSum = Integer.MAX_VALUE;
    for (int code = 0; code < 1 << V; ++code) {
      int distanceSum = 0;
      for (int i = 0; i < V; ++i) {
        int minD = Integer.MAX_VALUE;
        for (int j = 0; j < V; ++j) {
          if (distances[i][j] != Integer.MAX_VALUE
              && ((code & (1 << i)) != 0) != ((code & (1 << j)) != 0)) {
            minD = Math.min(minD, distances[i][j]);
          }
        }
        if (minD == Integer.MAX_VALUE) {
          distanceSum = Integer.MAX_VALUE;

          break;
        }

        distanceSum += minD;
      }

      if (distanceSum < minDistanceSum) {
        minDistanceSum = distanceSum;
        result = 1;
      } else if (distanceSum == minDistanceSum) {
        ++result;
      }
    }

    return result;
  }
}