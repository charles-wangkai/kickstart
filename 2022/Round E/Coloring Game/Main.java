import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N)));
    }

    sc.close();
  }

  static int solve(int N) {
    return (N - 1) / 5 + 1;
  }
}
