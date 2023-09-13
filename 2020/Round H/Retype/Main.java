import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int S = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, K, S)));
    }

    sc.close();
  }

  static int solve(int N, int K, int S) {
    return K + Math.min(N, K - S + N - S);
  }
}
