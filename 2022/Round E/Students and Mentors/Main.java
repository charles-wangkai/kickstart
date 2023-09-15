import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] R = new int[N];
      for (int i = 0; i < R.length; ++i) {
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(R)));
    }

    sc.close();
  }

  static String solve(int[] R) {
    int[] sortedIndices =
        IntStream.range(0, R.length)
            .boxed()
            .sorted(Comparator.comparing(i -> R[i]))
            .mapToInt(x -> x)
            .toArray();

    int[] result = new int[R.length];
    int frontIndex = 0;
    for (int i = 0; i < sortedIndices.length; ++i) {
      while (frontIndex != sortedIndices.length - 1
          && R[sortedIndices[frontIndex + 1]] <= 2 * R[sortedIndices[i]]) {
        ++frontIndex;
      }

      int mentorIndex = frontIndex - ((frontIndex == i) ? 1 : 0);
      result[sortedIndices[i]] = (mentorIndex == -1) ? -1 : R[sortedIndices[mentorIndex]];
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}
