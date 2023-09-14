import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int DIGIT_NUM = 12;

  static List<int[]> countsList;

  public static void main(String[] args) {
    buildCountsList();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long A = sc.nextLong();
      long B = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B)));
    }

    sc.close();
  }

  static void buildCountsList() {
    countsList = new ArrayList<>();
    search(new int[10], 1, DIGIT_NUM, 0, 1);
  }

  static void search(int[] counts, int index, int rest, int digitSum, long digitProduct) {
    if (index == counts.length) {
      if (digitSum != 0 && digitProduct % digitSum == 0) {
        countsList.add(counts.clone());
      }

      return;
    }

    for (int i = 0; i <= rest; ++i) {
      counts[index] = i;
      search(
          counts,
          index + 1,
          rest - i,
          digitSum + index * counts[index],
          digitProduct * pow(index, counts[index]));
    }
  }

  static long solve(long A, long B) {
    return computeInterestingNum(B) - computeInterestingNum(A - 1);
  }

  static long computeHasZeroNum(long limit) {
    if (limit == 0) {
      return 0;
    }

    int[] digits = toDigits(limit);

    long result = 0;
    for (int i = digits.length - 2; i >= 1; --i) {
      result += 9 * (pow(10, i) - pow(9, i));
    }

    for (int i = 0; i < digits.length; ++i) {
      for (int d = ((i == 0) ? 1 : 0); d < digits[i]; ++d) {
        if (d == 0) {
          result += pow(10, digits.length - i - 1);
        } else {
          result += pow(10, digits.length - i - 1) - pow(9, digits.length - i - 1);
        }
      }

      if (digits[i] == 0) {
        long right = 0;
        for (int j = i + 1; j < digits.length; ++j) {
          right = right * 10 + digits[j];
        }

        result += right + 1;

        break;
      }
    }

    return result;
  }

  static long pow(int base, int exponent) {
    return IntStream.range(0, exponent).asLongStream().reduce(1, (x, y) -> x * base);
  }

  static int[] toDigits(long x) {
    return String.valueOf(x).chars().map(c -> c - '0').toArray();
  }

  static boolean isInteresting(int[] digits) {
    int sum = Arrays.stream(digits).sum();

    return sum != 0 && Arrays.stream(digits).asLongStream().reduce(1, (p, q) -> p * q) % sum == 0;
  }

  static long computeInterestingNum(long limit) {
    if (limit == 0) {
      return 0;
    }
    if (limit == pow(10, DIGIT_NUM)) {
      return (isInteresting(toDigits(limit)) ? 1 : 0) + computeInterestingNum(limit - 1);
    }

    long result = computeHasZeroNum(limit);
    int[] digits = toDigits(limit);
    for (int i = 0; i < countsList.size(); ++i) {
      int[] counts = countsList.get(i).clone();
      int countSum = Arrays.stream(counts).sum();
      if (countSum < digits.length) {
        result += computeFreeNum(counts);
      } else if (countSum == digits.length) {
        for (int j = 0; j <= digits.length; ++j) {
          if (j == digits.length) {
            ++result;
          } else {
            for (int d = 1; d < digits[j]; ++d) {
              if (counts[d] != 0) {
                --counts[d];
                result += computeFreeNum(counts);
                ++counts[d];
              }
            }

            if (digits[j] == 0 || counts[digits[j]] == 0) {
              break;
            }
            --counts[digits[j]];
          }
        }
      }
    }

    return result;
  }

  static long computeFreeNum(int[] counts) {
    long result = 1;
    int size = Arrays.stream(counts).sum();
    for (int count : counts) {
      result *= C(size, count);
      size -= count;
    }

    return result;
  }

  static int C(int n, int r) {
    int result = 1;
    for (int i = 0; i < r; ++i) {
      result = result * (n - i) / (i + 1);
    }

    return result;
  }
}
