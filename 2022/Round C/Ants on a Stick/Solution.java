import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
      int L = sc.nextInt();
      int[] P = new int[N];
      int[] D = new int[N];
      for (int i = 0; i < N; ++i) {
        P[i] = sc.nextInt();
        D[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(P, D, L)));
    }

    sc.close();
  }

  static String solve(int[] P, int[] D, int L) {
    int N = P.length;

    List<Integer> leftTimes = new ArrayList<>();
    List<Integer> rightTimes = new ArrayList<>();
    for (int i = 0; i < N; ++i) {
      if (D[i] == 0) {
        leftTimes.add(P[i]);
      } else {
        rightTimes.add(L - P[i]);
      }
    }
    Collections.sort(leftTimes);
    Collections.sort(rightTimes);

    int[] sortedIndices =
        IntStream.range(0, N)
            .boxed()
            .sorted(Comparator.comparing(i -> P[i]))
            .mapToInt(x -> x)
            .toArray();
    int[] times = new int[N];
    for (int i = 0; i < leftTimes.size(); ++i) {
      times[sortedIndices[i]] = leftTimes.get(i);
    }
    for (int i = 0; i < rightTimes.size(); ++i) {
      times[sortedIndices[sortedIndices.length - 1 - i]] = rightTimes.get(i);
    }

    return IntStream.range(0, N)
        .boxed()
        .sorted(Comparator.comparing((Integer i) -> times[i]).thenComparing(i -> i))
        .map(i -> i + 1)
        .map(String::valueOf)
        .collect(Collectors.joining(" "));
  }
}