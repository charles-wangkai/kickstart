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

      System.out.println(String.format("Case #%d: %s", tc, solve(A) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] A) {
    int N = A.length;

    int leftIndex;
    int rightIndex;
    boolean leftOrRight;
    if (N % 2 == 0) {
      leftIndex = (N - 1) / 2;
      rightIndex = leftIndex + 1;
      leftOrRight = true;
    } else {
      rightIndex = (N - 1) / 2;
      leftIndex = rightIndex - 1;
      leftOrRight = false;
    }

    int minValue = 1;
    int maxValue = N;
    for (int i = 0; i < N; ++i) {
      int index = leftOrRight ? leftIndex : rightIndex;
      if (A[index] == minValue) {
        ++minValue;
      } else if (A[index] == maxValue) {
        --maxValue;
      } else {
        return false;
      }

      if (leftOrRight) {
        --leftIndex;
      } else {
        ++rightIndex;
      }
      leftOrRight = !leftOrRight;
    }

    return true;
  }
}
