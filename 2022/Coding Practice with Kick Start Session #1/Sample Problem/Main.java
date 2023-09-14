import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] C = new int[N];
      for (int i = 0; i < C.length; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(C, M)));
    }

    sc.close();
  }

  static int solve(int[] C, int M) {
    return Arrays.stream(C).sum() % M;
  }
}
