import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, K)));
    }

    sc.close();
  }

  static int solve(int[] A, int K) {
    int result = 0;
    int beginIndex = 0;
    for (int i = 0; i < A.length; ++i) {
      if (A[beginIndex] == K && beginIndex + A[beginIndex] == i + A[i]) {
        if (A[i] == 1) {
          ++result;
        }
      } else {
        beginIndex = i;
      }
    }

    return result;
  }
}