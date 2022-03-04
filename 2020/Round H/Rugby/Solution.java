import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
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

      System.out.println(String.format("Case #%d: %d", tc, solve(X, Y)));
    }

    sc.close();
  }

  static long solve(int[] X, int[] Y) {
    int[] sortedX = Arrays.stream(X).boxed().sorted().mapToInt(x -> x).toArray();

    return computeMinStepNumToEqual(
            IntStream.range(0, sortedX.length).map(i -> sortedX[i] - i).toArray())
        + computeMinStepNumToEqual(Y);
  }

  static long computeMinStepNumToEqual(int[] values) {
    long result = 0;
    int[] sorted = Arrays.stream(values).boxed().sorted().mapToInt(x -> x).toArray();
    for (int i = 0, j = values.length - 1; i < j; ++i, --j) {
      result += sorted[j] - sorted[i];
    }

    return result;
  }
}