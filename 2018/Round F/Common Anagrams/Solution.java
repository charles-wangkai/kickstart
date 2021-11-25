import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String A = sc.next();
      String B = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B)));
    }

    sc.close();
  }

  static int solve(String A, String B) {
    Set<String> bKeys = new HashSet<>();
    for (int i = 0; i < B.length(); ++i) {
      for (int j = i; j < B.length(); ++j) {
        bKeys.add(buildKey(B.substring(i, j + 1)));
      }
    }

    int result = 0;
    for (int i = 0; i < A.length(); ++i) {
      for (int j = i; j < A.length(); ++j) {
        if (bKeys.contains(buildKey(A.substring(i, j + 1)))) {
          ++result;
        }
      }
    }

    return result;
  }

  static String buildKey(String s) {
    return s.chars()
        .sorted()
        .mapToObj(ch -> String.valueOf((char) ch))
        .collect(Collectors.joining());
  }
}