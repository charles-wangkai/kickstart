import java.util.Scanner;

public class Main {
  static final int GLASS_CAPACITY = 250;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int B = sc.nextInt();
      int L = sc.nextInt();
      int N = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(B, L, N)));
    }

    sc.close();
  }

  static double solve(int B, int L, int N) {
    double[][] quantities = {{B * 750}};

    for (int level = 2; level <= L; ++level) {
      double[][] nextQuantities = new double[level][];
      for (int r = 0; r < level; ++r) {
        nextQuantities[r] = new double[r + 1];
      }

      for (int r = 0; r < quantities.length; ++r) {
        for (int c = 0; c < quantities[r].length; ++c) {
          double extra = Math.max(0, quantities[r][c] - GLASS_CAPACITY);

          nextQuantities[r][c] += extra / 3;
          nextQuantities[r + 1][c] += extra / 3;
          nextQuantities[r + 1][c + 1] += extra / 3;
        }
      }

      quantities = nextQuantities;
    }

    int r = 0;
    int c = 0;
    int number = 1;
    while (number != N) {
      if (c == r) {
        ++r;
        c = 0;
      } else {
        ++c;
      }

      ++number;
    }

    return Math.min(GLASS_CAPACITY, quantities[r][c]);
  }
}
