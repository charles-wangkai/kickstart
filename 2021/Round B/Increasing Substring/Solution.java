import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(S)));
    }

    sc.close();
  }

  static String solve(String S) {
    return IntStream.range(0, S.length())
        .map(
            i -> {
              int beginIndex = i;
              while (beginIndex != 0 && S.charAt(beginIndex - 1) < S.charAt(beginIndex)) {
                --beginIndex;
              }

              return i - beginIndex + 1;
            })
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }
}