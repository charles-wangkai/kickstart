import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  static final int ALPHABET_SIZE = 26;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      int Q = sc.nextInt();
      String s = sc.next();
      int[] L = new int[Q];
      int[] R = new int[Q];
      for (int i = 0; i < Q; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(s, L, R)));
    }

    sc.close();
  }

  static int solve(String s, int[] L, int[] R) {
    int[][] prefixSums = new int[s.length() + 1][ALPHABET_SIZE];
    for (int i = 0; i < s.length(); ++i) {
      for (int j = 0; j < ALPHABET_SIZE; ++j) {
        prefixSums[i + 1][j] = prefixSums[i][j] + ((j == s.charAt(i) - 'A') ? 1 : 0);
      }
    }

    return (int)
        IntStream.range(0, L.length)
            .filter(
                i ->
                    IntStream.range(0, ALPHABET_SIZE)
                            .filter(j -> (prefixSums[R[i]][j] - prefixSums[L[i] - 1][j]) % 2 != 0)
                            .count()
                        <= 1)
            .count();
  }
}