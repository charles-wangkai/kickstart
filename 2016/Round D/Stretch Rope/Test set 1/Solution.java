import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int L = sc.nextInt();
      int[] A = new int[N];
      int[] B = new int[N];
      int[] P = new int[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
        P[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, B, P, M, L)));
    }

    sc.close();
  }

  static String solve(int[] A, int[] B, int[] P, int M, int L) {
    int N = A.length;

    int minCost = Integer.MAX_VALUE;
    for (int code = 1; code < 1 << N; ++code) {
      int lower = 0;
      int upper = 0;
      int cost = 0;
      for (int i = 0; i < N; ++i) {
        if ((code & (1 << i)) != 0) {
          lower += A[i];
          upper += B[i];
          cost += P[i];
        }
      }
      if (L >= lower && L <= upper) {
        minCost = Math.min(minCost, cost);
      }
    }

    return (minCost <= M) ? String.valueOf(minCost) : "IMPOSSIBLE";
  }
}
