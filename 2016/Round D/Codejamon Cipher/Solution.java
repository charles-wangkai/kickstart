import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int V = sc.nextInt();
      int S = sc.nextInt();
      String[] words = new String[V];
      for (int i = 0; i < words.length; ++i) {
        words[i] = sc.next();
      }
      String[] sentences = new String[S];
      for (int i = 0; i < sentences.length; ++i) {
        sentences[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(words, sentences)));
    }

    sc.close();
  }

  static String solve(String[] words, String[] sentences) {
    String[] result = new String[sentences.length];
    for (int i = 0; i < sentences.length; ++i) {
      result[i] = String.valueOf(computeOriginalNum(words, sentences[i]));
    }

    return String.join(" ", result);
  }

  static int computeOriginalNum(String[] words, String sentence) {
    int[] originalNums = new int[sentence.length() + 1];
    originalNums[0] = 1;
    for (int i = 1; i < originalNums.length; ++i) {
      for (String word : words) {
        if (word.length() <= i && isSame(sentence.substring(i - word.length(), i), word)) {
          originalNums[i] = addMod(originalNums[i], originalNums[i - word.length()]);
        }
      }
    }

    return originalNums[sentence.length()];
  }

  static boolean isSame(String s1, String s2) {
    int[] counts1 = buildCounts(s1);
    int[] counts2 = buildCounts(s2);
    for (int i = 0; i < counts1.length; ++i) {
      if (counts1[i] != counts2[i]) {
        return false;
      }
    }

    return true;
  }

  static int[] buildCounts(String s) {
    int[] counts = new int[26];
    for (char ch : s.toCharArray()) {
      ++counts[ch - 'a'];
    }

    return counts;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }
}
