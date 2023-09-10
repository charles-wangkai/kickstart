import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int LIMIT = 10_000_000;

  static int[] squares;

  public static void main(String[] args) {
    buildSquares();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static void buildSquares() {
    squares =
        IntStream.iterate(0, i -> i + 1).takeWhile(i -> i * i <= LIMIT).map(i -> i * i).toArray();
  }

  static long solve(int[] A) {
    Map<Integer, Integer> lastSumToCount = new HashMap<>();
    int lastSum = 0;
    lastSumToCount.put(0, 1);
    for (int i = A.length - 1; i >= 0; --i) {
      lastSum += A[i];
      lastSumToCount.put(lastSum, lastSumToCount.getOrDefault(lastSum, 0) + 1);
    }

    long result = 0;
    int total = Arrays.stream(A).sum();
    int maxSubArraySum = computeMaxSubArraySum(A);
    int firstSum = 0;
    for (int i = 0; i < A.length; ++i) {
      lastSumToCount.put(lastSum, lastSumToCount.get(lastSum) - 1);

      for (int square : squares) {
        if (square > maxSubArraySum) {
          break;
        }

        result += lastSumToCount.getOrDefault(total - firstSum - square, 0);
      }

      firstSum += A[i];
      lastSum -= A[i];
    }

    return result;
  }

  static int computeMaxSubArraySum(int[] A) {
    int result = 0;
    int sum = 0;
    for (int x : A) {
      sum += x;
      result = Math.max(result, sum);
      sum = Math.max(sum, 0);
    }

    return result;
  }
}
