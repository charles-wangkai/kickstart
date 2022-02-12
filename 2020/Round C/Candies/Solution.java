// https://en.wikipedia.org/wiki/Fenwick_tree

import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      sc.nextLine();
      String[] operations = new String[Q];
      for (int i = 0; i < operations.length; ++i) {
        operations[i] = sc.nextLine();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, operations)));
    }

    sc.close();
  }

  static long solve(int[] A, String[] operations) {
    long[] progress = new long[1 + Integer.highestOneBit(A.length) * 2];
    long[] alternate = new long[progress.length];
    for (int i = 0; i < A.length; ++i) {
      add(progress, i, ((i % 2 == 0) ? 1 : -1) * (i + 1) * A[i]);
      add(alternate, i, ((i % 2 == 0) ? 1 : -1) * A[i]);
    }

    long result = 0;
    for (String operation : operations) {
      String[] parts = operation.split(" ");
      if (parts[0].equals("U")) {
        int X = Integer.parseInt(parts[1]) - 1;
        int V = Integer.parseInt(parts[2]);

        add(progress, X, ((X % 2 == 0) ? 1 : -1) * (X + 1) * (V - A[X]));
        add(alternate, X, ((X % 2 == 0) ? 1 : -1) * (V - A[X]));
        A[X] = V;
      } else {
        int L = Integer.parseInt(parts[1]) - 1;
        int R = Integer.parseInt(parts[2]) - 1;

        long progressRange = prefixSum(progress, R) - ((L == 0) ? 0 : prefixSum(progress, L - 1));
        long alternateRange =
            prefixSum(alternate, R) - ((L == 0) ? 0 : prefixSum(alternate, L - 1));

        long score;
        if (L % 2 == 0) {
          score = progressRange - L * alternateRange;
        } else {
          score = -progressRange + L * alternateRange;
        }
        result += score;
      }
    }

    return result;
  }

  static int LSB(int i) {
    return i & -i;
  }

  static void add(long[] a, int i, int delta) {
    if (i == 0) {
      a[0] += delta;
      return;
    }
    for (; i < a.length; i += LSB(i)) a[i] += delta;
  }

  static long prefixSum(long[] a, int i) {
    long sum = a[0];
    for (; i != 0; i -= LSB(i)) sum += a[i];
    return sum;
  }
}