import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, K)));
    }

    sc.close();
  }

  static int solve(int[] A, int K) {
    Arrays.sort(A);

    int result = 0;
    int day = 0;
    int index = 0;
    while (index != A.length) {
      int consumedCount = 0;
      while (consumedCount != K && index != A.length) {
        if (A[index] > day) {
          ++result;
          ++consumedCount;
        }

        ++index;
      }

      ++day;
    }

    return result;
  }
}
