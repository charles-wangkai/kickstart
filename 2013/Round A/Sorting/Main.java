import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] s = new int[N];
      for (int i = 0; i < s.length; ++i) {
        s[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(s)));
    }

    sc.close();
  }

  static String solve(int[] s) {
    int[] sortedEvens =
        Arrays.stream(s)
            .filter(x -> x % 2 == 0)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .mapToInt(x -> x)
            .toArray();
    int[] sortedOdds = Arrays.stream(s).filter(x -> x % 2 != 0).sorted().toArray();

    int[] result = new int[s.length];
    int evenIndex = 0;
    int oddIndex = 0;
    for (int i = 0; i < result.length; ++i) {
      if (s[i] % 2 == 0) {
        result[i] = sortedEvens[evenIndex];
        ++evenIndex;
      } else {
        result[i] = sortedOdds[oddIndex];
        ++oddIndex;
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}
