import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  static final char[] CHOICES = {'R', 'S', 'P'};
  static final int ROUND_NUM = 60;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    int X = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int E = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(X, W, E)));
    }

    sc.close();
  }

  static String solve(int X, int W, int E) {
    Map<String, State> codeToState = Map.of(encode(new int[] {0, 0, 0}), new State(0, ""));
    for (int i = 0; i < ROUND_NUM; ++i) {
      Map<String, State> nextCodeToState = new HashMap<>();
      for (String code : codeToState.keySet()) {
        int[] counts = decode(code);
        State state = codeToState.get(code);

        double[] probs = new double[CHOICES.length];
        int total = Arrays.stream(counts).sum();
        for (int j = 0; j < probs.length; ++j) {
          probs[j] = (total == 0) ? (1.0 / 3) : ((double) counts[(j + 1) % counts.length] / total);
        }

        for (int j = 0; j < CHOICES.length; ++j) {
          ++counts[j];

          double delta = 0;
          for (int k = 0; k < CHOICES.length; ++k) {
            int cmp = compare(j, k);
            if (cmp == 1) {
              delta += probs[k] * W;
            } else if (cmp == 0) {
              delta += probs[k] * E;
            }
          }

          String nextCode = encode(counts);
          double nextScore = state.score + delta;
          if (nextScore > nextCodeToState.getOrDefault(nextCode, new State(0, null)).score) {
            nextCodeToState.put(nextCode, new State(nextScore, state.choices + CHOICES[j]));
          }

          --counts[j];
        }
      }

      codeToState = nextCodeToState;
    }

    return codeToState.values().stream()
        .max(Comparator.comparing(state -> state.score))
        .get()
        .choices;
  }

  static int compare(int index1, int index2) {
    int diff = index2 - index1;
    if (diff == 2) {
      diff = -1;
    } else if (diff == -2) {
      diff = 1;
    }

    return diff;
  }

  static int[] decode(String code) {
    return Arrays.stream(code.split(",")).mapToInt(Integer::parseInt).toArray();
  }

  static String encode(int[] counts) {
    return Arrays.stream(counts).mapToObj(String::valueOf).collect(Collectors.joining(","));
  }
}

class State {
  double score;
  String choices;

  State(double score, String choices) {
    this.score = score;
    this.choices = choices;
  }
}
