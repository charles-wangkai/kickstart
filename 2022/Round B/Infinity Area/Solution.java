import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(R, A, B)));
    }

    sc.close();
  }

  static double solve(int R, int A, int B) {
    double result = 0;
    while (R != 0) {
      result += computeArea(R);
      R *= A;
      result += computeArea(R);
      R /= B;
    }

    return result;
  }

  static double computeArea(int r) {
    return Math.PI * r * r;
  }
}