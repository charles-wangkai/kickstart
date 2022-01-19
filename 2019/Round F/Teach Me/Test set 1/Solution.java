import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int S = sc.nextInt();
      int[][] A = new int[N][];
      for (int i = 0; i < A.length; ++i) {
        int C = sc.nextInt();
        A[i] = new int[C];
        for (int j = 0; j < A[i].length; ++j) {
          A[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, S)));
    }

    sc.close();
  }

  static int solve(int[][] A, int S) {
    int result = 0;
    for (int i = 0; i < A.length; ++i) {
      for (int j = 0; j < A.length; ++j) {
        int j_ = j;
        if (Arrays.stream(A[i]).anyMatch(ai -> Arrays.stream(A[j_]).allMatch(aj -> aj != ai))) {
          ++result;
        }
      }
    }

    return result;
  }
}