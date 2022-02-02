import java.util.Scanner;

public class Solution {
  static final int BIT_NUM = 50;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      long M = sc.nextLong();
      long[] A = new long[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextLong();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, M)));
    }

    sc.close();
  }

  static long solve(long[] A, long M) {
    int[][] counts = new int[BIT_NUM][2];
    for (long Ai : A) {
      for (int i = 0; i < counts.length; ++i) {
        ++counts[i][((Ai & (1L << i)) == 0) ? 0 : 1];
      }
    }

    long[] minSums = new long[counts.length];
    for (int i = 0; i < minSums.length; ++i) {
      minSums[i] =
          ((i == 0) ? 0 : minSums[i - 1]) + (1L << i) * Math.min(counts[i][0], counts[i][1]);
    }

    if (minSums[minSums.length - 1] > M) {
      return -1;
    }

    long result = 0;
    long rest = M;
    for (int i = BIT_NUM - 1; i >= 0; --i) {
      for (int j = 1; ; --j) {
        if (((i == 0) ? 0 : minSums[i - 1]) + (1L << i) * counts[i][1 - j] <= rest) {
          rest -= (1L << i) * counts[i][1 - j];
          result += (1L << i) * j;

          break;
        }
      }
    }

    return result;
  }
}