import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int N = sc.nextInt();
      int[] X = new int[W];
      for (int i = 0; i < X.length; ++i) {
        X[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, N)));
    }

    sc.close();
  }

  static long solve(int[] X, int N) {
    int[] sorted = Arrays.stream(X).boxed().sorted().mapToInt(x -> x).toArray();

    PriorityQueue<Integer> lefts = new PriorityQueue<>();
    long leftSum = 0;
    for (int x : X) {
      lefts.offer(x - N);
      leftSum += x - N;
    }

    PriorityQueue<Integer> rights = new PriorityQueue<>();
    long rightSum = 0;

    long result = Long.MAX_VALUE;
    for (int target : sorted) {
      while (!rights.isEmpty() && rights.peek() <= target) {
        int head = rights.poll();
        rightSum -= head;

        lefts.offer(head);
        leftSum += head;
      }

      while (!lefts.isEmpty() && target - lefts.peek() > Math.abs(lefts.peek() + N - target)) {
        int head = lefts.poll();
        leftSum -= head;

        int next = head + N;
        if (next <= target) {
          lefts.offer(next);
          leftSum += next;
        } else {
          rights.offer(next);
          rightSum += next;
        }
      }

      result =
          Math.min(
              result,
              ((long) target * lefts.size() - leftSum)
                  + (rightSum - (long) target * rights.size()));
    }

    return result;
  }
}