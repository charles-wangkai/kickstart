import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int M = sc.nextInt();
      int N = sc.nextInt();
      int P = sc.nextInt() - 1;
      int[][] S = new int[M][N];
      for (int r = 0; r < M; ++r) {
        for (int c = 0; c < N; ++c) {
          S[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, P)));
    }

    sc.close();
  }

  static int solve(int[][] S, int P) {
    return IntStream.range(0, S[0].length)
        .map(
            c ->
                Math.max(
                    0,
                    IntStream.range(0, S.length)
                            .filter(r -> r != P)
                            .map(r -> S[r][c])
                            .max()
                            .getAsInt()
                        - S[P][c]))
        .sum();
  }
}
