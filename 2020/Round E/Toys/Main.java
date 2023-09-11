import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] E = new int[N];
      int[] R = new int[N];
      for (int i = 0; i < N; ++i) {
        E[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(E, R)));
    }

    sc.close();
  }

  static String solve(int[] E, int[] R) {
    int N = E.length;

    long sum = Arrays.stream(E).asLongStream().sum();
    PriorityQueue<Element> pq =
        new PriorityQueue<>(Comparator.comparing((Element e) -> e.lower).reversed());

    long maxTime = sum;
    int bestRemoveNum = 0;
    long prefixSum = 0;
    int removeNum = 0;
    for (int i = 0; i < N; ++i) {
      if (sum >= R[i] + E[i]) {
        prefixSum += E[i];
        if (sum + prefixSum > maxTime) {
          maxTime = sum + prefixSum;
          bestRemoveNum = removeNum;
        }

        pq.offer(new Element(i, R[i] + E[i]));
      } else {
        sum -= E[i];
        ++removeNum;

        while (!pq.isEmpty() && pq.peek().lower > sum) {
          Element head = pq.poll();
          ++removeNum;
          sum -= E[head.index];
          prefixSum -= E[head.index];
        }
      }
    }

    if (sum != 0) {
      maxTime = Long.MAX_VALUE;
      bestRemoveNum = removeNum;
    }

    return String.format(
        "%s %d",
        (maxTime == Long.MAX_VALUE) ? "INDEFINITELY" : String.valueOf(maxTime), bestRemoveNum);
  }
}

class Element {
  int index;
  int lower;

  Element(int index, int lower) {
    this.index = index;
    this.lower = lower;
  }
}
