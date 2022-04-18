import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int A = sc.nextInt();
      int B = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B)));
    }

    sc.close();
  }

  static int solve(int A, int B) {
    return (int) IntStream.rangeClosed(A, B).filter(Solution::isInteresting).count();
  }

  static boolean isInteresting(int x) {
    int[] digits = String.valueOf(x).chars().map(c -> c - '0').toArray();

    return Arrays.stream(digits).reduce(1, (p, q) -> p * q) % Arrays.stream(digits).sum() == 0;
  }
}