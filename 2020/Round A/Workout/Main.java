import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] M = new int[N];
      for (int i = 0; i < M.length; ++i) {
        M[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(M, K)));
    }

    sc.close();
  }

  static int solve(int[] M, int K) {
    int result = -1;
    int lower = 1;
    int upper = IntStream.range(0, M.length - 1).map(i -> M[i + 1] - M[i]).max().getAsInt();
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(M, K, middle)) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }

  static boolean check(int[] M, int K, int diff) {
    return IntStream.range(0, M.length - 1)
            .map(
                i ->
                    Math.max(
                        0, (M[i + 1] - M[i]) / diff - (((M[i + 1] - M[i]) % diff == 0) ? 1 : 0)))
            .sum()
        <= K;
  }
}
