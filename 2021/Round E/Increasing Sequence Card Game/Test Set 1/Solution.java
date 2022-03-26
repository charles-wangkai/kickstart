import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N)));
    }

    sc.close();
  }

  static double solve(int N) {
    List<Integer> scores = new ArrayList<>();
    search(scores, IntStream.rangeClosed(1, N).toArray(), 0);

    return scores.stream().mapToInt(x -> x).average().getAsDouble();
  }

  static void search(List<Integer> scores, int[] cards, int index) {
    if (index == cards.length) {
      scores.add(computeScore(cards));

      return;
    }

    for (int i = index; i < cards.length; ++i) {
      swap(cards, i, index);
      search(scores, cards, index + 1);
      swap(cards, i, index);
    }
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  static int computeScore(int[] cards) {
    int result = 0;
    int max = 0;
    for (int card : cards) {
      if (card > max) {
        max = card;
        ++result;
      }
    }

    return result;
  }
}