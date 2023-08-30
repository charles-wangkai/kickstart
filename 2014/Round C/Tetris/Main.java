import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final String[][][] TETROMINOS = {
    null,
    {
      {
        "x.", //
        "xx", //
        ".x"
      },
      {
        ".xx", //
        "xx."
      },
      {
        "x.", //
        "xx", //
        ".x"
      },
      {
        ".xx", //
        "xx."
      }
    },
    {
      {
        ".x", //
        "xx", //
        "x."
      },
      {
        "xx.", //
        ".xx"
      },
      {
        ".x", //
        "xx", //
        "x."
      },
      {
        "xx.", //
        ".xx"
      }
    },
    {
      {
        "x.", //
        "x.", //
        "xx"
      },
      {
        "..x", //
        "xxx"
      },
      {
        "xx", //
        ".x", //
        ".x"
      },
      {
        "xxx", //
        "x.."
      }
    },
    {
      {
        ".x", //
        ".x", //
        "xx"
      },
      {
        "xxx", //
        "..x"
      },
      {
        "xx", //
        "x.", //
        "x."
      },
      {
        "x..", //
        "xxx"
      }
    },
    {
      {
        "xx", //
        "xx"
      },
      {
        "xx", //
        "xx"
      },
      {
        "xx", //
        "xx"
      },
      {
        "xx", //
        "xx"
      }
    },
    {
      {
        "x", //
        "x", //
        "x", //
        "x"
      },
      {"xxxx"},
      {
        "x", //
        "x", //
        "x", //
        "x"
      },
      {"xxxx"}
    },
    {
      {
        ".x.", //
        "xxx"
      },
      {
        ".x", //
        "xx", //
        ".x"
      },
      {
        "xxx", //
        ".x."
      },
      {
        "x.", //
        "xx", //
        "x."
      }
    }
  };

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int H = sc.nextInt();
      int N = sc.nextInt();
      int[] t = new int[N];
      int[] r = new int[N];
      int[] x = new int[N];
      for (int i = 0; i < N; ++i) {
        t[i] = sc.nextInt();
        r[i] = sc.nextInt();
        x[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(W, H, t, r, x)));
    }

    sc.close();
  }

  static String solve(int W, int H, int[] t, int[] r, int[] x) {
    char[][] field = new char[H][W];
    for (int i = 0; i < H; ++i) {
      Arrays.fill(field[i], '.');
    }

    for (int i = 0; i < t.length; ++i) {
      if (!place(field, TETROMINOS[t[i]][r[i]], x[i])) {
        return "Game Over!";
      }
    }

    return Arrays.stream(field).map(String::new).collect(Collectors.joining("\n"));
  }

  static boolean place(char[][] field, String[] tetromino, int xi) {
    int H = field.length;
    int W = field[0].length;

    int boxRow = tetromino.length;
    int boxCol = tetromino[0].length();

    int dr = -boxRow;
    while (dr + boxRow != H && canFit(field, tetromino, xi, dr + 1)) {
      ++dr;
    }
    if (dr < 0) {
      return false;
    }

    for (int r = 0; r < boxRow; ++r) {
      for (int c = 0; c < boxCol; ++c) {
        if (tetromino[r].charAt(c) == 'x') {
          field[r + dr][c + xi] = 'x';
        }
      }
    }

    int r = H - 1;
    while (r != -1) {
      int r_ = r;
      if (IntStream.range(0, W).allMatch(c -> field[r_][c] == 'x')) {
        for (int i = r; i >= 1; --i) {
          field[i] = Arrays.copyOf(field[i - 1], W);
        }
        Arrays.fill(field[0], '.');
      } else {
        --r;
      }
    }

    return true;
  }

  static boolean canFit(char[][] field, String[] tetromino, int xi, int nextDr) {
    int boxRow = tetromino.length;
    int boxCol = tetromino[0].length();

    return IntStream.range(0, boxRow)
        .allMatch(
            r ->
                IntStream.range(0, boxCol)
                    .allMatch(
                        c ->
                            tetromino[r].charAt(c) == '.'
                                || r + nextDr < 0
                                || field[r + nextDr][c + xi] == '.'));
  }
}
