import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
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
    Set<String> states = Set.of("");
    for (char c : S.toCharArray()) {
      Set<String> nextStates = new HashSet<>();
      for (String state : states) {
        for (int i = 0; i <= 1; ++i) {
          if (c == '?' || c - '0' == i) {
            String suffix = String.format("%s%d", state, i);
            if (check(suffix)) {
              nextStates.add((suffix.length() == 6) ? suffix.substring(1) : suffix);
            }
          }
        }
      }

      states = nextStates;
    }

    return !states.isEmpty();
  }

  static boolean check(String suffix) {
    return !((suffix.length() >= 5 && isPalindrome(suffix.substring(suffix.length() - 5)))
        || (suffix.length() >= 6 && isPalindrome(suffix.substring(suffix.length() - 6))));
  }

  static boolean isPalindrome(String s) {
    for (int i = 0, j = s.length() - 1; i < j; ++i, --j) {
      if (s.charAt(i) != s.charAt(j)) {
        return false;
      }
    }

    return true;
  }
}
