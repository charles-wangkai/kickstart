import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, M)));
    }

    sc.close();
  }

  static int solve(int[] A, int M) {
    return IntStream.range(0, 1 << 7)
        .filter(k -> Arrays.stream(A).map(x -> x ^ k).sum() <= M)
        .max()
        .orElse(-1);
  }
}