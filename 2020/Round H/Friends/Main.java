import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final int ALPHABET_SIZE = 26;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      String[] S = new String[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.next();
      }
      int[] X = new int[Q];
      int[] Y = new int[Q];
      for (int i = 0; i < Q; ++i) {
        X[i] = sc.nextInt() - 1;
        Y[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(S, X, Y)));
    }

    sc.close();
  }

  static String solve(String[] S, int[] X, int[] Y) {
    int[][] distances = new int[ALPHABET_SIZE][ALPHABET_SIZE];
    for (int i = 0; i < ALPHABET_SIZE; ++i) {
      for (int j = 0; j < ALPHABET_SIZE; ++j) {
        distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (String name : S) {
      int[] letters = buildLetters(name);
      for (int i = 0; i < letters.length; ++i) {
        for (int j = i + 1; j < letters.length; ++j) {
          distances[letters[i]][letters[j]] = 1;
          distances[letters[j]][letters[i]] = 1;
        }
      }
    }

    for (int k = 0; k < ALPHABET_SIZE; ++k) {
      for (int i = 0; i < ALPHABET_SIZE; ++i) {
        for (int j = 0; j < ALPHABET_SIZE; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    return IntStream.range(0, X.length)
        .map(
            i -> {
              int[] xLetters = buildLetters(S[X[i]]);
              int[] yLetters = buildLetters(S[Y[i]]);

              int minDistance = Integer.MAX_VALUE;
              for (int xLetter : xLetters) {
                for (int yLetter : yLetters) {
                  minDistance = Math.min(minDistance, distances[xLetter][yLetter]);
                }
              }

              return (minDistance == Integer.MAX_VALUE) ? -1 : (2 + minDistance);
            })
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  static int[] buildLetters(String s) {
    return s.chars().distinct().map(c -> c - 'A').toArray();
  }
}
