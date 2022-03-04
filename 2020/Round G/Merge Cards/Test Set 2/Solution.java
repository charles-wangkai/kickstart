import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
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

    double[][] expected = new double[N][N];
    for (int length = 2; length <= N; ++length) {
      for (int beginIndex = 0; beginIndex + length <= N; ++beginIndex) {
        int endIndex = beginIndex + length - 1;
        for (int middleIndex = beginIndex; middleIndex < endIndex; ++middleIndex) {
          expected[beginIndex][endIndex] +=
              (computeRangeSum(A, beginIndex, endIndex)
                      + expected[beginIndex][middleIndex]
                      + expected[middleIndex + 1][endIndex])
                  / (length - 1.0);
        }
      }
    }

    return expected[0][N - 1];
  }

  static long computeRangeSum(int[] A, int beginIndex, int endIndex) {
    return IntStream.rangeClosed(beginIndex, endIndex).map(i -> A[i]).asLongStream().sum();
  }
}