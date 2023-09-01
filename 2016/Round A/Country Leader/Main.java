import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
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

      System.out.println(String.format("Case #%d: %s", tc, solve(names)));
    }

    sc.close();
  }

  static String solve(String[] names) {
    return Arrays.stream(names)
        .max(
            Comparator.<String, Integer>comparing(
                    name -> (int) name.chars().filter(c -> c != ' ').distinct().count())
                .thenComparing(Comparator.reverseOrder()))
        .get();
  }
}
