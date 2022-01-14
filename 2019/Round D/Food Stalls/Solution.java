import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int K = sc.nextInt();
      int N = sc.nextInt();
      int[] X = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
      }
      int[] C = new int[N];
      for (int i = 0; i < N; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(K, X, C)));
    }

    sc.close();
  }

  static long solve(int K, int[] X, int[] C) {
    int[] sortedIndices =
        IntStream.range(0, X.length)
            .boxed()
            .sorted(Comparator.comparing(i -> X[i]))
            .mapToInt(x -> x)
            .toArray();

    long rightSum = 0;
    SortedMap<Integer, Integer> rightSmallCostToCount = new TreeMap<>();
    for (int i = 1; i < sortedIndices.length; ++i) {
      int cost = C[sortedIndices[i]] + X[sortedIndices[i]] - X[sortedIndices[0]];
      rightSum += cost;
      updateMap(rightSmallCostToCount, cost, 1);
    }
    SortedMap<Integer, Integer> rightLargeCostToCount = new TreeMap<>();
    for (int i = 0; i < X.length - 1 - K; ++i) {
      int cost = rightSmallCostToCount.lastKey();
      rightSum -= cost;
      updateMap(rightSmallCostToCount, cost, -1);
      updateMap(rightLargeCostToCount, cost, 1);
    }

    long result = C[sortedIndices[0]] + rightSum;
    long leftSum = 0;
    PriorityQueue<Integer> leftSmallCosts = new PriorityQueue<>(Comparator.reverseOrder());
    PriorityQueue<Integer> leftLargeCosts = new PriorityQueue<>();
    for (int i = 1; i < sortedIndices.length; ++i) {
      int offset = X[sortedIndices[i]] - X[sortedIndices[0]];

      int leftCost = C[sortedIndices[i - 1]] + X[sortedIndices[0]] - X[sortedIndices[i - 1]];
      leftLargeCosts.offer(leftCost);
      int cost = leftLargeCosts.poll();
      leftSum += cost;
      leftSmallCosts.offer(cost);

      int rightCost = C[sortedIndices[i]] + X[sortedIndices[i]] - X[sortedIndices[0]];
      if (rightLargeCostToCount.containsKey(rightCost)) {
        updateMap(rightLargeCostToCount, rightCost, -1);

        if (rightSmallCostToCount.isEmpty()) {
          cost = leftSmallCosts.poll();
          leftSum -= cost;
          leftLargeCosts.offer(cost);
        } else {
          cost = rightSmallCostToCount.lastKey();
          rightSum -= cost;
          updateMap(rightSmallCostToCount, cost, -1);
          updateMap(rightLargeCostToCount, cost, 1);
        }
      } else {
        rightSum -= rightCost;
        updateMap(rightSmallCostToCount, rightCost, -1);
      }

      while (!rightLargeCostToCount.isEmpty()
          && !leftSmallCosts.isEmpty()
          && rightLargeCostToCount.firstKey() - offset < leftSmallCosts.peek() + offset) {
        cost = leftSmallCosts.poll();
        leftSum -= cost;
        leftLargeCosts.offer(cost);

        cost = rightLargeCostToCount.firstKey();
        rightSum += cost;
        updateMap(rightSmallCostToCount, cost, 1);
        updateMap(rightLargeCostToCount, cost, -1);
      }

      result =
          Math.min(
              result,
              C[sortedIndices[i]]
                  + (leftSum + (long) offset * leftSmallCosts.size())
                  + (rightSum - (long) offset * (K - leftSmallCosts.size())));
    }

    return result;
  }

  static void updateMap(Map<Integer, Integer> map, int key, int delta) {
    map.put(key, map.getOrDefault(key, 0) + delta);
    map.remove(key, 0);
  }
}