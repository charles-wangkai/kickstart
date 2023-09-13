import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int T = Integer.parseInt(st.nextToken());
    for (int tc = 1; tc <= T; ++tc) {
      st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      long C = Long.parseLong(st.nextToken());
      long[] L = new long[N];
      long[] R = new long[N];
      for (int i = 0; i < N; ++i) {
        st = new StringTokenizer(br.readLine());
        L[i] = Long.parseLong(st.nextToken());
        R[i] = Long.parseLong(st.nextToken());
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R, C)));
    }
  }

  static long solve(long[] L, long[] R, long C) {
    SortedMap<Long, Integer> pointToDelta = new TreeMap<>();
    for (int i = 0; i < L.length; ++i) {
      pointToDelta.put(L[i] + 1, pointToDelta.getOrDefault(L[i] + 1, 0) + 1);
      pointToDelta.put(R[i], pointToDelta.getOrDefault(R[i], 0) - 1);
    }

    List<Range> ranges = new ArrayList<>();
    long left = -1;
    int count = 0;
    for (long point : pointToDelta.keySet()) {
      if (count != 0) {
        ranges.add(new Range(left, point - 1, count));
      }

      left = point;
      count += pointToDelta.get(point);
    }
    Collections.sort(ranges, Comparator.comparing((Range range) -> range.count).reversed());

    long result = L.length;
    long rest = C;
    for (Range range : ranges) {
      long cutNum = Math.min(rest, range.right - range.left + 1);
      result += cutNum * range.count;
      rest -= cutNum;
    }

    return result;
  }
}

class Range {
  long left;
  long right;
  int count;

  Range(long left, long right, int count) {
    this.left = left;
    this.right = right;
    this.count = count;
  }
}
