import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[M];
      int[] B = new int[M];
      for (int i = 0; i < M; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, A, B, K)));
    }

    sc.close();
  }

  static int solve(int N, int[] A, int[] B, int K) {
    return (int) Arrays.stream(B).distinct().count();
  }
}