import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[][] A = new int[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          A[r][c] = sc.nextInt();
        }
      }
      int[][] B = new int[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          B[r][c] = sc.nextInt();
        }
      }
      int[] R = new int[N];
      for (int i = 0; i < R.length; ++i) {
        R[i] = sc.nextInt();
      }
      int[] C = new int[N];
      for (int i = 0; i < C.length; ++i) {
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, R, C)));
    }

    sc.close();
  }

  static int solve(int[][] A, int[][] B, int[] R, int[] C) {
    int N = A.length;

    int result = Integer.MAX_VALUE;
    for (int mask = 0; mask < 1 << (N * N); ++mask) {
      boolean[][] sures = new boolean[N][N];
      for (int r = 0; r < N; ++r) {
        for (int c = 0; c < N; ++c) {
          sures[r][c] = (mask & (1 << (r * N + c))) != 0;
        }
      }

      if (check(sures)) {
        int cost = 0;
        for (int r = 0; r < N; ++r) {
          for (int c = 0; c < N; ++c) {
            if (sures[r][c]) {
              cost += B[r][c];
            }
          }
        }

        result = Math.min(result, cost);
      }
    }

    return result;
  }

  static boolean check(boolean[][] sures) {
    int N = sures.length;

    @SuppressWarnings("unchecked")
    Set<Integer>[] rowPendings = new Set[N];
    for (int i = 0; i < rowPendings.length; ++i) {
      rowPendings[i] = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    Set<Integer>[] colPendings = new Set[N];
    for (int i = 0; i < colPendings.length; ++i) {
      colPendings[i] = new HashSet<>();
    }

    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (!sures[r][c]) {
          rowPendings[r].add(c);
          colPendings[c].add(r);
        }
      }
    }

    Queue<Element> queue = new ArrayDeque<>();
    for (int i = 0; i < rowPendings.length; ++i) {
      if (rowPendings[i].size() == 1) {
        queue.offer(new Element(true, i));
      }
    }
    for (int i = 0; i < colPendings.length; ++i) {
      if (colPendings[i].size() == 1) {
        queue.offer(new Element(false, i));
      }
    }

    while (!queue.isEmpty()) {
      Element head = queue.poll();
      if (head.rowOrCol) {
        if (rowPendings[head.index].size() == 1) {
          int c = rowPendings[head.index].iterator().next();
          rowPendings[head.index].clear();

          colPendings[c].remove(head.index);
          if (colPendings[c].size() == 1) {
            queue.offer(new Element(false, c));
          }
        }
      } else {
        if (colPendings[head.index].size() == 1) {
          int r = colPendings[head.index].iterator().next();
          colPendings[head.index].clear();

          rowPendings[r].remove(head.index);
          if (rowPendings[r].size() == 1) {
            queue.offer(new Element(true, r));
          }
        }
      }
    }

    return IntStream.range(0, N)
        .allMatch(i -> rowPendings[i].isEmpty() && colPendings[i].isEmpty());
  }
}

class Element {
  boolean rowOrCol;
  int index;

  Element(boolean rowOrCol, int index) {
    this.rowOrCol = rowOrCol;
    this.index = index;
  }
}