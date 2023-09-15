import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int Rs = sc.nextInt();
      int Rh = sc.nextInt();
      int N = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
      }
      int M = sc.nextInt();
      int[] Z = new int[M];
      int[] W = new int[M];
      for (int i = 0; i < M; ++i) {
        Z[i] = sc.nextInt();
        W[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(Rs, Rh, X, Y, Z, W)));
    }

    sc.close();
  }

  static String solve(int Rs, int Rh, int[] X, int[] Y, int[] Z, int[] W) {
    long minRedSquaredDistance =
        IntStream.range(0, X.length)
            .mapToLong(i -> square(X[i]) + square(Y[i]))
            .min()
            .orElse(Long.MAX_VALUE);
    long minYellowSquaredDistance =
        IntStream.range(0, Z.length)
            .mapToLong(i -> square(Z[i]) + square(W[i]))
            .min()
            .orElse(Long.MAX_VALUE);

    return String.format(
        "%d %d",
        IntStream.range(0, X.length)
            .filter(
                i ->
                    square(X[i]) + square(Y[i])
                        <= Math.min(square(Rs + Rh), minYellowSquaredDistance))
            .count(),
        IntStream.range(0, Z.length)
            .filter(
                i ->
                    square(Z[i]) + square(W[i]) <= Math.min(square(Rs + Rh), minRedSquaredDistance))
            .count());
  }

  static int square(int x) {
    return x * x;
  }
}
