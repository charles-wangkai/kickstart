import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(A)));
    }

    sc.close();
  }

  static double solve(int[] A) {
    int N = A.length;

    long[] prefixSums = new long[N];
    for (int i = 0; i < prefixSums.length; ++i) {
      prefixSums[i] = ((i == 0) ? 0 : prefixSums[i - 1]) + A[i];
    }

    double[][] expected = new double[N][N];
    double[] leftSums = new double[N];
    double[] rightSums = new double[N];
    for (int length = 2; length <= N; ++length) {
      for (int beginIndex = 0; beginIndex + length <= N; ++beginIndex) {
        int endIndex = beginIndex + length - 1;

        expected[beginIndex][endIndex] +=
            prefixSums[endIndex]
                - ((beginIndex == 0) ? 0 : prefixSums[beginIndex - 1])
                + (leftSums[beginIndex] + rightSums[endIndex]) / (length - 1.0);

        leftSums[beginIndex] += expected[beginIndex][endIndex];
        rightSums[endIndex] += expected[beginIndex][endIndex];
      }
    }

    return expected[0][N - 1];
  }
}
