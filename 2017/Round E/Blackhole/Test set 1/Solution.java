import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int[] X = new int[3];
      int[] Y = new int[3];
      int[] Z = new int[3];
      for (int i = 0; i < 3; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
        Z[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(X, Y, Z)));
    }

    sc.close();
  }

  static double solve(int[] X, int[] Y, int[] Z) {
    return (Arrays.stream(X).max().getAsInt() - Arrays.stream(X).min().getAsInt()) / 6.0;
  }
}
