import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] values = new int[N];
      for (int i = 0; i < values.length; ++i) {
        values[i] = sc.nextInt();
      }
      int[] L = new int[Q];
      int[] R = new int[Q];
      for (int i = 0; i < Q; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(values, L, R)));
    }

    sc.close();
  }

  static String solve(int[] values, int[] L, int[] R) {
    List<Integer> sums = new ArrayList<>();
    for (int beginIndex = 0; beginIndex < values.length; ++beginIndex) {
      int sum = 0;
      for (int endIndex = beginIndex; endIndex < values.length; ++endIndex) {
        sum += values[endIndex];
        sums.add(sum);
      }
    }
    Collections.sort(sums);

    return IntStream.range(0, L.length)
        .mapToObj(
            i ->
                String.valueOf(
                    IntStream.rangeClosed(L[i] - 1, R[i] - 1).map(sums::get).asLongStream().sum()))
        .collect(Collectors.joining("\n"));
  }
}
