import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String W = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(W)));
    }

    sc.close();
  }

  static String solve(String W) {
    if (W.length() % 2 != 0) {
      return "AMBIGUOUS";
    }

    char[] result = new char[W.length()];
    result[1] = W.charAt(0);
    for (int i = 3; i < result.length; i += 2) {
      result[i] = (char) ((W.charAt(i - 1) - result[i - 2] + 26) % 26 + 'A');
    }
    result[result.length - 2] = W.charAt(W.length() - 1);
    for (int i = result.length - 4; i >= 0; i -= 2) {
      result[i] = (char) ((W.charAt(i + 1) - result[i + 2] + 26) % 26 + 'A');
    }

    return new String(result);
  }
}
