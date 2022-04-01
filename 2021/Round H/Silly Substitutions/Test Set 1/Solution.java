import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(S)));
    }

    sc.close();
  }

  static String solve(String S) {
    String result = S;
    while (true) {
      int oldLength = result.length();
      result =
          result
              .replace("01", "2")
              .replace("12", "3")
              .replace("23", "4")
              .replace("34", "5")
              .replace("45", "6")
              .replace("56", "7")
              .replace("67", "8")
              .replace("78", "9")
              .replace("89", "0")
              .replace("90", "1");

      if (result.length() == oldLength) {
        break;
      }
    }

    return result;
  }
}