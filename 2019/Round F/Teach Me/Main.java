import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int S = sc.nextInt();
      int[][] A = new int[N][];
      for (int i = 0; i < A.length; ++i) {
        int C = sc.nextInt();
        A[i] = new int[C];
        for (int j = 0; j < A[i].length; ++j) {
          A[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, S)));
    }

    sc.close();
  }

  static long solve(int[][] A, int S) {
    for (int[] Ai : A) {
      Arrays.sort(Ai);
    }

    Map<String, Integer> keyToCount = new HashMap<>();
    for (int[] Ai : A) {
      String key = buildKey(Ai, (1 << Ai.length) - 1);
      keyToCount.put(key, keyToCount.getOrDefault(key, 0) + 1);
    }

    return Arrays.stream(A)
        .mapToInt(
            Ai ->
                A.length
                    - IntStream.range(0, 1 << Ai.length)
                        .map(mask -> keyToCount.getOrDefault(buildKey(Ai, mask), 0))
                        .sum())
        .asLongStream()
        .sum();
  }

  static String buildKey(int[] skills, int mask) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < skills.length; ++i) {
      if (((mask >> i) & 1) == 1) {
        if (result.length() != 0) {
          result.append(",");
        }
        result.append(skills[i]);
      }
    }

    return result.toString();
  }
}
