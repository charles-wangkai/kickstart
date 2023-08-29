import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int B = sc.nextInt();
      int[] x1 = new int[B];
      int[] y1 = new int[B];
      int[] x2 = new int[B];
      int[] y2 = new int[B];
      for (int i = 0; i < B; ++i) {
        x1[i] = sc.nextInt();
        y1[i] = sc.nextInt();
        x2[i] = sc.nextInt();
        y2[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(x1, y1, x2, y2)));
    }

    sc.close();
  }

  static String solve(int[] x1, int[] y1, int[] x2, int[] y2) {
    List<Integer> xs = new ArrayList<>();
    List<Integer> ys = new ArrayList<>();
    for (int i = 0; i < x1.length; ++i) {
      for (int x = x1[i]; x <= x2[i]; ++x) {
        for (int y = y1[i]; y <= y2[i]; ++y) {
          xs.add(x);
          ys.add(y);
        }
      }
    }
    Collections.sort(xs);
    Collections.sort(ys);

    int x = 0;
    int y = 0;
    long d = Long.MAX_VALUE;
    for (int i = 0; i < x1.length; ++i) {
      int currentX = computeCenter(xs, x1[i], x2[i]);
      int currentY = computeCenter(ys, y1[i], y2[i]);
      long currentD = computeDistanceSum(xs, ys, currentX, currentY);

      if (currentD < d || (currentD == d && (currentX < x || (currentX == x && currentY < y)))) {
        x = currentX;
        y = currentY;
        d = currentD;
      }
    }

    return String.format("%d %d %d", x, y, d);
  }

  static long computeDistanceSum(List<Integer> xs, List<Integer> ys, int centerX, int centerY) {
    return xs.stream().mapToLong(x -> Math.abs(x - centerX)).sum()
        + ys.stream().mapToLong(y -> Math.abs(y - centerY)).sum();
  }

  static int computeCenter(List<Integer> values, int lower, int upper) {
    if (upper <= values.get((values.size() - 1) / 2)) {
      return upper;
    } else if (lower >= values.get(values.size() / 2)) {
      return lower;
    }

    return values.get((values.size() - 1) / 2);
  }
}
