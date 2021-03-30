import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {
  static final String[] DIGIT_WORDS = {
    "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
  };
  static final String[] COUNT_WORDS = {
    null,
    null,
    "double",
    "triple",
    "quadruple",
    "quintuple",
    "sextuple",
    "septuple",
    "octuple",
    "nonuple",
    "decuple"
  };

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String N = sc.next();
      String F = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(N, F)));
    }

    sc.close();
  }

  static String solve(String N, String F) {
    List<String> result = new ArrayList<>();
    int index = 0;
    for (int part : Arrays.stream(F.split("-")).mapToInt(Integer::parseInt).toArray()) {
      char digit = 0;
      int count = 0;
      for (int i = 0; i <= part; ++i) {
        if (i != part && N.charAt(index) == digit) {
          ++count;
        } else {
          if (digit != 0) {
            if (count == 1 || count > 10) {
              for (int j = 0; j < count; ++j) {
                result.add(String.valueOf(DIGIT_WORDS[digit - '0']));
              }
            } else {
              result.add(String.format("%s %s", COUNT_WORDS[count], DIGIT_WORDS[digit - '0']));
            }
          }

          if (i != part) {
            digit = N.charAt(index);
            count = 1;
          }
        }

        if (i != part) {
          ++index;
        }
      }
    }

    return String.join(" ", result);
  }
}
