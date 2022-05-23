import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int X = sc.nextInt();
      int Y = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(N, X, Y)));
    }

    sc.close();
  }

  static String solve(int N, int X, int Y) {
    int total = N * (N + 1) / 2;
    if ((long) total * X % (X + Y) == 0) {
      long sum = (long) total * X / (X + Y);
      if (sum <= total) {
        List<Integer> subset = new ArrayList<>();
        for (int i = N; i >= 1; --i) {
          if (sum >= i) {
            sum -= i;
            subset.add(i);
          }
        }

        return String.format(
            "POSSIBLE\n%d\n%s",
            subset.size(), subset.stream().map(String::valueOf).collect(Collectors.joining(" ")));
      }
    }

    return "IMPOSSIBLE";
  }
}