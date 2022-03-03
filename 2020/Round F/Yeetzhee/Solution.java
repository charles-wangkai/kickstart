import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[K];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N, M, A)));
    }

    sc.close();
  }

  static double solve(int N, int M, int[] A) {
    int[] targetNums = Arrays.stream(Arrays.copyOf(A, M)).sorted().toArray();

    double result = 0;
    Map<List<Integer>, Double> numsToProb =
        Map.of(IntStream.range(0, M).map(i -> 0).boxed().collect(Collectors.toList()), 1.0);
    for (int i = 0; i < N; ++i) {
      Map<List<Integer>, Double> nextNumsToProb = new HashMap<>();
      for (List<Integer> nums : numsToProb.keySet()) {
        double prob = numsToProb.get(nums);

        int nextCount = 0;
        for (int j = 0; j < M; ++j) {
          List<Integer> nextNums = buildNextNums(nums, j);
          if (check(targetNums, nextNums)) {
            ++nextCount;
          }
        }
        result += prob * ((double) M / nextCount);

        for (int j = 0; j < M; ++j) {
          List<Integer> nextNums = buildNextNums(nums, j);
          if (check(targetNums, nextNums)) {
            nextNumsToProb.put(
                nextNums, nextNumsToProb.getOrDefault(nextNums, 0.0) + prob / nextCount);
          }
        }
      }

      numsToProb = nextNumsToProb;
    }

    return result;
  }

  static List<Integer> buildNextNums(List<Integer> nums, int index) {
    List<Integer> result = new ArrayList<>(nums);
    result.set(index, result.get(index) + 1);
    Collections.sort(result);

    return result;
  }

  static boolean check(int[] targetNums, List<Integer> nums) {
    return IntStream.range(0, targetNums.length).allMatch(i -> targetNums[i] >= nums.get(i));
  }
}