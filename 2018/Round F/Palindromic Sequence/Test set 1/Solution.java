// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e07/0000000000051186#analysis

import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();
      int N = sc.nextInt();
      long K = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(L, N, K)));
    }

    sc.close();
  }

  static int solve(int L, int N, long K) {
    String prefix = "";
    while (K != 0) {
      for (int i = 0; ; ++i) {
        if (i == L) {
          return 0;
        }

        String nextPrefix = prefix + (char) (i + 'a');
        long wordNum = computeWordNum(L, N, nextPrefix);
        if (wordNum >= K) {
          prefix = nextPrefix;

          break;
        }

        K -= wordNum;
      }

      if (isPalindromePrefix(prefix, prefix.length())) {
        --K;
      }
    }

    return prefix.length();
  }

  static boolean isPalindromePrefix(String prefix, int length) {
    for (int i = 0, j = length - 1; i < j; ++i, --j) {
      if (i < prefix.length() && j < prefix.length() && prefix.charAt(i) != prefix.charAt(j)) {
        return false;
      }
    }

    return true;
  }

  static long computeWordNum(int L, int N, String prefix) {
    long result = 0;
    for (int length = prefix.length(); length <= N; ++length) {
      if (isPalindromePrefix(prefix, length)) {
        long delta = pow(L, computeFreePosNum(prefix.length(), length));
        if (delta == Long.MAX_VALUE || result > Long.MAX_VALUE - delta) {
          return Long.MAX_VALUE;
        }

        result += delta;
      }
    }

    return result;
  }

  static int computeFreePosNum(int prefixLength, int length) {
    int result = 0;
    for (int i = 0, j = length - 1; i <= j; ++i, --j) {
      if (i >= prefixLength) {
        ++result;
      }
    }

    return result;
  }

  static long pow(int base, int exponent) {
    long result = 1;
    for (int i = 0; i < exponent; ++i) {
      if (result > Long.MAX_VALUE / base) {
        return Long.MAX_VALUE;
      }

      result *= base;
    }

    return result;
  }
}