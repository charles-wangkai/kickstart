import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long D = sc.nextLong();
      int N = sc.nextInt();
      int X = sc.nextInt();
      int[] Q = new int[N];
      long[] L = new long[N];
      int[] V = new int[N];
      for (int i = 0; i < N; ++i) {
        Q[i] = sc.nextInt();
        L[i] = sc.nextLong();
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(D, Q, L, V, X)));
    }

    sc.close();
  }

  static long solve(long D, int[] Q, long[] L, int[] V, int X) {
    int[] sortedIndices =
        IntStream.range(0, Q.length)
            .boxed()
            .sorted(Comparator.comparing(i -> L[i]))
            .mapToInt(x -> x)
            .toArray();

    long result = 0;
    int pos = 0;
    PriorityQueue<Element> pq =
        new PriorityQueue<>(Comparator.comparing((Element e) -> e.profit).reversed());
    long d = D - 1;
    while (d >= 0 && (pos != sortedIndices.length || !pq.isEmpty())) {
      while (pos != sortedIndices.length && d + L[sortedIndices[pos]] < D) {
        pq.offer(new Element(V[sortedIndices[pos]], Q[sortedIndices[pos]]));
        ++pos;
      }

      if (pq.isEmpty()) {
        if (pos == sortedIndices.length) {
          break;
        }

        d = D - 1 - L[sortedIndices[pos]];
      } else if (pq.peek().quantity > X) {
        int dayNum =
            (int)
                Math.min(
                    d + 1,
                    Math.min(
                        pq.peek().quantity / X,
                        (pos == sortedIndices.length)
                            ? Integer.MAX_VALUE
                            : (d - (D - 1 - L[sortedIndices[pos]]))));
        result += (long) pq.peek().profit * X * dayNum;
        pq.peek().quantity -= X * dayNum;
        if (pq.peek().quantity == 0) {
          pq.poll();
        }
        d -= dayNum;
      } else {
        int rest = X;
        while (rest != 0 && !pq.isEmpty()) {
          if (pq.peek().quantity <= rest) {
            Element head = pq.poll();
            result += (long) head.profit * head.quantity;
            rest -= head.quantity;
          } else {
            result += (long) pq.peek().profit * rest;
            pq.peek().quantity -= rest;
            rest = 0;
          }
        }
        --d;
      }
    }

    return result;
  }
}

class Element {
  int profit;
  int quantity;

  Element(int profit, int quantity) {
    this.profit = profit;
    this.quantity = quantity;
  }
}