import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int D = sc.nextInt();
      int S = sc.nextInt();
      int[] C = new int[S];
      int[] E = new int[S];
      for (int i = 0; i < S; ++i) {
        C[i] = sc.nextInt();
        E[i] = sc.nextInt();
      }
      int[] A = new int[D];
      int[] B = new int[D];
      for (int i = 0; i < D; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(C, E, A, B)));
    }

    sc.close();
  }

  static String solve(int[] C, int[] E, int[] A, int[] B) {
    int S = C.length;

    int[] sortedIndices =
        IntStream.range(0, S)
            .boxed()
            .sorted((i1, i2) -> Integer.compare(E[i1] * C[i2], E[i2] * C[i1]))
            .mapToInt(x -> x)
            .toArray();

    int[] leftCSums = new int[S];
    for (int i = 0; i < leftCSums.length; ++i) {
      leftCSums[i] = ((i == 0) ? 0 : leftCSums[i - 1]) + C[sortedIndices[i]];
    }

    int[] rightESums = new int[S];
    for (int i = rightESums.length - 1; i >= 0; --i) {
      rightESums[i] = ((i == rightESums.length - 1) ? 0 : rightESums[i + 1]) + E[sortedIndices[i]];
    }

    return IntStream.range(0, A.length)
        .mapToObj(
            i -> {
              if (leftCSums[leftCSums.length - 1] < A[i]) {
                return "N";
              }

              int index = Arrays.binarySearch(leftCSums, A[i]);
              if (index < 0) {
                index = -1 - index;
              }

              return isNotLessThan(
                      (index == rightESums.length - 1) ? 0 : rightESums[index + 1],
                      (long) (leftCSums[index] - A[i]) * E[sortedIndices[index]],
                      C[sortedIndices[index]],
                      B[i])
                  ? "Y"
                  : "N";
            })
        .collect(Collectors.joining());
  }

  static boolean isNotLessThan(int whole, long numerator, int denominator, int rhs) {
    return (long) whole * denominator + numerator >= (long) rhs * denominator;
  }
}