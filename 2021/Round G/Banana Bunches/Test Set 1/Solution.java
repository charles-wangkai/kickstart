import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(B, K)));
    }

    sc.close();
  }

  static int solve(int[] B, int K) {
    int result = Integer.MAX_VALUE;
    for (int i = 0; i < B.length; ++i) {
      int sum1 = 0;
      for (int j = i; j < B.length; ++j) {
        sum1 += B[j];
        if (sum1 == K) {
          result = Math.min(result, j - i + 1);
        }

        for (int p = j + 1; p < B.length; ++p) {
          int sum2 = 0;
          for (int q = p; q < B.length; ++q) {
            sum2 += B[q];
            if (sum1 + sum2 == K) {
              result = Math.min(result, (j - i + 1) + (q - p + 1));
            }
          }
        }
      }
    }

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }
}