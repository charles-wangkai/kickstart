import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int X = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, X)));
    }

    sc.close();
  }

  static String solve(int[] A, int X) {
    return IntStream.range(0, A.length)
        .boxed()
        .sorted(Comparator.comparing((Integer i) -> (A[i] + X - 1) / X).thenComparing(i -> i))
        .mapToInt(i -> i + 1)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }
}