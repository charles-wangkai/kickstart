import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int F = sc.nextInt();
      int L = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(F, L)));
    }

    sc.close();
  }

  static int solve(int F, int L) {
    return (int)
        IntStream.rangeClosed(F, L)
            .filter(x -> String.valueOf(x).chars().allMatch(ch -> ch != '9') && x % 9 != 0)
            .count();
  }
}
