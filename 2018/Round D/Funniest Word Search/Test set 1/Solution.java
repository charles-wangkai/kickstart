import java.util.Scanner;

public class Solution {
  static final int ALPHABET_SIZE = 26;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int W = sc.nextInt();
      char[][] grid = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }
      String[] words = new String[W];
      for (int i = 0; i < words.length; ++i) {
        words[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(grid, words)));
    }

    sc.close();
  }

  static String solve(char[][] grid, String[] words) {
    int R = grid.length;
    int C = grid[0].length;

    boolean[] letters = new boolean[ALPHABET_SIZE];
    for (String word : words) {
      letters[word.charAt(0) - 'A'] = true;
    }

    int[][] prefixSums = new int[R + 1][C + 1];
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        prefixSums[r + 1][c + 1] =
            prefixSums[r][c + 1]
                + prefixSums[r + 1][c]
                - prefixSums[r][c]
                + (letters[grid[r][c] - 'A'] ? 1 : 0);
      }
    }

    Rational funValue = new Rational(0, 1);
    int count = 0;
    for (int beginR = 0; beginR < R; ++beginR) {
      for (int endR = beginR; endR < R; ++endR) {
        for (int beginC = 0; beginC < C; ++beginC) {
          for (int endC = beginC; endC < C; ++endC) {
            int matched =
                (prefixSums[endR + 1][endC + 1]
                        - prefixSums[beginR][endC + 1]
                        - prefixSums[endR + 1][beginC]
                        + prefixSums[beginR][beginC])
                    * 4;

            Rational f = new Rational(matched, (endR - beginR + 1) + (endC - beginC + 1));
            int cmp = compare(f, funValue);
            if (cmp == 0) {
              ++count;
            } else if (cmp > 0) {
              funValue = f;
              count = 1;
            }
          }
        }
      }
    }

    return String.format("%d/%d %d", funValue.numerator, funValue.denominator, count);
  }

  static int compare(Rational r1, Rational r2) {
    return Integer.compare(r1.numerator * r2.denominator, r2.numerator * r1.denominator);
  }
}

class Rational {
  int numerator;
  int denominator;

  Rational(int numerator, int denominator) {
    int g = gcd(numerator, denominator);

    this.numerator = numerator / g;
    this.denominator = denominator / g;
  }

  int gcd(int x, int y) {
    return (y == 0) ? x : gcd(y, x % y);
  }
}
