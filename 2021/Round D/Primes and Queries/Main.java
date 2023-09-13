// https://codingcompetitions.withgoogle.com/kickstart/round/00000000004361e3/000000000082bcf4#analysis
// https://en.wikipedia.org/wiki/P-adic_order
// https://en.wikipedia.org/wiki/Fenwick_tree

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int P = sc.nextInt();
      long[] A = new long[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextLong();
      }
      String[] queries = new String[Q];
      sc.nextLine();
      for (int i = 0; i < queries.length; ++i) {
        queries[i] = sc.nextLine();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, queries, P)));
    }

    sc.close();
  }

  static String solve(long[] A, String[] queries, int P) {
    int size = Integer.highestOneBit(A.length) * 2 + 1;
    int[] divisibles = new int[size];
    int[] nonDivisibleCommons = new int[size];
    int[] nonDivisibleSpecials = new int[size];
    int[] nonDivisibleCounts = new int[size];
    for (int i = 0; i < A.length; ++i) {
      set(P, divisibles, nonDivisibleCommons, nonDivisibleSpecials, nonDivisibleCounts, i, A[i], 1);
    }

    List<Long> result = new ArrayList<>();
    for (int i = 0; i < queries.length; ++i) {
      String[] parts = queries[i].split(" ");
      if (parts[0].equals("1")) {
        int pos = Integer.parseInt(parts[1]);
        long val = Long.parseLong(parts[2]);

        set(
            P,
            divisibles,
            nonDivisibleCommons,
            nonDivisibleSpecials,
            nonDivisibleCounts,
            pos - 1,
            A[pos - 1],
            -1);
        set(
            P,
            divisibles,
            nonDivisibleCommons,
            nonDivisibleSpecials,
            nonDivisibleCounts,
            pos - 1,
            val,
            1);
        A[pos - 1] = val;
      } else {
        int S = Integer.parseInt(parts[1]);
        int L = Integer.parseInt(parts[2]);
        int R = Integer.parseInt(parts[3]);

        result.add(
            (long) S * range_sum(divisibles, L - 1, R)
                + range_sum(nonDivisibleCommons, L - 1, R)
                + ((P == 2 && S % 2 == 0) ? range_sum(nonDivisibleSpecials, L - 1, R) : 0)
                + V(P, S) * range_sum(nonDivisibleCounts, L - 1, R));
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining(" "));
  }

  static void set(
      int P,
      int[] divisibles,
      int[] nonDivisibleCommons,
      int[] nonDivisibleSpecials,
      int[] nonDivisibleCounts,
      int index,
      long value,
      int deltaSign) {
    if (value < P) {
      return;
    }

    if (value % P == 0) {
      add(divisibles, index + 1, deltaSign * V(P, value));
    } else {
      add(nonDivisibleCommons, index + 1, deltaSign * V(P, value - value % P));
      add(nonDivisibleSpecials, index + 1, deltaSign * (V(P, value + value % P) - 1));
      add(nonDivisibleCounts, index + 1, deltaSign);
    }
  }

  static int V(int P, long x) {
    int result = 0;
    while (x % P == 0) {
      x /= P;
      ++result;
    }

    return result;
  }

  static int LSB(int i) {
    return i & -i;
  }

  static void add(int[] a, int i, int delta) {
    if (i == 0) {
      a[0] += delta;
      return;
    }
    for (; i < a.length; i += LSB(i)) a[i] += delta;
  }

  static int range_sum(int[] a, int i, int j) {
    int sum = 0;
    for (; j > i; j -= LSB(j)) sum += a[j];
    for (; i > j; i -= LSB(i)) sum -= a[i];
    return sum;
  }
}
