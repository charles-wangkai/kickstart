import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      int R = sc.nextInt();
      int C = sc.nextInt();
      int Sr = sc.nextInt();
      int Sc = sc.nextInt();
      String instructions = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(instructions, R, C, Sr, Sc)));
    }

    sc.close();
  }

  static String solve(String instructions, int R, int C, int Sr, int Sc) {
    Comparator<Range> rangeComparator = Comparator.comparing(range -> range.min);

    @SuppressWarnings("unchecked")
    NavigableSet<Range>[] rowRangeSets = new NavigableSet[R + 1];
    for (int r = 1; r < rowRangeSets.length; ++r) {
      rowRangeSets[r] = new TreeSet<>(rangeComparator);
    }

    @SuppressWarnings("unchecked")
    NavigableSet<Range>[] colRangeSets = new NavigableSet[C + 1];
    for (int c = 1; c < colRangeSets.length; ++c) {
      colRangeSets[c] = new TreeSet<>(rangeComparator);
    }

    rowRangeSets[Sr].add(new Range(Sc, Sc));
    colRangeSets[Sc].add(new Range(Sr, Sr));

    int r = Sr;
    int c = Sc;
    for (char instruction : instructions.toCharArray()) {
      if (instruction == 'N') {
        r = findLess(colRangeSets[c], r);
      } else if (instruction == 'S') {
        r = findLarger(colRangeSets[c], r);
      } else if (instruction == 'E') {
        c = findLarger(rowRangeSets[r], c);
      } else {
        c = findLess(rowRangeSets[r], c);
      }

      insert(rowRangeSets[r], c);
      insert(colRangeSets[c], r);
    }

    return String.format("%d %d", r, c);
  }

  static int findLess(NavigableSet<Range> ranges, int current) {
    return ranges.floor(new Range(current, -1)).min - 1;
  }

  static int findLarger(NavigableSet<Range> ranges, int current) {
    return ranges.floor(new Range(current, -1)).max + 1;
  }

  static void insert(NavigableSet<Range> ranges, int current) {
    int min = current;
    Range lower = ranges.lower(new Range(current, -1));
    if (lower != null && lower.max == current - 1) {
      min = lower.min;
      ranges.remove(lower);
    }

    int max = current;
    Range higher = ranges.higher(new Range(current, -1));
    if (higher != null && higher.min == current + 1) {
      max = higher.max;
      ranges.remove(higher);
    }

    ranges.add(new Range(min, max));
  }
}

class Range {
  int min;
  int max;

  Range(int min, int max) {
    this.min = min;
    this.max = max;
  }
}
