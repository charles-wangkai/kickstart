import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long G = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(G)));
    }

    sc.close();
  }

  static int solve(long G) {
    int result = 0;
    for (int i = 1; (long) i * i <= 2 * G; ++i) {
      if (2 * G % i == 0) {
        if (check(G, i)) {
          ++result;
        }
        if (2 * G / i != i && check(G, 2 * G / i)) {
          ++result;
        }
      }
    }

    return result;
  }

  static boolean check(long G, long n) {
    return (2 * G / n - n + 1) % 2 == 0 && (2 * G / n - n + 1) / 2 >= 1;
  }
}
