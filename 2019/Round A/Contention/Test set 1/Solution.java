import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] L = new int[Q];
      int[] R = new int[Q];
      for (int i = 0; i < Q; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, L, R)));
    }

    sc.close();
  }

  static int solve(int N, int[] L, int[] R) {
    int result = 0;
    int lower = 1;
    int upper = N;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(L, R, middle)) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static boolean check(int[] L, int[] R, int k) {
    List<Integer> rests = IntStream.range(0, L.length).boxed().collect(Collectors.toList());
    while (!rests.isEmpty()) {
      List<Integer> lasts = new ArrayList<>();
      for (int rest : rests) {
        if (computeLastAssignedNum(L, R, rests, rest) >= k) {
          lasts.add(rest);
        }
      }
      if (lasts.isEmpty()) {
        return false;
      }

      rests.removeAll(lasts);
    }

    return true;
  }

  static int computeLastAssignedNum(int[] L, int[] R, List<Integer> rests, int last) {
    List<Range> ranges = List.of(new Range(L[last], R[last]));
    for (int rest : rests) {
      if (rest != last) {
        List<Range> nextRanges = new ArrayList<>();
        for (Range range : ranges) {
          if (range.left > R[rest] || range.right < L[rest]) {
            nextRanges.add(range);
          } else {
            if (range.left < L[rest]) {
              nextRanges.add(new Range(range.left, L[rest] - 1));
            }
            if (range.right > R[rest]) {
              nextRanges.add(new Range(R[rest] + 1, range.right));
            }
          }
        }

        ranges = nextRanges;
      }
    }

    return ranges.stream().mapToInt(range -> range.right - range.left + 1).sum();
  }
}

class Range {
  int left;
  int right;

  Range(int left, int right) {
    this.left = left;
    this.right = right;
  }
}