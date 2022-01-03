import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int P = sc.nextInt();
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, P)));
    }

    sc.close();
  }

  static int solve(int[] S, int P) {
    int[] sorted = Arrays.stream(S).boxed().sorted().mapToInt(x -> x).toArray();

    int result = Integer.MAX_VALUE;
    int sum = IntStream.range(0, P - 1).map(i -> sorted[i]).sum();
    for (int i = P - 1; i < sorted.length; ++i) {
      sum += sorted[i];
      result = Math.min(result, sorted[i] * P - sum);

      sum -= sorted[i - P + 1];
    }

    return result;
  }
}
