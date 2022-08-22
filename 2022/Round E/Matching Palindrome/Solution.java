import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String P = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(P)));
    }

    sc.close();
  }

  static String solve(String P) {
    return P.substring(
        0,
        IntStream.rangeClosed(1, P.length())
            .filter(i -> P.length() % i == 0 && P.substring(0, i).repeat(P.length() / i).equals(P))
            .min()
            .getAsInt());
  }
}