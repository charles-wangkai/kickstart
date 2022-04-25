import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long A = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static int solve(long A) {
    return search(buildTerms(A), 0, 1);
  }

  static int search(List<Term> terms, int index, long factor) {
    if (index == terms.size()) {
      return isPalindrome(factor) ? 1 : 0;
    }

    int result = 0;
    for (int i = 0; i <= terms.get(index).exponent; ++i) {
      result += search(terms, index + 1, factor);
      factor *= terms.get(index).prime;
    }

    return result;
  }

  static boolean isPalindrome(long x) {
    return Long.parseLong(new StringBuilder(String.valueOf(x)).reverse().toString()) == x;
  }

  static List<Term> buildTerms(long A) {
    List<Term> result = new ArrayList<>();
    for (int i = 2; (long) i * i <= A; ++i) {
      int exponent = 0;
      while (A % i == 0) {
        ++exponent;
        A /= i;
      }
      if (exponent != 0) {
        result.add(new Term(i, exponent));
      }
    }
    if (A != 1) {
      result.add(new Term(A, 1));
    }

    return result;
  }
}

class Term {
  long prime;
  int exponent;

  Term(long prime, int exponent) {
    this.prime = prime;
    this.exponent = exponent;
  }
}