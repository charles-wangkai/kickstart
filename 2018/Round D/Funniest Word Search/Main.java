// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050ee1/00000000000510f0#analysis

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  static final int R_LIMIT = 100;
  static final int C_LIMIT = 100;

  public static void main(String[] args) {
    int[][][] right = new int[R_LIMIT][C_LIMIT][C_LIMIT + 1];
    int[][][] down = new int[R_LIMIT][C_LIMIT][R_LIMIT + 1];
    int[][][][] rightTotals = new int[R_LIMIT][C_LIMIT][R_LIMIT][C_LIMIT];
    int[][][][] downTotals = new int[R_LIMIT][C_LIMIT][R_LIMIT][C_LIMIT];

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

      System.out.println(
          String.format(
              "Case #%d: %s", tc, solve(right, down, rightTotals, downTotals, grid, words)));
    }

    sc.close();
  }

  static String solve(
      int[][][] right,
      int[][][] down,
      int[][][][] rightTotals,
      int[][][][] downTotals,
      char[][] grid,
      String[] words) {
    int R = grid.length;
    int C = grid[0].length;

    @SuppressWarnings("unchecked")
    List<String>[] wordLookup = new List[Math.max(R, C) + 1];
    for (int i = 0; i < wordLookup.length; ++i) {
      wordLookup[i] = new ArrayList<>();
    }
    for (String word : words) {
      if (word.length() < wordLookup.length) {
        wordLookup[word.length()].add(word);
        wordLookup[word.length()].add(new StringBuilder(word).reverse().toString());
      }
    }

    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        for (int k = 1; c + k - 1 < C; ++k) {
          int matched = 0;
          for (String word : wordLookup[k]) {
            if (isMatched(grid, word, r, c, 0, 1)) {
              ++matched;
            }
          }

          right[r][c][k] = k * matched + right[r][c][k - 1];
        }
      }
    }

    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        for (int k = 1; r + k - 1 < R; ++k) {
          int matched = 0;
          for (String word : wordLookup[k]) {
            if (isMatched(grid, word, r, c, 1, 0)) {
              ++matched;
            }
          }

          down[r][c][k] = k * matched + down[r][c][k - 1];
        }
      }
    }

    for (int r1 = 0; r1 < R; ++r1) {
      int[][] rightColumnSums = new int[C][C + 1];
      for (int r2 = r1; r2 < R; ++r2) {
        for (int c = 0; c < C; ++c) {
          for (int k = 1; c + k - 1 < C; ++k) {
            rightColumnSums[c][k] += right[r2][c][k];
          }
        }

        for (int c2 = 0; c2 < C; ++c2) {
          rightTotals[r1][c2][r2][c2] = rightColumnSums[c2][1];
          for (int c1 = c2 - 1; c1 >= 0; --c1) {
            rightTotals[r1][c1][r2][c2] =
                rightTotals[r1][c1 + 1][r2][c2] + rightColumnSums[c1][c2 - c1 + 1];
          }
        }
      }
    }

    for (int c1 = 0; c1 < C; ++c1) {
      int[][] downRowSums = new int[R][R + 1];
      for (int c2 = c1; c2 < C; ++c2) {
        for (int r = 0; r < R; ++r) {
          for (int k = 1; r + k - 1 < R; ++k) {
            downRowSums[r][k] += down[r][c2][k];
          }
        }

        for (int r2 = 0; r2 < R; ++r2) {
          downTotals[r2][c1][r2][c2] = downRowSums[r2][1];
          for (int r1 = r2 - 1; r1 >= 0; --r1) {
            downTotals[r1][c1][r2][c2] =
                downTotals[r1 + 1][c1][r2][c2] + downRowSums[r1][r2 - r1 + 1];
          }
        }
      }
    }

    long funNumerator = 0;
    long funDenominator = 1;
    int count = 0;
    for (int r1 = 0; r1 < R; ++r1) {
      for (int c1 = 0; c1 < C; ++c1) {
        for (int r2 = r1; r2 < R; ++r2) {
          for (int c2 = c1; c2 < C; ++c2) {
            long numerator = (long) rightTotals[r1][c1][r2][c2] + downTotals[r1][c1][r2][c2];
            long denominator = (r2 - r1 + 1) + (c2 - c1 + 1);

            int cmp = Long.compare(numerator * funDenominator, funNumerator * denominator);
            if (cmp == 0) {
              ++count;
            } else if (cmp > 0) {
              long g = gcd(numerator, denominator);
              funNumerator = numerator / g;
              funDenominator = denominator / g;

              count = 1;
            }
          }
        }
      }
    }

    return String.format("%d/%d %d", funNumerator, funDenominator, count);
  }

  static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  static boolean isMatched(
      char[][] grid, String word, int beginR, int beginC, int offsetR, int offsetC) {
    for (int i = 0; i < word.length(); ++i) {
      if (grid[beginR + offsetR * i][beginC + offsetC * i] != word.charAt(i)) {
        return false;
      }
    }

    return true;
  }
}
