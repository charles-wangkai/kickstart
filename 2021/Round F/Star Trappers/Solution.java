import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  static final double EPSILON = 1e-6;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
      }
      int Xs = sc.nextInt();
      int Ys = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(X, Y, Xs, Ys)));
    }

    sc.close();
  }

  static String solve(int[] X, int[] Y, int Xs, int Ys) {
    X = Arrays.stream(X).map(x -> x - Xs).toArray();
    Y = Arrays.stream(Y).map(y -> y - Ys).toArray();

    double result = Double.MAX_VALUE;
    for (int i = 0; i < X.length; ++i) {
      result = Math.min(result, computeMinPerimeter(X, Y, i));
    }

    return (result == Double.MAX_VALUE) ? "IMPOSSIBLE" : String.format("%.9f", result);
  }

  static double computeMinPerimeter(int[] X, int[] Y, int beginIndex) {
    double beginAngle = Math.atan2(Y[beginIndex], X[beginIndex]);
    double[] angles =
        IntStream.range(0, X.length)
            .mapToDouble(
                i -> {
                  double angle = Math.atan2(Y[i], X[i]) - beginAngle;
                  if (angle < 0) {
                    angle += Math.PI * 2;
                  }

                  return angle;
                })
            .toArray();

    int[] sortedIndices =
        IntStream.range(0, X.length)
            .boxed()
            .sorted(Comparator.comparing(i -> angles[i]))
            .mapToInt(x -> x)
            .toArray();

    double result = Double.MAX_VALUE;
    double[] perimeters = new double[X.length];
    Arrays.fill(perimeters, Double.MAX_VALUE);
    for (int i = 0; i < perimeters.length; ++i) {
      if (angles[sortedIndices[i]] < Math.PI - EPSILON) {
        perimeters[sortedIndices[i]] = computeDistance(X, Y, beginIndex, sortedIndices[i]);
      }

      for (int j = i - 1;
          j >= 0 && angles[sortedIndices[i]] - angles[sortedIndices[j]] < Math.PI - EPSILON;
          --j) {
        perimeters[sortedIndices[i]] =
            Math.min(
                perimeters[sortedIndices[i]],
                perimeters[sortedIndices[j]]
                    + computeDistance(X, Y, sortedIndices[i], sortedIndices[j]));
      }

      if (Math.PI * 2 - angles[sortedIndices[i]] < Math.PI - EPSILON
          && perimeters[sortedIndices[i]] != Double.MAX_VALUE) {
        result =
            Math.min(
                result,
                perimeters[sortedIndices[i]] + computeDistance(X, Y, sortedIndices[i], beginIndex));
      }
    }

    return result;
  }

  static double computeDistance(int[] X, int[] Y, int index1, int index2) {
    return Math.sqrt(
        (long) (X[index1] - X[index2]) * (X[index1] - X[index2])
            + (long) (Y[index1] - Y[index2]) * (Y[index1] - Y[index2]));
  }
}