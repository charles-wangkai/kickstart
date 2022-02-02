import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int H = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, H)));
    }

    sc.close();
  }

  static int solve(int[] A, int[] B, int H) {
    int N = A.length;

    int result = 0;
    for (int mask = pow(3, N) - 1; mask >= 0; --mask) {
      int[] states = decode(N, mask);
      long sumA = 0;
      long sumB = 0;
      for (int i = 0; i < states.length; ++i) {
        if (states[i] == 0) {
          sumA += A[i];
        } else if (states[i] == 1) {
          sumB += B[i];
        } else {
          sumA += A[i];
          sumB += B[i];
        }
      }

      if (sumA >= H && sumB >= H) {
        ++result;
      }
    }

    return result;
  }

  static int pow(int base, int exponent) {
    return IntStream.range(0, exponent).reduce(1, (x, y) -> x * base);
  }

  static int[] decode(int N, int mask) {
    int[] result = new int[N];
    for (int i = 0; i < result.length; ++i) {
      result[i] = mask % 3;
      mask /= 3;
    }

    return result;
  }
}