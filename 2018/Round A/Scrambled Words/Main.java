import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
  static final long[] PLACE_VALUES_1 = buildPlaceValues(31);
  static final long[] PLACE_VALUES_2 = buildPlaceValues(37);

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

  static long[] buildPlaceValues(int base) {
    long[] result = new long[26];
    for (int i = 0; i < result.length; ++i) {
      result[i] = ((i == 0) ? 1 : result[i - 1]) * base;
    }

    return result;
  }

  static int solve(String[] words, char S1, char S2, int N, int A, int B, int C, int D) {
    char[] S = generate(S1, S2, N, A, B, C, D);

    Map<Integer, List<String>> lengthToWords = new HashMap<>();
    for (String word : words) {
      int length = word.length();
      lengthToWords.putIfAbsent(length, new ArrayList<>());
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

    Map<WordKey, Integer> wordKeyToCount = new HashMap<>();
    for (String word : words) {
      WordKey wordKey = buildWordKey(word);
      wordKeyToCount.put(wordKey, wordKeyToCount.getOrDefault(wordKey, 0) + 1);
    }

    int result = 0;
    long middleHash1 = 0;
    long middleHash2 = 0;
    for (int i = 1; i < length - 1; ++i) {
      middleHash1 += PLACE_VALUES_1[S[i] - 'a'];
      middleHash2 += PLACE_VALUES_2[S[i] - 'a'];
    }
    for (int i = length - 1; i < S.length; ++i) {
      char begin = S[i - length + 1];
      char end = S[i];

      WordKey wordKey = new WordKey(begin, end, middleHash1, middleHash2);
      if (wordKeyToCount.containsKey(wordKey)) {
        result += wordKeyToCount.remove(wordKey);

        if (wordKeyToCount.isEmpty()) {
          break;
        }
      }

      middleHash1 -= PLACE_VALUES_1[S[i - length + 2] - 'a'];
      middleHash2 -= PLACE_VALUES_2[S[i - length + 2] - 'a'];

      middleHash1 += PLACE_VALUES_1[end - 'a'];
      middleHash2 += PLACE_VALUES_2[end - 'a'];
    }

    return result;
  }

  static WordKey buildWordKey(String word) {
    long middleHash1 = 0;
    long middleHash2 = 0;
    for (int i = 1; i < word.length() - 1; ++i) {
      middleHash1 += PLACE_VALUES_1[word.charAt(i) - 'a'];
      middleHash2 += PLACE_VALUES_2[word.charAt(i) - 'a'];
    }

    return new WordKey(word.charAt(0), word.charAt(word.length() - 1), middleHash1, middleHash2);
  }
}

class WordKey {
  char begin;
  char end;
  long middleHash1;
  long middleHash2;

  WordKey(char begin, char end, long middleHash1, long middleHash2) {
    this.begin = begin;
    this.end = end;
    this.middleHash1 = middleHash1;
    this.middleHash2 = middleHash2;
  }

  @Override
  public int hashCode() {
    return Objects.hash(begin, end, middleHash1, middleHash2);
  }

  @Override
  public boolean equals(Object obj) {
    WordKey other = (WordKey) obj;

    return begin == other.begin
        && end == other.end
        && middleHash1 == other.middleHash1
        && middleHash2 == other.middleHash2;
  }
}
