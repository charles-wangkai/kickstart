import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long N = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(N)));
    }

    sc.close();
  }

  static long solve(long N) {
    String s = String.valueOf(N);

    int oddIndex = 0;
    while (oddIndex != s.length() && (s.charAt(oddIndex) - '0') % 2 == 0) {
      ++oddIndex;
    }
    if (oddIndex == s.length()) {
      return 0;
    }

    int digit = s.charAt(oddIndex) - '0';

    long lower =
        Long.parseLong(
            String.format(
                "%s%d%s",
                s.substring(0, oddIndex), digit - 1, "8".repeat(s.length() - oddIndex - 1)));

    long upper;
    if (digit == 9) {
      int index = oddIndex - 1;
      while (index != -1 && s.charAt(index) - '0' == 8) {
        --index;
      }

      if (index == -1) {
        s = '0' + s;
        index = 0;
      }

      upper =
          Long.parseLong(
              String.format(
                  "%s%d%s",
                  s.substring(0, index),
                  s.charAt(index) - '0' + 2,
                  "0".repeat(s.length() - index - 1)));
    } else {
      upper =
          Long.parseLong(
              String.format(
                  "%s%d%s",
                  s.substring(0, oddIndex), digit + 1, "0".repeat(s.length() - oddIndex - 1)));
    }

    return Math.min(upper - N, N - lower);
  }
}
