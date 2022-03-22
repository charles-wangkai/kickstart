// https://en.wikipedia.org/wiki/Fenwick_tree

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  static final int MAX_S = 4;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int P = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
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

  static String solve(int[] A, String[] queries, int P) {
    int[][] B = new int[MAX_S + 1][Integer.highestOneBit(A.length) * 2 + 1];
    for (int i = 0; i < A.length; ++i) {
      for (int s = 1; s <= MAX_S; ++s) {
        add(B[s], i + 1, computeItem(A[i], s, P));
      }
    }

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < queries.length; ++i) {
      int[] parts = Arrays.stream(queries[i].split(" ")).mapToInt(Integer::parseInt).toArray();
      if (parts[0] == 1) {
        int pos = parts[1];
        int val = parts[2];

        for (int s = 1; s <= MAX_S; ++s) {
          add(B[s], pos, computeItem(val, s, P) - computeItem(A[pos - 1], s, P));
        }
        A[pos - 1] = val;
      } else {
        int S = parts[1];
        int L = parts[2];
        int R = parts[3];

        result.add(range_sum(B[S], L - 1, R));
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining(" "));
  }

  static int computeItem(int a, int s, int P) {
    int result = 0;
    long rest = pow(a, s) - pow(a % P, s);
    while (rest != 0 && rest % P == 0) {
      rest /= P;
      ++result;
    }

    return result;
  }

  static long pow(int base, int exponent) {
    return IntStream.range(0, exponent).asLongStream().reduce(1, (x, y) -> x * base);
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