// https://paste.ofcode.org/35q7xkSCybHXcjAmCWWhqCL
// https://en.wikipedia.org/wiki/Simulated_annealing

import java.util.Random;
import java.util.Scanner;

public class Solution {
  static final Random RANDOM = new Random();
  static final int INITIAL_A_SEARCH_SIZE = 100;
  static final double BEGIN_STEP = 100;
  static final double END_STEP = 1e-9;
  static final double STEP_LEARNING_RATE = 0.9;
  static final int RANDOM_SEARCH_NUM = 4;
  static final int ITERATION_NUM = 50;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int P = sc.nextInt();
      int H = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(X, Y, P, H)));
    }

    sc.close();
  }

  static double solve(int[] X, int[] Y, int P, int H) {
    double result = 0;
    double aUpperBound = -4.0 * H / (P * P);
    for (int i = 0; i <= INITIAL_A_SEARCH_SIZE; ++i) {
      result = Math.max(result, go(X, Y, P, H, aUpperBound * i / INITIAL_A_SEARCH_SIZE));
    }

    return result;
  }

  static double go(int[] X, int[] Y, int P, int H, double a) {
    a = Math.min(0, a);
    double result = eval(X, Y, P, H, a);

    double step = BEGIN_STEP;
    while (step > END_STEP) {
      double nextA = a;
      for (int i = 0; i < RANDOM_SEARCH_NUM; ++i) {
        double candidateA = a + (RANDOM.nextDouble() * 2 - 1) * step;
        double r = eval(X, Y, P, H, candidateA);
        if (r > result) {
          result = r;
          nextA = candidateA;
        }
      }

      a = Math.min(0, nextA);
      step *= STEP_LEARNING_RATE;
    }

    return result;
  }

  static double eval(int[] X, int[] Y, int P, int H, double a) {
    a = Math.min(0, a);

    double top = a * P * P / -4;
    double r = H - top;
    for (int i = 0; i < X.length; ++i) {
      r = Math.min(r, dis(X[i], Y[i], P, a));
    }

    return r;
  }

  static double dis(int xi, int yi, int P, double a) {
    return Math.min(search(xi, yi, P, a, 0, P / 2.0), search(xi, yi, P, a, P / 2.0, P));
  }

  static double search(int xi, int yi, int P, double a, double left, double right) {
    double result = Double.MAX_VALUE;
    Point obstacle = new Point(xi, yi);
    for (int i = 0; i < ITERATION_NUM; ++i) {
      double m1 = left + (right - left) / 3;
      Point p1 = computePoint(P, a, m1);
      double d1 = computeDistance(p1, obstacle);

      double m2 = right - (right - left) / 3;
      Point p2 = computePoint(P, a, m2);
      double d2 = computeDistance(p2, obstacle);

      if (d1 > d2) {
        left = m1;
      } else {
        right = m2;
      }

      result = Math.min(result, Math.min(d1, d2));
    }

    return result;
  }

  static Point computePoint(int P, double a, double x) {
    return new Point(x, a * x * (x - P));
  }

  static double computeDistance(Point p1, Point p2) {
    double dx = p1.x - p2.x;
    double dy = p1.y - p2.y;

    return Math.sqrt(dx * dx + dy * dy);
  }
}

class Point {
  double x;
  double y;

  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
}
