import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  static final int ALPHABET_SIZE = 26;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();
      String F = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S, F)));
    }

    sc.close();
  }

  static int solve(String S, String F) {
    boolean[] favorites = new boolean[ALPHABET_SIZE];
    for (char c : F.toCharArray()) {
      favorites[c - 'a'] = true;
    }

    return S.chars()
        .map(
            c ->
                IntStream.iterate(0, i -> i + 1)
                    .filter(
                        i ->
                            favorites[(c - 'a' + i) % favorites.length]
                                || favorites[Math.floorMod(c - 'a' - i, favorites.length)])
                    .findFirst()
                    .getAsInt())
        .sum();
  }
}