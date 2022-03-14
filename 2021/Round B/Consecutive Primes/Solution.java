import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long Z = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(Z)));
    }

    sc.close();
  }

  static long solve(long Z) {
    long result = -1;
    int lower = 1;
    int upper = (int) Math.ceil(Math.sqrt(Z));
    while (lower <= upper) {
      int middle = (lower + upper) / 2;

      int p1 = findNextPrime(middle);
      int p2 = findNextPrime(p1 + 1);
      long product = (long) p1 * p2;
      if (product <= Z) {
        result = product;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static int findNextPrime(int x) {
    while (!isPrime(x)) {
      ++x;
    }

    return x;
  }

  static boolean isPrime(int x) {
    if (x <= 1) {
      return false;
    }

    for (int i = 2; i * i <= x; ++i) {
      if (x % i == 0) {
        return false;
      }
    }

    return true;
  }
}