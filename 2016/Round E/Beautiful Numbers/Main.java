import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long N = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(N)));
    }

    sc.close();
  }

  static long solve(long N) {
    long result = N - 1;
    for (int i = 2; i <= 59; ++i) {
      int base = (int) Math.floor(Math.pow(N, 1.0 / i));
      if (base <= 1) {
        break;
      }

      long sum = 0;
      long power = 1;
      for (int j = 0; j <= i; ++j) {
        sum += power;
        power *= base;
      }
      if (sum == N) {
        result = base;
      }
    }

    return result;
  }
}
