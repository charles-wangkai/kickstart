import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      String[] S = new String[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, K)));
    }

    sc.close();
  }

  static int solve(String[] S, int K) {
    Map<Integer, List<String>> lengthToStrings = new HashMap<>();
    for (String string : S) {
      lengthToStrings.putIfAbsent(string.length(), new ArrayList<>());
      lengthToStrings.get(string.length()).add(string);
    }

    int result = 0;
    int maxLength = lengthToStrings.keySet().stream().mapToInt(x -> x).max().getAsInt();
    Map<String, Integer> prefixToCount = new HashMap<>();
    for (int i = maxLength; i >= 1; --i) {
      for (String string : lengthToStrings.getOrDefault(i, List.of())) {
        prefixToCount.put(string, prefixToCount.getOrDefault(string, 0) + 1);
      }

      Map<String, Integer> nextPrefixToCount = new HashMap<>();
      for (String prefix : prefixToCount.keySet()) {
        int count = prefixToCount.get(prefix);

        result += count / K * prefix.length();
        String nextPrefix = prefix.substring(0, prefix.length() - 1);
        if (count % K != 0) {
          nextPrefixToCount.put(
              nextPrefix, nextPrefixToCount.getOrDefault(nextPrefix, 0) + count % K);
        }
      }

      prefixToCount = nextPrefixToCount;
    }

    return result;
  }
}