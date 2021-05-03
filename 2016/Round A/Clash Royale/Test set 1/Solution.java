import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int M = sc.nextInt();
      int N = sc.nextInt();
      int[] K = new int[N];
      int[] L = new int[N];
      int[][] A = new int[N][];
      int[][] C = new int[N][];
      for (int i = 0; i < N; ++i) {
        K[i] = sc.nextInt();
        L[i] = sc.nextInt() - 1;
        A[i] = new int[K[i]];
        for (int j = 0; j < A[i].length; ++j) {
          A[i][j] = sc.nextInt();
        }
        C[i] = new int[K[i] - 1];
        for (int j = 0; j < C[i].length; ++j) {
          C[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(M, K, L, A, C)));
    }

    sc.close();
  }

  static int solve(int M, int[] K, int[] L, int[][] A, int[][] C) {
    return search(M, K, L, A, C, 0);
  }

  static int search(int M, int[] K, int[] L, int[][] A, int[][] C, int index) {
    int N = K.length;

    if (index == N) {
      int result = 0;
      for (int i = 0; i < N; ++i) {
        result += A[i][L[i]];
      }

      return result;
    }

    int result = search(M, K, L, A, C, index + 1);
    if (L[index] != K[index] - 1 && M >= C[index][L[index]]) {
      M -= C[index][L[index]];
      ++L[index];

      result = Math.max(result, search(M, K, L, A, C, index));

      --L[index];
      M += C[index][L[index]];
    }

    return result;
  }
}
