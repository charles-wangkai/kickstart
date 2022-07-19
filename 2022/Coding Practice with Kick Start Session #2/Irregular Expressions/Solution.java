import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String expression = sc.next();

      System.out.println(
          String.format("Case #%d: %s", tc, solve(expression) ? "Spell!" : "Nothing."));
    }

    sc.close();
  }

  static boolean solve(String expression) {
    return IntStream.range(0, expression.length())
        .anyMatch(
            beginIndex -> {
              int vowelCount = 0;
              int endIndex = beginIndex - 1;
              while (vowelCount <= 1 && endIndex != expression.length() - 1) {
                ++endIndex;
                if (isVowel(expression.charAt(endIndex))) {
                  ++vowelCount;
                }
              }
              if (vowelCount <= 1) {
                return false;
              }

              OptionalInt optionalVowelIndex =
                  IntStream.range(endIndex + 1, expression.length())
                      .filter(i -> isVowel(expression.charAt(i)))
                      .findFirst();
              if (optionalVowelIndex.isEmpty()) {
                return false;
              }

              String prefix = expression.substring(beginIndex, endIndex + 1);

              return IntStream.range(optionalVowelIndex.getAsInt() + 1, expression.length())
                  .anyMatch(i -> expression.startsWith(prefix, i));
            });
  }

  static boolean isVowel(char c) {
    return "aeiou".indexOf(c) >= 0;
  }
}