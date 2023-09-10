// https://codingcompetitions.withgoogle.com/kickstart/round/000000000019ffc8/00000000002d8565#analysis

import java.util.Scanner;

public class Main {
  static final int LIMIT = 100000;

  static double[] factorialLogs;

  public static void main(String[] args) {
    buildFactorialLogs();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int H = sc.nextInt();
      int L = sc.nextInt() - 1;
      int U = sc.nextInt() - 1;
      int R = sc.nextInt() - 1;
      int D = sc.nextInt() - 1;

      System.out.println(String.format("Case #%d: %.9f", tc, solve(W, H, L, U, R, D)));
    }

    sc.close();
  }

  static void buildFactorialLogs() {
    factorialLogs = new double[2 * LIMIT - 1];
    for (int i = 1; i < factorialLogs.length; ++i) {
      factorialLogs[i] = factorialLogs[i - 1] + Math.log(i) / Math.log(2);
    }
  }

  static double solve(int W, int H, int L, int U, int R, int D) {
    double result = 0;
    for (int r = D + 1, c = L - 1; r < H && c >= 0; ++r, --c) {
      if (r == H - 1) {
        for (int i = 0; i <= c; ++i) {
          result += computeCombOverTwoPower(r - 1 + i, i) / 2;
        }
      } else {
        result += computeCombOverTwoPower(r + c, c);
      }
    }
    for (int r = U - 1, c = R + 1; r >= 0 && c < W; --r, ++c) {
      if (c == W - 1) {
        for (int i = 0; i <= r; ++i) {
          result += computeCombOverTwoPower(i + c - 1, i) / 2;
        }
      } else {
        result += computeCombOverTwoPower(r + c, r);
      }
    }

    return result;
  }

  static double computeCombOverTwoPower(int n, int k) {
    return Math.pow(2, factorialLogs[n] - factorialLogs[k] - factorialLogs[n - k] - n);
  }
}
