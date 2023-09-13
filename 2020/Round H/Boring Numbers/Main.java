import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long L = sc.nextLong();
      long R = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(L, R)));
    }

    sc.close();
  }

  static long solve(long L, long R) {
    return computeBoringNum(R) - computeBoringNum(L - 1);
  }

  static long computeBoringNum(long limit) {
    int[] digits = String.valueOf(limit).chars().map(c -> c - '0').toArray();

    long result = IntStream.range(1, digits.length).mapToLong(Main::pow5).sum();
    int index = 0;
    while (index != digits.length) {
      for (int d = 0; d < digits[index]; ++d) {
        if (index % 2 != d % 2) {
          result += pow5(digits.length - index - 1);
        }
      }

      if (index % 2 == digits[index] % 2) {
        break;
      }

      ++index;
    }
    if (index == digits.length) {
      ++result;
    }

    return result;
  }

  static long pow5(int exponent) {
    return IntStream.range(0, exponent).asLongStream().reduce(1, (x, y) -> x * 5);
  }
}
