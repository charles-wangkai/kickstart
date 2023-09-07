import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int P = sc.nextInt();
      String[] forbiddens = new String[P];
      for (int i = 0; i < forbiddens.length; ++i) {
        forbiddens[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, forbiddens)));
    }

    sc.close();
  }

  static long solve(int N, String[] forbiddens) {
    return (1L << N)
        - Arrays.stream(forbiddens)
            .filter(
                forbidden ->
                    Arrays.stream(forbiddens)
                        .allMatch(f -> f.equals(forbidden) || !forbidden.startsWith(f)))
            .mapToLong(forbidden -> 1L << (N - forbidden.length()))
            .sum();
  }
}
