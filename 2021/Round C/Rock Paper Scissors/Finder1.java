import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Finder1 {
  static final char[] CHOICES = {'R', 'S', 'P'};
  static Map<Character, Integer> CHOICE_TO_INDEX =
      IntStream.range(0, CHOICES.length)
          .boxed()
          .collect(Collectors.toMap(i -> CHOICES[i], Function.identity()));

  static Map<Character, Integer> choiceToCount = new HashMap<>();

  public static void main(String[] args) throws Throwable {
    int[][] inputs = readInputs();

    List<String> patterns = new ArrayList<>();
    // search(patterns, "", 12);
    int length = 29;
    // for (int i = 0; i <= length; ++i) {
    //   for (int j = 0; i + j <= length; ++j) {
    //     for (int k = 0; i + j + k <= length; ++k) {
    //       int p = length - i - j - k;

    //       patterns.add(
    //           String.format(
    //               "%s%s%s%s", "R".repeat(i), "S".repeat(j), "P".repeat(k), "R".repeat(p)));
    //     }
    //   }
    // }
    for (int c1 = 0; c1 < length; ++c1) {
      for (int c2 = 0; c1 + c2 < length; ++c2) {
        for (int c3 = 0; c1 + c2 + c3 < length; ++c3) {
          for (int c4 = 0; c1 + c2 + c3 + c4 < length; ++c4) {
            for (int c5 = 0; c1 + c2 + c3 + c4 + c5 < length; ++c5) {
              int c6 = length - c1 - c2 - c3 - c4 - c5;

              patterns.add(
                  String.format(
                      "%s%s%s%s%s%s",
                      "S".repeat(c1),
                      "P".repeat(c2),
                      "R".repeat(c3),
                      "S".repeat(c4),
                      "P".repeat(c5),
                      "R".repeat(c6)));
            }
          }
        }
      }
    }
    System.out.println("patterns.size(): " + patterns.size());

    double maxScore = 0;
    String bestPattern = null;

    for (String pattern : patterns) {
      String output = "R" + pattern.repeat(60);
      double score =
          IntStream.range(0, inputs.length)
              .filter(i -> inputs[i][1] == inputs[i][0] / 2)
              // .filter(i -> inputs[i][1] == 0)
              .mapToDouble(i -> computeScore(inputs[i], output))
              .average()
              .getAsDouble();

      // double score = 0;
      // for (int[] input : inputs) {
      //   score += computeScore(input, output) / inputs.length;
      // }

      if (score > maxScore) {
        maxScore = score;
        bestPattern = pattern;
      }
    }

    System.out.println("maxScore: " + maxScore);
    System.out.println("bestPattern: " + bestPattern);
  }

  static void search(List<String> patterns, String current, int rest) {
    if (rest == 0) {
      patterns.add(current);

      return;
    }
    if (current.isEmpty()) {
      search(patterns, "R", rest - 1);

      return;
    }

    for (char c : CHOICES) {
      search(patterns, current + c, rest - 1);
    }
  }

  static double computeScore(int[] input, String output) {
    output = output.substring(0, 60);

    double score = 0;
    int total = 0;

    choiceToCount.clear();
    for (char c : output.toCharArray()) {
      for (int i = 0; i < CHOICES.length; ++i) {
        char defeated = CHOICES[(i + 1) % CHOICES.length];
        double p =
            (total == 0) ? (1.0 / 3) : ((double) choiceToCount.getOrDefault(defeated, 0) / total);

        int cmp = compare(c, CHOICES[i]);
        if (cmp == 1) {
          score += p * input[0];
        } else if (cmp == 0) {
          score += p * input[1];
        }
      }

      choiceToCount.put(c, choiceToCount.getOrDefault(c, 0) + 1);
      ++total;
    }

    return score;
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

  static int[][] readInputs() throws Throwable {
    Scanner sc =
        new Scanner(
            new File("/Users/kaiwang/repos/kickstart/2021/Round C/Rock Paper Scissors/in.txt"));

    int T = sc.nextInt();
    int[][] inputs = new int[T][2];
    sc.nextInt();
    for (int i = 0; i < inputs.length; ++i) {
      inputs[i][0] = sc.nextInt();
      inputs[i][1] = sc.nextInt();
    }

    sc.close();

    return inputs;
  }

  // static String[] readOutputs(int T) throws Throwable {
  //   Scanner sc =
  //       new Scanner(
  //           new File("/Users/kaiwang/repos/kickstart/2021/Round C/Rock Paper Scissors/out.txt"));

  //   String[] outputs = new String[T];
  //   for (int i = 0; i < outputs.length; ++i) {
  //     String[] parts = sc.nextLine().split(" ");
  //     outputs[i] = parts[parts.length - 1];
  //   }

  //   sc.close();

  //   return outputs;
  // }
}
