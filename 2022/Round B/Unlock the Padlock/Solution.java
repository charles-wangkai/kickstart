import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int D = sc.nextInt();
      int[] V = new int[N];
      for (int i = 0; i < V.length; ++i) {
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, D)));
    }

    sc.close();
  }

  static long solve(int[] V, int D) {
    int[] reduced =
        IntStream.range(0, V.length)
            .filter(i -> i == 0 || V[i] != V[i - 1])
            .map(i -> V[i])
            .toArray();

    @SuppressWarnings("unchecked")
    List<State>[][] dp = new List[reduced.length][reduced.length];
    for (int i = 0; i < dp.length; ++i) {
      dp[i][i] = List.of(new State(reduced[i], 0));
    }
    for (int length = 2; length <= reduced.length; ++length) {
      for (int beginIndex = 0; beginIndex + length <= reduced.length; ++beginIndex) {
        int endIndex = beginIndex + length - 1;

        if (reduced[beginIndex] == reduced[endIndex]) {
          dp[beginIndex][endIndex] =
              List.of(transfer(D, dp[beginIndex + 1][endIndex - 1], reduced[beginIndex]));
        } else {
          dp[beginIndex][endIndex] =
              List.of(
                  transfer(D, dp[beginIndex + 1][endIndex], reduced[beginIndex]),
                  transfer(D, dp[beginIndex][endIndex - 1], reduced[endIndex]));
        }
      }
    }

    return dp[0][dp.length - 1].stream()
        .mapToLong(
            state ->
                state.operationNum + Math.min(Math.abs(state.value), D - Math.abs(state.value)))
        .min()
        .getAsLong();
  }

  static State transfer(int D, List<State> prevStates, int target) {
    return new State(
        target,
        prevStates.stream()
            .mapToLong(
                prevState ->
                    prevState.operationNum
                        + Math.min(
                            Math.abs(prevState.value - target),
                            D - Math.abs(prevState.value - target)))
            .min()
            .getAsLong());
  }
}

class State {
  int value;
  long operationNum;

  State(int value, long operationNum) {
    this.value = value;
    this.operationNum = operationNum;
  }
}