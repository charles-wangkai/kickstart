import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String N = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(N)));
    }

    sc.close();
  }

  static String solve(String N) {
    char added = (char) (Math.floorMod(-N.chars().map(c -> c - '0').sum(), 9) + '0');

    int index = (added == '0') ? 1 : 0;
    while (index != N.length() && N.charAt(index) <= added) {
      ++index;
    }

    return String.format("%s%c%s", N.substring(0, index), added, N.substring(index));
  }
}