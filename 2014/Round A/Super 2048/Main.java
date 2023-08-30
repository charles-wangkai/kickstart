import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String DIR = sc.next();
      int[][] board = new int[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          board[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(board, DIR)));
    }

    sc.close();
  }

  static String solve(int[][] board, String DIR) {
    int[][] result;
    if (DIR.equals("left")) {
      result = moveLeft(board);
    } else if (DIR.equals("right")) {
      result = moveRight(board);
    } else if (DIR.equals("up")) {
      result = moveUp(board);
    } else {
      result = moveDown(board);
    }

    return Arrays.stream(result)
        .map(line -> Arrays.stream(line).mapToObj(String::valueOf).collect(Collectors.joining(" ")))
        .collect(Collectors.joining("\n"));
  }

  static List<Integer> merge(Queue<Integer> values) {
    List<Integer> merged = new ArrayList<>();
    while (!values.isEmpty()) {
      int head = values.poll();
      if (!values.isEmpty() && values.peek() == head) {
        merged.add(head + values.poll());
      } else {
        merged.add(head);
      }
    }

    return merged;
  }

  static int[][] moveLeft(int[][] board) {
    int N = board.length;

    int[][] result = new int[N][N];
    for (int r = 0; r < N; ++r) {
      Queue<Integer> values = new ArrayDeque<>();
      for (int c = 0; c < N; ++c) {
        if (board[r][c] != 0) {
          values.offer(board[r][c]);
        }
      }

      List<Integer> merged = merge(values);

      for (int i = 0; i < merged.size(); ++i) {
        result[r][i] = merged.get(i);
      }
    }

    return result;
  }

  static int[][] moveRight(int[][] board) {
    int N = board.length;

    int[][] result = new int[N][N];
    for (int r = 0; r < N; ++r) {
      Queue<Integer> values = new ArrayDeque<>();
      for (int c = N - 1; c >= 0; --c) {
        if (board[r][c] != 0) {
          values.offer(board[r][c]);
        }
      }

      List<Integer> merged = merge(values);

      for (int i = 0; i < merged.size(); ++i) {
        result[r][N - 1 - i] = merged.get(i);
      }
    }

    return result;
  }

  static int[][] moveUp(int[][] board) {
    int N = board.length;

    int[][] result = new int[N][N];
    for (int c = 0; c < N; ++c) {
      Queue<Integer> values = new ArrayDeque<>();
      for (int r = 0; r < N; ++r) {
        if (board[r][c] != 0) {
          values.offer(board[r][c]);
        }
      }

      List<Integer> merged = merge(values);

      for (int i = 0; i < merged.size(); ++i) {
        result[i][c] = merged.get(i);
      }
    }

    return result;
  }

  static int[][] moveDown(int[][] board) {
    int N = board.length;

    int[][] result = new int[N][N];
    for (int c = 0; c < N; ++c) {
      Queue<Integer> values = new ArrayDeque<>();
      for (int r = N - 1; r >= 0; --r) {
        if (board[r][c] != 0) {
          values.offer(board[r][c]);
        }
      }

      List<Integer> merged = merge(values);

      for (int i = 0; i < merged.size(); ++i) {
        result[N - 1 - i][c] = merged.get(i);
      }
    }

    return result;
  }
}
