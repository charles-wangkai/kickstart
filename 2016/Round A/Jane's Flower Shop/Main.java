import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int ITERATION_NUM = 100;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int M = sc.nextInt();
      int[] C = new int[M + 1];
      for (int i = 0; i < C.length; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(C)));
    }

    sc.close();
  }

  static double solve(int[] C) {
    double lower = -1;
    double upper = 1;
    for (int i = 0; i < ITERATION_NUM; ++i) {
      double middle = (lower + upper) / 2;
      if (f(C, middle) < 0) {
        upper = middle;
      } else {
        lower = middle;
      }
    }

    return (lower + upper) / 2;
  }

  static double f(int[] C, double r) {
    return IntStream.range(0, C.length)
        .mapToDouble(i -> ((i == 0) ? -1 : 1) * C[i] * Math.pow(1 + r, C.length - 1 - i))
        .sum();
  }
}
