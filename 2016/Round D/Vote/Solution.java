// https://en.wikipedia.org/wiki/Bertrand%27s_ballot_theorem

import java.util.Scanner;

public class Solution {
  static int totalNum;
  static int leadNum;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N, M)));
    }

    sc.close();
  }

  static double solve(int N, int M) {
    return (double) (N - M) / (N + M);
  }
}
