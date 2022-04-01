import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Solution {
  static final int DIGIT_NUM = 10;

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
    int N = S.length();

    int[] digits = S.chars().map(c -> c - '0').toArray();

    int[] prevIndices = IntStream.range(0, N).map(i -> i - 1).toArray();
    int[] nextIndices = IntStream.range(0, N).map(i -> (i == N - 1) ? -1 : (i + 1)).toArray();

    @SuppressWarnings("unchecked")
    Set<Integer>[] indexSets = new Set[DIGIT_NUM];
    for (int i = 0; i < indexSets.length; ++i) {
      indexSets[i] = new HashSet<>();
    }
    for (int i = 0; i < digits.length - 1; ++i) {
      if ((digits[i] + 1) % DIGIT_NUM == digits[i + 1]) {
        indexSets[digits[i]].add(i);
      }
    }

    while (true) {
      boolean changed = false;
      for (int i = 0; i < DIGIT_NUM; ++i) {
        int newDigit = (i + 2) % DIGIT_NUM;

        if (!indexSets[i].isEmpty()) {
          changed = true;

          for (int index : indexSets[i]) {
            if (prevIndices[index] != -1) {
              if ((digits[prevIndices[index]] + 1) % DIGIT_NUM == i) {
                indexSets[digits[prevIndices[index]]].remove(prevIndices[index]);
              }
              if ((digits[prevIndices[index]] + 1) % DIGIT_NUM == newDigit) {
                indexSets[digits[prevIndices[index]]].add(prevIndices[index]);
              }
            }

            int afterIndex = nextIndices[nextIndices[index]];
            if (afterIndex != -1) {
              if ((i + 2) % DIGIT_NUM == digits[afterIndex]) {
                indexSets[(i + 1) % DIGIT_NUM].remove(nextIndices[index]);
              }
              if ((newDigit + 1) % DIGIT_NUM == digits[afterIndex]) {
                indexSets[newDigit].add(index);
              }
            }

            nextIndices[index] = afterIndex;
            if (afterIndex != -1) {
              prevIndices[afterIndex] = index;
            }

            digits[index] = newDigit;
          }

          indexSets[i].clear();
        }
      }

      if (!changed) {
        break;
      }
    }

    StringBuilder result = new StringBuilder();
    int index = 0;
    while (index != -1) {
      result.append(digits[index]);
      index = nextIndices[index];
    }

    return result.toString();
  }
}