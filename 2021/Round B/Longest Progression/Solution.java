import java.util.Scanner;

public class Solution {
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

  static int solve(int[] A) {
    int N = A.length;

    int[] leftLengths = new int[N];
    leftLengths[0] = 1;
    for (int i = 1; i < leftLengths.length; ++i) {
      leftLengths[i] =
          (i != 1 && A[i] - A[i - 1] == A[i - 1] - A[i - 2]) ? (leftLengths[i - 1] + 1) : 2;
    }

    int[] rightLengths = new int[N];
    rightLengths[rightLengths.length - 1] = 1;
    for (int i = rightLengths.length - 2; i >= 0; --i) {
      rightLengths[i] =
          (i != rightLengths.length - 2 && A[i + 1] - A[i] == A[i + 2] - A[i + 1])
              ? (rightLengths[i + 1] + 1)
              : 2;
    }

    int result = 0;
    for (int i = 0; i < N; ++i) {
      if (i != 0) {
        result = Math.max(result, leftLengths[i - 1] + 1);
      }
      if (i != N - 1) {
        result = Math.max(result, rightLengths[i + 1] + 1);
      }
      if (i != 0 && i != N - 1 && (A[i + 1] - A[i - 1]) % 2 == 0) {
        int diff = (A[i + 1] - A[i - 1]) / 2;
        result =
            Math.max(
                result,
                1
                    + ((i != 1 && diff == A[i - 1] - A[i - 2]) ? leftLengths[i - 1] : 1)
                    + ((i != N - 2 && diff == A[i + 2] - A[i + 1]) ? rightLengths[i + 1] : 1));
      }
    }

    return result;
  }
}