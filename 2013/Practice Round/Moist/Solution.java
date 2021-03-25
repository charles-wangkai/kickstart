import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      sc.nextLine();
      String[] names = new String[N];
      for (int i = 0; i < names.length; ++i) {
        names[i] = sc.nextLine();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(names)));
    }

    sc.close();
  }

  static int solve(String[] names) {
    int result = 0;
    String max = "";
    for (String name : names) {
      if (name.compareTo(max) < 0) {
        ++result;
      } else {
        max = name;
      }
    }

    return result;
  }
}
