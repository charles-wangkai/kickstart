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
    int count = 0;
    int prevDiff = Integer.MIN_VALUE;
    for (int i = 0; i < A.length - 1; ++i) {
      int diff = A[i + 1] - A[i];
      if (diff == prevDiff) {
        ++count;
      } else {
        count = 2;
      }

      result = Math.max(result, count);
      prevDiff = diff;
    }

    return result;
  }
}