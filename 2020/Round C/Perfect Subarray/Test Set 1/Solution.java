import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static int solve(int[] A) {
    int result = 0;
    for (int i = 0; i < A.length; ++i) {
      int sum = 0;
      for (int j = i; j < A.length; ++j) {
        sum += A[j];
        if (isSquare(sum)) {
          ++result;
        }
      }
    }

    return result;
  }

  static boolean isSquare(int x) {
    int root = (int) Math.round(Math.sqrt(x));

    return root * root == x;
  }
}