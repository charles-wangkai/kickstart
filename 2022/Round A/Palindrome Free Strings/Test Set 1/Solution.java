import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(S) ? "POSSIBLE" : "IMPOSSIBLE"));
    }

    sc.close();
  }

  static boolean solve(String S) {
    for (int mask = 0; mask < 1 << S.length(); ++mask) {
      char[] digits = S.toCharArray();
      for (int i = 0; i < digits.length; ++i) {
        if (digits[i] == '?') {
          digits[i] = ((mask & (1 << i)) == 0) ? '0' : '1';
        }
      }

      if (check(digits)) {
        return true;
      }
    }

    return false;
  }

  static boolean check(char[] digits) {
    return IntStream.rangeClosed(5, digits.length)
        .allMatch(
            length ->
                IntStream.rangeClosed(0, digits.length - length)
                    .allMatch(
                        beginIndex -> !isPalindrome(digits, beginIndex, beginIndex + length - 1)));
  }

  static boolean isPalindrome(char[] digits, int beginIndex, int endIndex) {
    while (beginIndex < endIndex) {
      if (digits[beginIndex] != digits[endIndex]) {
        return false;
      }

      ++beginIndex;
      --endIndex;
    }

    return true;
  }
}