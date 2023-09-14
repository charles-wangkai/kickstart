import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
  static final int K_LIMIT = 100000;

  static int[] x1 = new int[K_LIMIT];
  static int[] y1 = new int[K_LIMIT];
  static int[] x2 = new int[K_LIMIT];
  static int[] y2 = new int[K_LIMIT];

  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int T = Integer.parseInt(st.nextToken());
    for (int tc = 1; tc <= T; ++tc) {
      st = new StringTokenizer(br.readLine());
      int K = Integer.parseInt(st.nextToken());
      for (int i = 0; i < K; ++i) {
        st = new StringTokenizer(br.readLine());
        x1[i] = Integer.parseInt(st.nextToken());
        y1[i] = Integer.parseInt(st.nextToken());
        x2[i] = Integer.parseInt(st.nextToken());
        y2[i] = Integer.parseInt(st.nextToken());
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(K, x1, y1, x2, y2)));

      System.gc();
    }
  }

  static String solve(int K, int[] x1, int[] y1, int[] x2, int[] y2) {
    return String.format("%d %d", findCoordinate(K, x1, x2), findCoordinate(K, y1, y2));
  }

  static int findCoordinate(int K, int[] lowers, int[] uppers) {
    int[] coordinates =
        IntStream.concat(
                IntStream.range(0, K).map(i -> lowers[i]),
                IntStream.range(0, K).map(i -> uppers[i]))
            .boxed()
            .sorted()
            .distinct()
            .mapToInt(Integer::intValue)
            .toArray();

    PriorityQueue<Integer> rights = new PriorityQueue<>(Comparator.comparing(i -> lowers[i]));
    for (int i = 0; i < K; ++i) {
      rights.offer(i);
    }
    long rightSum = IntStream.range(0, K).map(i -> lowers[i]).asLongStream().sum();
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
