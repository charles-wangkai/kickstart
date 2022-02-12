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
    long[] progressPrefixs = buildProgressPrefixs(A);
    int[] alternatePrefixs = buildAlternatePrefixs(A);

    long result = 0;
    for (String operation : operations) {
      String[] parts = operation.split(" ");
      if (parts[0].equals("U")) {
        int X = Integer.parseInt(parts[1]) - 1;
        int V = Integer.parseInt(parts[2]);

        A[X] = V;
        progressPrefixs = buildProgressPrefixs(A);
        alternatePrefixs = buildAlternatePrefixs(A);
      } else {
        int L = Integer.parseInt(parts[1]) - 1;
        int R = Integer.parseInt(parts[2]) - 1;

        long progressRange = progressPrefixs[R] - ((L == 0) ? 0 : progressPrefixs[L - 1]);
        int alternateRange = alternatePrefixs[R] - ((L == 0) ? 0 : alternatePrefixs[L - 1]);

        long score;
        if (L % 2 == 0) {
          score = progressRange - (long) L * alternateRange;
        } else {
          score = -progressRange + (long) L * alternateRange;
        }
        result += score;
      }
    }

    return result;
  }

  static long[] buildProgressPrefixs(int[] A) {
    long[] result = new long[A.length];
    for (int i = 0; i < result.length; ++i) {
      result[i] = ((i == 0) ? 0 : result[i - 1]) + ((i % 2 == 0) ? 1 : -1) * (i + 1) * A[i];
    }

    return result;
  }

  static int[] buildAlternatePrefixs(int[] A) {
    int[] result = new int[A.length];
    for (int i = 0; i < result.length; ++i) {
      result[i] = ((i == 0) ? 0 : result[i - 1]) + ((i % 2 == 0) ? 1 : -1) * A[i];
    }

    return result;
  }
}