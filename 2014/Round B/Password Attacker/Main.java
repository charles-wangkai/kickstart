import java.util.Scanner;

public class Main {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int M = sc.nextInt();
      int N = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(M, N)));
    }

    sc.close();
  }

  static int solve(int M, int N) {
    int[] wayNums = new int[M + 1];
    wayNums[0] = 1;

    for (int i = 0; i < N; ++i) {
      int[] nextWayNums = new int[wayNums.length];
      for (int j = 0; j <= M; ++j) {
        nextWayNums[j] = addMod(nextWayNums[j], multiplyMod(wayNums[j], j));

        if (j != M) {
          nextWayNums[j + 1] = addMod(nextWayNums[j + 1], multiplyMod(wayNums[j], M - j));
        }
      }

      wayNums = nextWayNums;
    }

    return wayNums[M];
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }
}
