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
      int L = sc.nextInt();
      String[] words = new String[L];
      for (int i = 0; i < words.length; ++i) {
        words[i] = sc.next();
      }
      char S1 = sc.next().charAt(0);
      char S2 = sc.next().charAt(0);
      int N = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();
      int D = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(words, S1, S2, N, A, B, C, D)));
    }

    sc.close();
  }

  static int solve(String[] words, char S1, char S2, int N, int A, int B, int C, int D) {
    char[] S = generate(S1, S2, N, A, B, C, D);

    Map<Integer, List<String>> lengthToWords = new HashMap<>();
    for (String word : words) {
      int length = word.length();
      if (!lengthToWords.containsKey(length)) {
        lengthToWords.put(length, new ArrayList<>());
      }
      lengthToWords.get(length).add(word);
    }

    int result = 0;
    for (List<String> ws : lengthToWords.values()) {
      result += computeAppearedNum(S, ws);
    }

    return result;
  }

  static char[] generate(char S1, char S2, int N, int A, int B, int C, int D) {
    int[] x = new int[N];
    x[0] = S1;
    x[1] = S2;
    for (int i = 2; i < x.length; ++i) {
      x[i] = (int) (((long) A * x[i - 1] + (long) B * x[i - 2] + C) % D);
    }

    char[] result = new char[N];
    result[0] = S1;
    result[1] = S2;
    for (int i = 2; i < result.length; ++i) {
      result[i] = (char) ('a' + x[i] % 26);
    }

    return result;
  }

  static int computeAppearedNum(char[] S, List<String> words) {
    int length = words.iterator().next().length();

    Map<String, Integer> codeToCount = new HashMap<>();
    for (String word : words) {
      String code = buildWordCode(word);
      codeToCount.put(code, codeToCount.getOrDefault(code, 0) + 1);
    }

    int result = 0;
    int[] counts = new int[26];
    for (int i = 1; i < length - 1; ++i) {
      ++counts[S[i] - 'a'];
    }
    for (int i = length - 1; i < S.length; ++i) {
      char begin = S[i - length + 1];
      char end = S[i];

      String code = buildCode(begin, end, counts);
      if (codeToCount.containsKey(code)) {
        result += codeToCount.remove(code);

        if (codeToCount.isEmpty()) {
          break;
        }
      }

      --counts[S[i - length + 2] - 'a'];
      ++counts[end - 'a'];
    }

    return result;
  }

  static String buildWordCode(String word) {
    int[] counts = new int[26];
    for (int i = 1; i < word.length() - 1; ++i) {
      ++counts[word.charAt(i) - 'a'];
    }

    return buildCode(word.charAt(0), word.charAt(word.length() - 1), counts);
  }

  static String buildCode(char begin, char end, int[] counts) {
    StringBuilder result = new StringBuilder().append(begin).append(end);
    for (int i = 0; i < counts.length; ++i) {
      if (counts[i] != 0) {
        result.append((char) ('a' + i)).append(counts[i]);
      }
    }

    return result.toString();
  }
}
