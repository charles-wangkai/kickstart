import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static long solve(int[] A) {
    long result = 0;
    Map<Integer, Integer> valueToCount = new HashMap<>();
    for (int i = 0; i < A.length; ++i) {
      for (int j = i + 1; j < A.length; ++j) {
        if (A[i] == 0 || A[j] == 0) {
          if (A[i] == 0 && A[j] == 0) {
            result += i;
          } else {
            result += valueToCount.getOrDefault(0, 0);
          }
        } else {
          long product = (long) A[i] * A[j];
          if (product <= Integer.MAX_VALUE) {
            result += valueToCount.getOrDefault((int) product, 0);
          }

          if (A[i] != 1 && A[j] != 1) {
            if (A[i] % A[j] == 0) {
              result += valueToCount.getOrDefault(A[i] / A[j], 0);
            } else if (A[j] % A[i] == 0) {
              result += valueToCount.getOrDefault(A[j] / A[i], 0);
            }
          }
        }
      }

      valueToCount.put(A[i], valueToCount.getOrDefault(A[i], 0) + 1);
    }

    return result;
  }
}
