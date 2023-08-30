import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final int DIGIT_NUM = 10;
  static final int SEGMENT_NUM = 7;
  static final String[] DISPLAYS = {
    "1111110", "0110000", "1101101", "1111001", "0110011", "1011011", "1011111", "1110000",
    "1111111", "1111011"
  };

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] states = new String[N];
      for (int i = 0; i < states.length; ++i) {
        states[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(states)));
    }

    sc.close();
  }

  static String solve(String[] states) {
    Set<String> nextStates = new HashSet<>();
    for (int code = 0; code < 1 << SEGMENT_NUM; ++code) {
      for (int begin = 0; begin < DISPLAYS.length; ++begin) {
        if (check(states, code, begin)) {
          nextStates.add(computeState(code, move(begin, states.length)));
        }
      }
    }

    return (nextStates.size() == 1) ? nextStates.iterator().next() : "ERROR!";
  }

  static String computeState(int code, int digit) {
    return IntStream.range(0, SEGMENT_NUM)
        .mapToObj(i -> ((code & (1 << i)) == 0) ? "0" : String.valueOf(DISPLAYS[digit].charAt(i)))
        .collect(Collectors.joining());
  }

  static boolean check(String[] states, int code, int begin) {
    return IntStream.range(0, states.length)
        .allMatch(i -> states[i].equals(computeState(code, move(begin, i))));
  }

  static int move(int begin, int step) {
    return ((begin - step) % DIGIT_NUM + DIGIT_NUM) % DIGIT_NUM;
  }
}
