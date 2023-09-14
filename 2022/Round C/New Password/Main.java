import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String password = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(password)));
    }

    sc.close();
  }

  static String solve(String password) {
    String result = password;
    if (!password.chars().anyMatch(Character::isUpperCase)) {
      result += 'A';
    }
    if (!password.chars().anyMatch(Character::isLowerCase)) {
      result += 'a';
    }
    if (!password.chars().anyMatch(Character::isDigit)) {
      result += '0';
    }
    if (password.chars().allMatch(c -> "#@*&".indexOf(c) == -1)) {
      result += '#';
    }
    result += "A".repeat(Math.max(0, 7 - result.length()));

    return result;
  }
}
