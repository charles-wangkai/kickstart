import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Searcher {
  static final char[] CHOICES = {'R', 'S', 'P'};
  static Map<Character, Integer> CHOICE_TO_INDEX =
      IntStream.range(0, CHOICES.length)
          .boxed()
          .collect(Collectors.toMap(i -> CHOICES[i], Function.identity()));

  public static void main(String[] args) {
    String s = "R";
    double score = 0;
    Map<Character, Integer> choiceToCount = new HashMap<>();
    choiceToCount.put('R', 1);
    while (s.length() != 60) {
      double maxDelta = -1;
      char bestC = 0;
      for (char c : CHOICES) {
        double delta = 0;
        for (int i = 0; i < CHOICES.length; ++i) {
          char defeated = CHOICES[(i + 1) % CHOICES.length];
          double p = (double) choiceToCount.getOrDefault(defeated, 0) / s.length();

          int cmp = compare(c, CHOICES[i]);
          if (cmp == 1) {
            delta += p * 50;
          } else if (cmp == 0) {
            delta += p * 2;
          }
        }

        if (delta > maxDelta) {
          maxDelta = delta;
          bestC = c;
        }
      }

      s += bestC;
      score += maxDelta;
      choiceToCount.put(bestC, choiceToCount.getOrDefault(bestC, 0) + 1);
    }

    System.out.println("s: " + s);
    System.out.println("score: " + score);
  }

  static int compare(char c1, char c2) {
    int index1 = CHOICE_TO_INDEX.get(c1);
    int index2 = CHOICE_TO_INDEX.get(c2);

    int diff = index2 - index1;
    if (diff == 2) {
      diff = -1;
    } else if (diff == -2) {
      diff = 1;
    }

    return diff;
  }
}
