import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long K = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(K)));
    }

    sc.close();
  }

  static int solve(long K) {
    long length = 0;
    while (length < K) {
      length = length * 2 + 1;
    }

    return search(length, K - 1);
  }

  static int search(long length, long index) {
    if (index == 0) {
      return 0;
    }

    long half = length / 2;
    if (index == half) {
      return 0;
    } else if (index < half) {
      return search(half, index);
    }

    return 1 - search(half, length - 1 - index);
  }
}
