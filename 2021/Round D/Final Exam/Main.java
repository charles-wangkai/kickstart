import java.util.Arrays;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      long[] A = new long[N];
      long[] B = new long[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextLong();
        B[i] = sc.nextLong();
      }
      long[] S = new long[M];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextLong();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, B, S)));
    }

    sc.close();
  }

  static String solve(long[] A, long[] B, long[] S) {
    NavigableSet<Range> ranges = new TreeSet<>(Comparator.comparing(range -> range.left));
    for (int i = 0; i < A.length; ++i) {
      ranges.add(new Range(A[i], B[i]));
    }

    long[] result = new long[S.length];
    for (int i = 0; i < result.length; ++i) {
      Range floor = ranges.floor(new Range(S[i], -1));
      Range ceiling = ranges.ceiling(new Range(S[i], -1));
      if (floor != null && floor.right >= S[i]) {
        result[i] = S[i];

        ranges.remove(floor);
        if (floor.left != S[i]) {
          ranges.add(new Range(floor.left, S[i] - 1));
        }
        if (floor.right != S[i]) {
          ranges.add(new Range(S[i] + 1, floor.right));
        }
      } else if (ceiling == null || (floor != null && S[i] - floor.right <= ceiling.left - S[i])) {
        result[i] = floor.right;

        ranges.remove(floor);
        if (floor.left != floor.right) {
          ranges.add(new Range(floor.left, floor.right - 1));
        }
      } else {
        result[i] = ceiling.left;

        ranges.remove(ceiling);
        if (ceiling.left != ceiling.right) {
          ranges.add(new Range(ceiling.left + 1, ceiling.right));
        }
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}

class Range {
  long left;
  long right;

  Range(long left, long right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, right);
  }

  @Override
  public boolean equals(Object obj) {
    Range other = (Range) obj;

    return left == other.left && right == other.right;
  }
}
