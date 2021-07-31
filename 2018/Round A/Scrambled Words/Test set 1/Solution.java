import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    String S = generate(S1, S2, N, A, B, C, D);

    return (int) Arrays.stream(words).filter(word -> contain(S, word)).count();
  }

  static String generate(char S1, char S2, int N, int A, int B, int C, int D) {
    int[] x = new int[N];
    x[0] = S1;
    x[1] = S2;
    for (int i = 2; i < x.length; ++i) {
      x[i] = (int) (((long) A * x[i - 1] + (long) B * x[i - 2] + C) % D);
    }

    return String.format(
        "%c%c%s",
        S1,
        S2,
        IntStream.range(2, x.length)
            .mapToObj(i -> String.valueOf((char) ('a' + x[i] % 26)))
            .collect(Collectors.joining()));
  }

  static boolean contain(String S, String word) {
    if (word.length() > S.length()) {
      return false;
    }

    char wordBegin = word.charAt(0);
    char wordEnd = word.charAt(word.length() - 1);
    int[] wordCounts = new int[26];
    for (int i = 1; i < word.length() - 1; ++i) {
      ++wordCounts[word.charAt(i) - 'a'];
    }

    int[] counts = new int[26];
    for (int i = 1; i < word.length() - 1; ++i) {
      ++counts[S.charAt(i) - 'a'];
    }
    for (int i = word.length() - 1; i < S.length(); ++i) {
      char begin = S.charAt(i - word.length() + 1);
      char end = S.charAt(i);

      if (begin == wordBegin && end == wordEnd && Arrays.equals(counts, wordCounts)) {
        return true;
      }

      --counts[S.charAt(i - word.length() + 2) - 'a'];
      ++counts[end - 'a'];
    }

    return false;
  }
}
