import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

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
    Map<Integer, SortedMap<Integer, Integer>> leftSumToCountByLength = new HashMap<>();
    Map<Integer, SortedMap<Integer, Integer>> rightSumToCountByLength = new HashMap<>();
    for (int i = 0; i < B.length; ++i) {
      int rightSum = 0;
      for (int j = i; j < B.length; ++j) {
        rightSum += B[j];
        if (!rightSumToCountByLength.containsKey(rightSum)) {
          rightSumToCountByLength.put(rightSum, new TreeMap<>());
        }
        SortedMap<Integer, Integer> countByLength = rightSumToCountByLength.get(rightSum);
        countByLength.put(j - i + 1, countByLength.getOrDefault(j - i + 1, 0) + 1);
      }
    }

    int result =
        rightSumToCountByLength.containsKey(K)
            ? rightSumToCountByLength.get(K).firstKey()
            : Integer.MAX_VALUE;

    for (int i = 0; i < B.length; ++i) {
      int leftSum = 0;
      for (int leftIndex = i; leftIndex >= 0; --leftIndex) {
        leftSum += B[leftIndex];
        if (!leftSumToCountByLength.containsKey(leftSum)) {
          leftSumToCountByLength.put(leftSum, new TreeMap<>());
        }
        SortedMap<Integer, Integer> countByLength = leftSumToCountByLength.get(leftSum);
        countByLength.put(i - leftIndex + 1, countByLength.getOrDefault(i - leftIndex + 1, 0) + 1);
      }

      int rightSum = 0;
      for (int rightIndex = i; rightIndex < B.length; ++rightIndex) {
        rightSum += B[rightIndex];
        SortedMap<Integer, Integer> countByLength = rightSumToCountByLength.get(rightSum);
        int length = rightIndex - i + 1;
        countByLength.put(length, countByLength.get(length) - 1);
        countByLength.remove(length, 0);
        if (countByLength.isEmpty()) {
          rightSumToCountByLength.remove(rightSum);
        }
      }

      for (int lSum = 1; lSum < K; ++lSum) {
        if (leftSumToCountByLength.containsKey(lSum)
            && rightSumToCountByLength.containsKey(K - lSum)) {
          result =
              Math.min(
                  result,
                  leftSumToCountByLength.get(lSum).firstKey()
                      + rightSumToCountByLength.get(K - lSum).firstKey());
        }
      }
    }

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }
}