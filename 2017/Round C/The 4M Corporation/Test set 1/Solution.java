import java.util.Arrays;
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

    for (int size = 1; size <= 13; ++size) {
      if (isPossible(MINIMUM, MAXIMUM, MEAN, MEDIAN, size)) {
        return String.valueOf(size);
      }
    }

    return "IMPOSSIBLE";
  }

  static boolean isPossible(int MINIMUM, int MAXIMUM, int MEAN, int MEDIAN, int size) {
    if (size == 1) {
      return MINIMUM == MAXIMUM && MINIMUM == MEAN && MINIMUM == MEDIAN;
    }
    if (size == 2) {
      return MEAN == MEDIAN && MEAN * 2 == MINIMUM + MAXIMUM;
    }

    int[] a = new int[size];
    Arrays.fill(a, -1);

    a[0] = MINIMUM;
    a[size - 1] = MAXIMUM;

    if (size % 2 == 0) {
      for (int i = MINIMUM; i <= MEDIAN; ++i) {
        a[size / 2 - 1] = i;
        a[size / 2] = MEDIAN * 2 - i;

        if (a[size / 2] <= MAXIMUM && search(a, 0, MEAN * size - MINIMUM - MAXIMUM - MEDIAN * 2)) {
          return true;
        }
      }

      return false;
    }

    a[size / 2] = MEDIAN;

    return search(a, 0, MEAN * size - MINIMUM - MAXIMUM - MEDIAN);
  }

  static boolean search(int[] a, int index, int rest) {
    if (rest < 0) {
      return false;
    }
    if (index == a.length) {
      return rest == 0;
    }
    if (a[index] != -1) {
      return search(a, index + 1, rest);
    }

    int limitIndex = index + 1;
    while (a[limitIndex] == -1) {
      ++limitIndex;
    }

    for (int i = a[index - 1]; i <= a[limitIndex]; ++i) {
      a[index] = i;

      if (i <= rest && search(a, index + 1, rest - i)) {
        return true;
      }

      a[index] = -1;
    }

    return false;
  }
}
