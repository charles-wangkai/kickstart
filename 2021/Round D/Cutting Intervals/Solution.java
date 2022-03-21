import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      long C = sc.nextLong();
      long[] L = new long[N];
      long[] R = new long[N];
      for (int i = 0; i < N; ++i) {
        L[i] = sc.nextLong();
        R[i] = sc.nextLong();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R, C)));
    }

    sc.close();
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