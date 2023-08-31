import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String s = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(s)));
    }

    sc.close();
  }

  static int solve(String s) {
    return IntStream.range(0, s.length())
        .map(
            i ->
                (int)
                    IntStream.rangeClosed(i - 1, i + 1)
                        .filter(j -> j >= 0 && j < s.length())
                        .map(s::charAt)
                        .distinct()
                        .count())
        .reduce(Main::multiplyMod)
        .getAsInt();
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }
}
