import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] C = new String[N];
      int[] D = new int[N];
      int[] U = new int[N];
      for (int i = 0; i < N; ++i) {
        C[i] = sc.next();
        D[i] = sc.nextInt();
        U[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(C, D, U)));
    }

    sc.close();
  }

  static int solve(String[] C, int[] D, int[] U) {
    int N = C.length;

    int[] colorSortedIndices =
        IntStream.range(0, N)
            .boxed()
            .sorted(Comparator.comparing((Integer i) -> C[i]).thenComparing(i -> U[i]))
            .mapToInt(x -> x)
            .toArray();
    int[] durabilitySortedIndices =
        IntStream.range(0, N)
            .boxed()
            .sorted(Comparator.comparing((Integer i) -> D[i]).thenComparing(i -> U[i]))
            .mapToInt(x -> x)
            .toArray();

    return (int)
        IntStream.range(0, N)
            .filter(i -> colorSortedIndices[i] == durabilitySortedIndices[i])
            .count();
  }
}