import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Solution {
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

    @SuppressWarnings("unchecked")
    Stack<Integer>[] rightSumToMinLengths = new Stack[K + 1];
    for (int i = 0; i < rightSumToMinLengths.length; ++i) {
      rightSumToMinLengths[i] = new Stack<>();
    }
    for (int i = B.length - 1; i >= 0; --i) {
      int rightSum = 0;
      for (int rightIndex = i; rightIndex < B.length; ++rightIndex) {
        rightSum += B[rightIndex];
        if (rightSum > K) {
          break;
        }

        if (rightSumToMinLengths[rightSum].empty()
            || rightIndex - i + 1 <= rightSumToMinLengths[rightSum].peek()) {
          rightSumToMinLengths[rightSum].push(rightIndex - i + 1);
        }
      }
    }

    int result =
        rightSumToMinLengths[K].empty() ? Integer.MAX_VALUE : rightSumToMinLengths[K].peek();

    for (int i = 0; i < B.length; ++i) {
      int rightSum = 0;
      for (int rightIndex = i; rightIndex < B.length; ++rightIndex) {
        rightSum += B[rightIndex];
        if (rightSum > K) {
          break;
        }

        if (!rightSumToMinLengths[rightSum].empty()
            && rightSumToMinLengths[rightSum].peek() == rightIndex - i + 1) {
          rightSumToMinLengths[rightSum].pop();
        }
      }

      int leftSum = 0;
      for (int leftIndex = i; leftIndex >= 0; --leftIndex) {
        leftSum += B[leftIndex];
        if (leftSum > K) {
          break;
        }

        leftSumToMinLength[leftSum] = Math.min(leftSumToMinLength[leftSum], i - leftIndex + 1);

        if (!rightSumToMinLengths[K - leftSum].empty()) {
          result =
              Math.min(
                  result, leftSumToMinLength[leftSum] + rightSumToMinLengths[K - leftSum].peek());
        }
      }
    }

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }
}