import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String I = sc.next();
      String P = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(I, P)));
    }

    sc.close();
  }

  static String solve(String I, String P) {
    int beginIndex = 0;
    for (char c : I.toCharArray()) {
      int index = P.indexOf(c, beginIndex);
      if (index == -1) {
        return "IMPOSSIBLE";
      }

      beginIndex = index + 1;
    }

    return String.valueOf(P.length() - I.length());
  }
}