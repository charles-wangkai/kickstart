import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int D = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, D)));
    }

    sc.close();
  }

  static long solve(int N, int D) {
    long result = 0;
    for (int product = 1; product <= N; ++product) {
      for (int i = 1; i * i <= product; ++i) {
        if (product % i == 0) {
          int other = product / i;
          if (other % D == 0) {
            result += computeTripleNum(i, N - product);
          }
          if (other != i && i % D == 0) {
            result += computeTripleNum(other, N - product);
          }
        }
      }
    }

    return result;
  }

  static int computeTripleNum(int abcSum, int bccSum) {
    int minB = 0;
    int maxB = bccSum;

    if (2 * abcSum - bccSum <= 1) {
      return 0;
    }

    maxB = Math.min(maxB, 2 * abcSum - bccSum - 2);

    boolean bEven = (2 * abcSum - bccSum) % 2 == 0;
    if (bEven) {
      maxB = maxB / 2 * 2;
    } else {
      minB = 1;
      maxB = maxB - ((maxB % 2 == 0) ? 1 : 0);
    }

    return (maxB - minB) / 2 + 1;
  }
}
