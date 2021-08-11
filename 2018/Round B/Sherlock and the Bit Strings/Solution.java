// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050ff4/000000000005107b#analysis

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  static final int PREFIX_DIGIT_NUM = 16;
  static final long P_LIMIT = 1_000_000_000_000_000_000L;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      long P = sc.nextLong();
      int[] A = new int[K];
      int[] B = new int[K];
      int[] C = new int[K];
      for (int i = 0; i < K; ++i) {
        A[i] = sc.nextInt() - 1;
        B[i] = sc.nextInt() - 1;
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, A, B, C, P)));
    }

    sc.close();
  }

  static String solve(int N, int[] A, int[] B, int[] C, long P) {
    @SuppressWarnings("unchecked")
    List<Integer>[] constraintLists = new List[N];
    for (int i = 0; i < constraintLists.length; ++i) {
      constraintLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < B.length; ++i) {
      constraintLists[B[i]].add(i);
    }

    long[][] wayNums = new long[N + 1][1 << PREFIX_DIGIT_NUM];
    for (int i = N; i >= 1; --i) {
      for (int last = 0; last < (1 << PREFIX_DIGIT_NUM); ++last) {
        if (check(A, B, C, constraintLists[i - 1], last)) {
          wayNums[i][last] =
              addInLimit(
                  wayNums[i][last],
                  (i == N)
                      ? 1
                      : addInLimit(
                          wayNums[i + 1][buildNextLast(last, 0)],
                          wayNums[i + 1][buildNextLast(last, 1)]));
        }
      }
    }

    StringBuilder result = new StringBuilder();
    int last = 0;
    for (int i = 1; i <= N; ++i) {
      int nextLastZero = buildNextLast(last, 0);

      if (wayNums[i][nextLastZero] >= P) {
        result.append(0);
        last = nextLastZero;
      } else {
        result.append(1);
        P -= wayNums[i][nextLastZero];
        last = nextLastZero + 1;
      }
    }

    return result.toString();
  }

  static long addInLimit(long x, long y) {
    return Math.min(P_LIMIT, x + y);
  }

  static int buildNextLast(int last, int digit) {
    return ((last % (1 << (PREFIX_DIGIT_NUM - 1))) << 1) + digit;
  }

  static boolean check(int[] A, int[] B, int[] C, List<Integer> constraints, int last) {
    for (int constraint : constraints) {
      if (Integer.bitCount(last % (1 << (B[constraint] - A[constraint] + 1))) != C[constraint]) {
        return false;
      }
    }

    return true;
  }
}
