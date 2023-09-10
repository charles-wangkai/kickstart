import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int Q = sc.nextInt();
      int[] P = new int[M];
      for (int i = 0; i < P.length; ++i) {
        P[i] = sc.nextInt();
      }
      int[] R = new int[Q];
      for (int i = 0; i < R.length; ++i) {
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, P, R)));
    }

    sc.close();
  }

  static long solve(int N, int[] P, int[] R) {
    Map<Integer, Integer> divisorToCount = new HashMap<>();
    for (int Pi : P) {
      for (int i = 1; i * i <= Pi; ++i) {
        if (Pi % i == 0) {
          divisorToCount.put(i, divisorToCount.getOrDefault(i, 0) + 1);
          if (Pi / i != i) {
            divisorToCount.put(Pi / i, divisorToCount.getOrDefault(Pi / i, 0) + 1);
          }
        }
      }
    }

    return Arrays.stream(R)
        .map(r -> N / r - divisorToCount.getOrDefault(r, 0))
        .asLongStream()
        .sum();
  }
}
