// https://zibada.guru/gcj/ks2019a/solutions/wifi.0.cpp

import java.util.Arrays;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class Main {
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
    Range[] ranges =
        IntStream.range(0, L.length)
            .mapToObj(i -> new Range(L[i] - 1, R[i]))
            .sorted(
                Comparator.<Range, Integer>comparing(range -> range.left)
                    .thenComparing(range -> range.right))
            .toArray(Range[]::new);

    int result = 0;
    int lower = 1;
    int upper = N;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(ranges, middle)) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static boolean check(Range[] ranges, int k) {
    int[] assigned = new int[ranges.length];
    NavigableSet<Element> rests =
        new TreeSet<>(
            Comparator.<Element, Integer>comparing(element -> element.right)
                .thenComparing(element -> element.index));
    int last = 0;
    for (int i = 0; i < ranges.length; ++i) {
      while (!rests.isEmpty()) {
        Element rest = rests.first();
        if (rest.right > ranges[i].left) {
          break;
        }

        assigned[rest.index] += rest.right - last;
        last = rest.right;
        rests.remove(rest);
      }

      if (last < ranges[i].left) {
        if (!rests.isEmpty()) {
          assigned[rests.first().index] += ranges[i].left - last;
        }
        last = ranges[i].left;
      }

      Element current = new Element(i, ranges[i].right);
      rests.add(current);

      while (true) {
        Element rest = rests.lower(current);
        if (rest == null || assigned[rest.index] < k) {
          break;
        }

        rests.remove(rest);
      }
    }

    for (Element rest : rests) {
      assigned[rest.index] += rest.right - last;
      last = rest.right;
    }

    return Arrays.stream(assigned).allMatch(x -> x >= k);
  }
}

class Element {
  int index;
  int right;

  Element(int index, int right) {
    this.index = index;
    this.right = right;
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
