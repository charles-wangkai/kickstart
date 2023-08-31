import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] P = new int[N];
      long[] K = new long[N];
      for (int i = 0; i < N; ++i) {
        P[i] = sc.nextInt();
        K[i] = sc.nextLong();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(P, K)));
    }

    sc.close();
  }

  static long solve(int[] P, long[] K) {
    long lower = Arrays.stream(K).max().getAsLong();
    long upper = Long.MAX_VALUE;
    for (int i = 0; i < P.length; ++i) {
      lower = Math.max(lower, (100 * K[i] + P[i] + 1) / (P[i] + 1));

      if (P[i] != 0) {
        upper = Math.min(upper, 100 * K[i] / P[i]);
      }
    }

    return (lower == upper) ? lower : -1;
  }
}
