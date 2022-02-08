import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] M = new int[N];
      for (int i = 0; i < M.length; ++i) {
        M[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(M, K)));
    }

    sc.close();
  }

  static int solve(int[] M, int K) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
    for (int i = 0; i < M.length - 1; ++i) {
      pq.offer(M[i + 1] - M[i]);
    }

    for (int i = 0; i < K; ++i) {
      if (pq.peek() != 1) {
        int head = pq.poll();
        pq.offer(head / 2);
        pq.offer(head - head / 2);
      }
    }

    return pq.peek();
  }
}