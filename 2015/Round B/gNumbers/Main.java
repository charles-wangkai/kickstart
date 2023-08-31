import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long N = sc.nextLong();

      System.out.println(String.format("Case #%d: %s", tc, solve(N)));
    }

    sc.close();
  }

  static String solve(long N) {
    return isFirstPlayerWin(new HashMap<>(), new Number(N, buildPrimeFactors(N)))
        ? "Laurence"
        : "Seymour";
  }

  static boolean isFirstPlayerWin(Map<Long, Boolean> cache, Number number) {
    if (!cache.containsKey(number.value)) {
      boolean result = false;
      if (!isGNumber(number.value)) {
        for (long primeFactor : number.primeFactors) {
          long nextValue = number.value;
          while (nextValue % primeFactor == 0) {
            nextValue /= primeFactor;
          }

          List<Long> nextPrimeFactors =
              number.primeFactors.stream()
                  .filter(x -> x != primeFactor)
                  .collect(Collectors.toList());

          if (!isFirstPlayerWin(cache, new Number(nextValue, nextPrimeFactors))) {
            result = true;
          }
        }
      }

      cache.put(number.value, result);
    }

    return cache.get(number.value);
  }

  static boolean isGNumber(long x) {
    int digitSum = String.valueOf(x).chars().map(ch -> ch - '0').sum();

    for (int i = 2; i * i <= digitSum; ++i) {
      if (digitSum % i == 0) {
        return false;
      }
    }

    return true;
  }

  static List<Long> buildPrimeFactors(long N) {
    List<Long> result = new ArrayList<>();
    for (int i = 2; (long) i * i <= N; ++i) {
      if (N % i == 0) {
        result.add((long) i);
        while (N % i == 0) {
          N /= i;
        }
      }
    }
    if (N != 1) {
      result.add(N);
    }

    return result;
  }
}

class Number {
  long value;
  List<Long> primeFactors;

  Number(long value, List<Long> primeFactors) {
    this.value = value;
    this.primeFactors = primeFactors;
  }
}
