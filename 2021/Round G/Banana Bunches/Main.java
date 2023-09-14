import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(B, K)));
    }

    sc.close();
  }

  static int solve(int[] B, int K) {
    int[] leftSumToMinLength = new int[K + 1];
    Arrays.fill(leftSumToMinLength, Integer.MAX_VALUE);
    leftSumToMinLength[0] = 0;

    int result = Integer.MAX_VALUE;
    for (int i = 0; i < B.length; ++i) {
      int leftSum = 0;
      for (int leftIndex = i - 1; leftIndex >= 0; --leftIndex) {
        leftSum += B[leftIndex];
        if (leftSum > K) {
          break;
        }

        leftSumToMinLength[leftSum] = Math.min(leftSumToMinLength[leftSum], i - leftIndex);
      }

      int rightSum = 0;
      for (int rightIndex = i; rightIndex < B.length; ++rightIndex) {
        rightSum += B[rightIndex];
        if (rightSum > K) {
          break;
        }

        if (leftSumToMinLength[K - rightSum] != Integer.MAX_VALUE) {
          result = Math.min(result, leftSumToMinLength[K - rightSum] + (rightIndex - i + 1));
        }
      }
    }

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }
}
