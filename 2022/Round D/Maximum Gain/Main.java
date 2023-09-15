import java.util.Scanner;
import java.util.stream.IntStream;

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
      int M = sc.nextInt();
      int[] B = new int[M];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }
      int K = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, K)));
    }

    sc.close();
  }

  static long solve(int[] A, int[] B, int K) {
    long[] aMaxPoints = computeMaxPoints(A, K);
    long[] bMaxPoints = computeMaxPoints(B, K);

    return IntStream.rangeClosed(0, K)
        .mapToLong(i -> aMaxPoints[i] + bMaxPoints[K - i])
        .max()
        .getAsLong();
  }

  static long[] computeMaxPoints(int[] points, int K) {
    int maxLength = Math.min(points.length, K);

    long[] leftSums = new long[K + 1];
    for (int i = 1; i <= maxLength; ++i) {
      leftSums[i] = leftSums[i - 1] + points[i - 1];
    }

    long[] rightSums = new long[K + 1];
    for (int i = 1; i <= maxLength; ++i) {
      rightSums[i] = rightSums[i - 1] + points[points.length - i];
    }

    long[] result = new long[K + 1];
    for (int leftLength = 0; leftLength <= maxLength; ++leftLength) {
      for (int rightLength = 0; leftLength + rightLength <= maxLength; ++rightLength) {
        result[leftLength + rightLength] =
            Math.max(
                result[leftLength + rightLength], leftSums[leftLength] + rightSums[rightLength]);
      }
    }

    return result;
  }
}
