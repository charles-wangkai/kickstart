import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Checker {
  static final char[] CHOICES = {'R', 'S', 'P'};

  public static void main(String[] args) throws Throwable {
    int[][] inputs = readInputs();
    String[] outputs = readOutputs(inputs.length);

    System.out.println(
        IntStream.range(0, inputs.length)
            .mapToDouble(i -> computeScore(inputs[i], outputs[i]))
            .average()
            .getAsDouble());
  }

  static double computeScore(int[] input, String output) {
    output = output.substring(0, 60);

    double score = 0;
    int total = 0;
    Map<Character, Integer> choiceToCount = new HashMap<>();
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
    int index1 =
        IntStream.range(0, CHOICES.length).filter(i -> CHOICES[i] == c1).findAny().getAsInt();
    int index2 =
        IntStream.range(0, CHOICES.length).filter(i -> CHOICES[i] == c2).findAny().getAsInt();

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

  static String[] readOutputs(int T) throws Throwable {
    Scanner sc =
        new Scanner(
            new File("/Users/kaiwang/repos/kickstart/2021/Round C/Rock Paper Scissors/out.txt"));

    String[] outputs = new String[T];
    for (int i = 0; i < outputs.length; ++i) {
      String[] parts = sc.nextLine().split(" ");
      outputs[i] = parts[parts.length - 1];
    }

    sc.close();

    return outputs;
  }
}
