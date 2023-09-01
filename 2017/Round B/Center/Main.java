// https://www.cnblogs.com/zwfymqz/p/8253530.html

import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
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

    return computeDistanceSum(x, W) + computeDistanceSum(y, W);
  }

  static double computeDistanceSum(double[] positions, double[] W) {
    int N = positions.length;

    int[] sortedIndices =
        IntStream.range(0, N)
            .boxed()
            .sorted(Comparator.comparing(i -> positions[i]))
            .mapToInt(x -> x)
            .toArray();

    double leftSum = 0;
    double rightSum =
        IntStream.range(0, N)
            .mapToDouble(i -> W[i] * (positions[i] - positions[sortedIndices[0]]))
            .sum();
    double leftWeightSum = 0;
    double rightWeightSum =
        IntStream.range(0, N).filter(i -> i != sortedIndices[0]).mapToDouble(i -> W[i]).sum();
    double result = leftSum + rightSum;
    for (int i = 1; i < N; ++i) {
      leftSum +=
          (leftWeightSum + W[sortedIndices[i - 1]])
              * (positions[sortedIndices[i]] - positions[sortedIndices[i - 1]]);
      rightSum -= rightWeightSum * (positions[sortedIndices[i]] - positions[sortedIndices[i - 1]]);

      result = Math.min(result, leftSum + rightSum);

      leftWeightSum += W[sortedIndices[i - 1]];
      rightWeightSum -= W[sortedIndices[i]];
    }

    return result;
  }
}
