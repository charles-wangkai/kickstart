import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String P = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(P)));
    }

    sc.close();
  }

  static int solve(String P) {
    int N = P.length();

    boolean[] reds = new boolean[N];
    boolean[] yellows = new boolean[N];
    boolean[] blues = new boolean[N];
    for (int i = 0; i < N; ++i) {
      char c = P.charAt(i);
      if (c == 'R') {
        reds[i] = true;
      } else if (c == 'Y') {
        yellows[i] = true;
      } else if (c == 'B') {
        blues[i] = true;
      } else if (c == 'O') {
        reds[i] = true;
        yellows[i] = true;
      } else if (c == 'P') {
        reds[i] = true;
        blues[i] = true;
      } else if (c == 'G') {
        yellows[i] = true;
        blues[i] = true;
      } else if (c == 'A') {
        reds[i] = true;
        yellows[i] = true;
        blues[i] = true;
      }
    }

    return computeStrokeNum(reds) + computeStrokeNum(yellows) + computeStrokeNum(blues);
  }

  static int computeStrokeNum(boolean[] colors) {
    return (int)
        IntStream.range(0, colors.length)
            .filter(i -> colors[i] && (i == 0 || !colors[i - 1]))
            .count();
  }
}