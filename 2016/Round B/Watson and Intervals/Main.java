import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int L1 = sc.nextInt();
      int R1 = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C1 = sc.nextInt();
      int C2 = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, L1, R1, A, B, C1, C2, M)));
    }

    sc.close();
  }

  static int solve(int N, int L1, int R1, int A, int B, int C1, int C2, int M) {
    Interval[] intervals = new Interval[N];
    int x = L1;
    int y = R1;
    for (int i = 0; i < intervals.length; ++i) {
      intervals[i] = new Interval(Math.min(x, y), Math.max(x, y));

      int nextX = (int) (((long) A * x + (long) B * y + C1) % M);
      int nextY = (int) (((long) A * y + (long) B * x + C2) % M);

      x = nextX;
      y = nextY;
    }

    Element[] elements =
        IntStream.range(0, intervals.length)
            .boxed()
            .flatMap(
                i ->
                    List.of(
                        new Element(i, intervals[i].left, true),
                        new Element(i, intervals[i].right, false))
                        .stream())
            .sorted(
                Comparator.comparing((Element e) -> e.endpoint)
                    .thenComparing(e -> e.beginOrEnd, Comparator.reverseOrder()))
            .toArray(Element[]::new);

    int[] areas = new int[N];
    int prevEndPoint = -1;
    Set<Integer> indices = new HashSet<>();
    for (Element element : elements) {
      if (indices.size() == 1) {
        areas[indices.iterator().next()] +=
            element.endpoint - (element.beginOrEnd ? 1 : 0) - prevEndPoint + 1;
      }

      if (element.beginOrEnd) {
        indices.add(element.index);
      } else {
        indices.remove(element.index);
      }

      prevEndPoint = element.endpoint + (element.beginOrEnd ? 0 : 1);
    }

    return computeCoveredArea(intervals) - Arrays.stream(areas).max().getAsInt();
  }

  static int computeCoveredArea(Interval[] intervals) {
    Arrays.sort(intervals, Comparator.comparing(interval -> interval.left));

    List<Interval> merged = new ArrayList<>();
    for (Interval interval : intervals) {
      if (merged.isEmpty() || merged.get(merged.size() - 1).right < interval.left) {
        merged.add(interval);
      } else {
        merged.set(
            merged.size() - 1,
            new Interval(
                merged.get(merged.size() - 1).left,
                Math.max(merged.get(merged.size() - 1).right, interval.right)));
      }
    }

    return merged.stream().mapToInt(interval -> interval.right - interval.left + 1).sum();
  }
}

class Interval {
  int left;
  int right;

  Interval(int left, int right) {
    this.left = left;
    this.right = right;
  }
}

class Element {
  int index;
  int endpoint;
  boolean beginOrEnd;

  Element(int index, int endpoint, boolean beginOrEnd) {
    this.index = index;
    this.endpoint = endpoint;
    this.beginOrEnd = beginOrEnd;
  }
}
