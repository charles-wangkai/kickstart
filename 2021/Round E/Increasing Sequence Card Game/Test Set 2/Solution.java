import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N)));
    }

    sc.close();
  }

  static double solve(int N) {
    double[] scores = new double[N + 1];
    double sum = 0;
    for (int i = 1; i < scores.length; ++i) {
      scores[i] = sum / i + 1;
      sum += scores[i];
    }

    return scores[N];
  }
}