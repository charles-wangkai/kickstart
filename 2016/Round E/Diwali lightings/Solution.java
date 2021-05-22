import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();
      long I = sc.nextLong() - 1;
      long J = sc.nextLong() - 1;

      System.out.println(String.format("Case #%d: %d", tc, solve(S, I, J)));
    }

    sc.close();
  }

  static long solve(String S, long I, long J) {
    long beginGroup = I / S.length();
    int beginIndex = (int) (I % S.length());
    long endGroup = J / S.length();
    int endIndex = (int) (J % S.length());

    if (beginGroup == endGroup) {
      return IntStream.rangeClosed(beginIndex, endIndex).filter(i -> S.charAt(i) == 'B').count();
    }

    return (endGroup - beginGroup - 1) * S.chars().filter(ch -> ch == 'B').count()
        + IntStream.range(beginIndex, S.length()).filter(i -> S.charAt(i) == 'B').count()
        + IntStream.rangeClosed(0, endIndex).filter(i -> S.charAt(i) == 'B').count();
  }
}
