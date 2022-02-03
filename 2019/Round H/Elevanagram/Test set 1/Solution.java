import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int[] A = new int[9];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] A) {
    Set<State> states = new HashSet<>();
    states.add(new State(0, 0));
    for (int i = 0; i < A.length; ++i) {
      Set<State> nextStates = new HashSet<>();
      for (State state : states) {
        for (int j = 0; j <= A[i]; ++j) {
          nextStates.add(
              new State(
                  state.countDiff + (j - (A[i] - j)), state.sumDiff + (i + 1) * (j - (A[i] - j))));
        }
      }

      states = nextStates;
    }

    return states.stream()
        .anyMatch(
            state -> state.sumDiff % 11 == 0 && (state.countDiff == 0 || state.countDiff == 1));
  }
}

class State {
  int countDiff;
  int sumDiff;

  State(int countDiff, int sumDiff) {
    this.countDiff = countDiff;
    this.sumDiff = sumDiff;
  }

  @Override
  public int hashCode() {
    return Objects.hash(countDiff, sumDiff);
  }

  @Override
  public boolean equals(Object obj) {
    State other = (State) obj;

    return countDiff == other.countDiff && sumDiff == other.sumDiff;
  }
}