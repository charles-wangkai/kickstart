import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();
      int R = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R)));
    }

    sc.close();
  }

  static int solve(int L, int R) {
    return (int) IntStream.rangeClosed(L, R).filter(Solution::isBoring).count();
  }

  static boolean isBoring(int x) {
    String s = String.valueOf(x);

    return IntStream.range(0, s.length()).allMatch(i -> i % 2 != (s.charAt(i) - '0') % 2);
  }
}