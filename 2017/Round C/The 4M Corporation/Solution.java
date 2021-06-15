import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int MINIMUM = sc.nextInt();
      int MAXIMUM = sc.nextInt();
      int MEAN = sc.nextInt();
      int MEDIAN = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(MINIMUM, MAXIMUM, MEAN, MEDIAN)));
    }

    sc.close();
  }

  static String solve(int MINIMUM, int MAXIMUM, int MEAN, int MEDIAN) {
    if (!(MINIMUM <= MAXIMUM
        && MEAN >= MINIMUM
        && MEAN <= MAXIMUM
        && MEDIAN >= MINIMUM
        && MEDIAN <= MAXIMUM)) {
      return "IMPOSSIBLE";
    }
    if (MINIMUM == MAXIMUM && MINIMUM == MEAN && MINIMUM == MEDIAN) {
      return "1";
    }
    if (MEAN == MEDIAN && MEAN * 2 == MINIMUM + MAXIMUM) {
      return "2";
    }

    int result =
        Math.min(
            computeMinOddSize(MINIMUM, MAXIMUM, MEAN, MEDIAN),
            computeMinEvenSize(MINIMUM, MAXIMUM, MEAN, MEDIAN));

    return (result == Integer.MAX_VALUE) ? "IMPOSSIBLE" : String.valueOf(result);
  }

  static int computeMinOddSize(int MINIMUM, int MAXIMUM, int MEAN, int MEDIAN) {
    int diff = MINIMUM + MEDIAN + MAXIMUM - MEAN * 3;
    if (diff == 0) {
      return 3;
    }
    if (diff < 0) {
      return computeMinOddSize(-MAXIMUM, -MINIMUM, -MEAN, -MEDIAN);
    }

    if (MINIMUM + MEDIAN >= MEAN * 2) {
      return Integer.MAX_VALUE;
    }

    return 3 + divideToCeil(diff, (MEAN * 2 - (MINIMUM + MEDIAN))) * 2;
  }

  static int computeMinEvenSize(int MINIMUM, int MAXIMUM, int MEAN, int MEDIAN) {
    int diff = MINIMUM + MEDIAN + MEDIAN + MAXIMUM - MEAN * 4;
    if (diff == 0) {
      return 4;
    }
    if (diff < 0) {
      return computeMinEvenSize(-MAXIMUM, -MINIMUM, -MEAN, -MEDIAN);
    }

    if (MINIMUM + MEDIAN >= MEAN * 2) {
      return Integer.MAX_VALUE;
    }

    return 4 + divideToCeil(diff, (MEAN * 2 - (MINIMUM + MEDIAN))) * 2;
  }

  static int divideToCeil(int x, int y) {
    return (x + y - 1) / y;
  }
}
