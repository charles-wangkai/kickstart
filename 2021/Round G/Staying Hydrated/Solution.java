import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int K = sc.nextInt();
      int[] x1 = new int[K];
      int[] y1 = new int[K];
      int[] x2 = new int[K];
      int[] y2 = new int[K];
      for (int i = 0; i < K; ++i) {
        x1[i] = sc.nextInt();
        y1[i] = sc.nextInt();
        x2[i] = sc.nextInt();
        y2[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(x1, y1, x2, y2)));
    }

    sc.close();
  }

  static String solve(int[] x1, int[] y1, int[] x2, int[] y2) {
    return String.format("%d %d", findCoordinate(x1, x2), findCoordinate(y1, y2));
  }

  static int findCoordinate(int[] lowers, int[] uppers) {
    int[] coordinates =
        IntStream.concat(Arrays.stream(lowers), Arrays.stream(uppers))
            .boxed()
            .sorted()
            .distinct()
            .mapToInt(x -> x)
            .toArray();

    PriorityQueue<Integer> rights = new PriorityQueue<>(Comparator.comparing(i -> lowers[i]));
    for (int i = 0; i < lowers.length; ++i) {
      rights.offer(i);
    }
    long rightSum = Arrays.stream(lowers).asLongStream().sum();
    long leftSum = 0;
    int leftNum = 0;
    PriorityQueue<Integer> middles = new PriorityQueue<>(Comparator.comparing(i -> uppers[i]));
    long minDistanceSum = Long.MAX_VALUE;
    int result = Integer.MIN_VALUE;

    for (int coordinate : coordinates) {
      while (!rights.isEmpty() && lowers[rights.peek()] < coordinate) {
        rightSum -= lowers[rights.peek()];
        middles.offer(rights.poll());
      }
      while (!middles.isEmpty() && uppers[middles.peek()] < coordinate) {
        leftSum += uppers[middles.poll()];
        ++leftNum;
      }

      long distanceSum =
          (rightSum - (long) rights.size() * coordinate) + ((long) leftNum * coordinate - leftSum);
      if (distanceSum < minDistanceSum) {
        minDistanceSum = distanceSum;
        result = coordinate;
      }
    }

    return result;
  }
}