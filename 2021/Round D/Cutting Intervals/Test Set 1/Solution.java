import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int C = sc.nextInt();
      int[] L = new int[N];
      int[] R = new int[N];
      for (int i = 0; i < N; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R, C)));
    }

    sc.close();
  }

  static int solve(int[] L, int[] R, int C) {
    int[] counts = new int[Arrays.stream(R).max().getAsInt()];
    for (int i = 0; i < L.length; ++i) {
      for (int j = L[i] + 1; j < R[i]; ++j) {
        ++counts[j];
      }
    }

    return L.length
        + Arrays.stream(counts)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .limit(C)
            .mapToInt(x -> x)
            .sum();
  }
}