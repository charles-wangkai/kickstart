import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int D = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, D)));
    }

    sc.close();
  }

  static int solve(int N, int D) {
    int result = 0;
    for (int b = 0; b < N; ++b) {
      for (int c = 0; b + 2 * c < N; ++c) {
        int product = N - b - 2 * c;
        for (int i = 1; i * i <= product; ++i) {
          if (product % i == 0) {
            int other = product / i;
            if (i > b + c && other % D == 0) {
              ++result;
            }
            if (other != i && other > b + c && i % D == 0) {
              ++result;
            }
          }
        }
      }
    }

    return result;
  }
}
