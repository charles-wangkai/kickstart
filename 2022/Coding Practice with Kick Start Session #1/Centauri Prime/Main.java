import java.util.Scanner;
import java.util.Set;

public class Main {
  static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u');

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String name = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(name)));
    }

    sc.close();
  }

  static String solve(String name) {
    char last = Character.toLowerCase(name.charAt(name.length() - 1));

    String ruler;
    if (last == 'y') {
      ruler = "nobody";
    } else if (VOWELS.contains(last)) {
      ruler = "Alice";
    } else {
      ruler = "Bob";
    }

    return String.format("%s is ruled by %s.", name, ruler);
  }
}
