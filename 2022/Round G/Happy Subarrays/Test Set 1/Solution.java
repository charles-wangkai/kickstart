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

  static long solve(int[] A) {
    long result = 0;
    for (int i = 0; i < A.length; ++i) {
      int sum = 0;
      for (int j = i; j < A.length; ++j) {
        sum += A[j];
        if (sum < 0) {
          break;
        }

        result += sum;
      }
    }

    return result;
  }
}
