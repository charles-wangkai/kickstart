import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int[] availables = new int[10];
      for (int i = 0; i < availables.length; ++i) {
        availables[i] = sc.nextInt();
      }
      int X = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(availables, X)));
    }

    sc.close();
  }

  static String solve(int[] availables, int X) {
    List<Integer> divisors = new ArrayList<>();
    for (int i = 1; i * i <= X; ++i) {
      if (X % i == 0) {
        divisors.add(i);
        if (i * i != X) {
          divisors.add(X / i);
        }
      }
    }
    Collections.sort(divisors);

    Set<Integer> availableDivisors =
        divisors.stream()
            .filter(
                divisor ->
                    String.valueOf(divisor).chars().allMatch(ch -> availables[ch - '0'] == 1))
            .collect(Collectors.toSet());

    int[] clickNums = new int[divisors.size()];
    for (int i = 0; i < clickNums.length; ++i) {
      clickNums[i] =
          availableDivisors.contains(divisors.get(i))
              ? String.valueOf(divisors.get(i)).length()
              : Integer.MAX_VALUE;
      for (int j = 0; j < i; ++j) {
        if (divisors.get(i) % divisors.get(j) == 0
            && clickNums[j] != Integer.MAX_VALUE
            && availableDivisors.contains(divisors.get(i) / divisors.get(j))) {
          clickNums[i] =
              Math.min(
                  clickNums[i],
                  clickNums[j] + 1 + String.valueOf(divisors.get(i) / divisors.get(j)).length());
        }
      }
    }

    return (clickNums[clickNums.length - 1] == Integer.MAX_VALUE)
        ? "Impossible"
        : String.valueOf(clickNums[clickNums.length - 1] + 1);
  }
}
