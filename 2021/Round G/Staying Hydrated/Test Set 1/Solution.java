import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int K = sc.nextInt();
      int[] x1 = new int[K];
      int[] y1 = new int[K];
      int[] x2 = new int[K];
      int[] y2 = new int[K];
      for (int i = 0; i < K; ++i) {
        x1[i] = sc.nextInt();
        y1[i] = sc.nextInt();
        x2[i] = sc.nextInt();
        y2[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(x1, y1, x2, y2)));
    }

    sc.close();
  }

  static String solve(int[] x1, int[] y1, int[] x2, int[] y2) {
    return String.format("%d %d", findCoordinate(x1, x2), findCoordinate(y1, y2));
  }

  static int findCoordinate(int[] lowers, int[] uppers) {
    long minDistanceSum =
        IntStream.concat(Arrays.stream(lowers), Arrays.stream(uppers))
            .mapToLong(value -> computeDistanceSum(lowers, uppers, value))
            .min()
            .getAsLong();

    return IntStream.concat(Arrays.stream(lowers), Arrays.stream(uppers))
        .filter(value -> computeDistanceSum(lowers, uppers, value) == minDistanceSum)
        .min()
        .getAsInt();
  }

  static long computeDistanceSum(int[] lowers, int[] uppers, int value) {
    return IntStream.range(0, lowers.length)
        .map(
            i -> {
              if (value < lowers[i]) {
                return lowers[i] - value;
              } else if (value > uppers[i]) {
                return value - uppers[i];
              }

              return 0;
            })
        .asLongStream()
        .sum();
  }
}