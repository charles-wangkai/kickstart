// https://www.cnblogs.com/zwfymqz/p/8253530.html

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      double[] X = new double[N];
      double[] Y = new double[N];
      double[] W = new double[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextDouble();
        Y[i] = sc.nextDouble();
        W[i] = sc.nextDouble();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(X, Y, W)));
    }

    sc.close();
  }

  static double solve(double[] X, double[] Y, double[] W) {
    int N = X.length;

    double[] x = new double[N];
    double[] y = new double[N];
    for (int i = 0; i < N; ++i) {
      x[i] = (X[i] + Y[i]) / 2;
      y[i] = (X[i] - Y[i]) / 2;
    }

    return computeDistanceSum(x) + computeDistanceSum(y);
  }

  static double computeDistanceSum(double[] positions) {
    Arrays.sort(positions);

    double result = 0;
    for (int i = 0, j = positions.length - 1; i < j; ++i, --j) {
      result += positions[j] - positions[i];
    }

    return result;
  }
}
