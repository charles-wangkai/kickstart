import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] L = new int[N];
      for (int i = 0; i < L.length; ++i) {
        L[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L)));
    }

    sc.close();
  }

  static long solve(int[] L) {
    Arrays.sort(L);

    Map<Integer, Integer> lengthToCount = new HashMap<>();
    for (int length : L) {
      lengthToCount.put(length, lengthToCount.getOrDefault(length, 0) + 1);
    }

    long result = 0;
    for (int length : lengthToCount.keySet()) {
      int count = lengthToCount.get(length);

      if (count >= 3) {
        result += C(count, 3) * (findNum(L, 1, length * 3L - 1) - count);
      }

      if (count >= 2) {
        for (int top : L) {
          if (top != length) {
            int lowerValue = top + 1;
            long upperValue = top + length * 2L - 1;

            result +=
                C(count, 2)
                    * (findNum(L, lowerValue, upperValue)
                        - ((length >= lowerValue && length <= upperValue) ? count : 0));
          }
        }
      }
    }

    return result;
  }

  static long C(int n, int r) {
    long result = 1;
    for (int i = 0; i < r; ++i) {
      result = result * (n - i) / (i + 1);
    }

    return result;
  }

  static int findNum(int[] L, int lowerValue, long upperValue) {
    return Math.max(0, findRightIndex(L, upperValue) - findLeftIndex(L, lowerValue) + 1);
  }

  static int findLeftIndex(int[] L, int lowerValue) {
    int result = L.length;
    int lowerIndex = 0;
    int upperIndex = L.length - 1;
    while (lowerIndex <= upperIndex) {
      int middleIndex = (lowerIndex + upperIndex) / 2;
      if (L[middleIndex] >= lowerValue) {
        result = middleIndex;
        upperIndex = middleIndex - 1;
      } else {
        lowerIndex = middleIndex + 1;
      }
    }

    return result;
  }

  static int findRightIndex(int[] L, long upperValue) {
    int result = -1;
    int lowerIndex = 0;
    int upperIndex = L.length - 1;
    while (lowerIndex <= upperIndex) {
      int middleIndex = (lowerIndex + upperIndex) / 2;
      if (L[middleIndex] <= upperValue) {
        result = middleIndex;
        lowerIndex = middleIndex + 1;
      } else {
        upperIndex = middleIndex - 1;
      }
    }

    return result;
  }
}
