import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      long P = sc.nextLong();
      int[] A = new int[K];
      int[] B = new int[K];
      int[] C = new int[K];
      for (int i = 0; i < K; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, A, B, C, P)));
    }

    sc.close();
  }

  static String solve(int N, int[] A, int[] B, int[] C, long P) {
    char[] result = new char[N];
    for (int i = 0; i < A.length; ++i) {
      result[A[i] - 1] = (char) ('0' + C[i]);
    }

    int index = result.length - 1;
    --P;
    while (P != 0) {
      while (result[index] != 0) {
        --index;
      }

      result[index] = (char) ('0' + P % 2);

      P >>= 1;
    }

    for (int i = 0; i <= index; ++i) {
      if (result[i] == 0) {
        result[i] = '0';
      }
    }

    return new String(result);
  }
}
