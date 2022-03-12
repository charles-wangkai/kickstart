import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int[][] G = new int[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          G[r][c] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(G)));
    }

    sc.close();
  }

  static long solve(int[][] G) {
    int R = G.length;
    int C = G[0].length;

    long result = 0;
    boolean[][] visited = new boolean[R][C];
    PriorityQueue<Element> pq =
        new PriorityQueue<>(Comparator.comparing((Element e) -> e.height).reversed());
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        pq.offer(new Element(r, c, G[r][c]));
      }
    }
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (!visited[head.r][head.c]) {
        visited[head.r][head.c] = true;
        result += head.height - G[head.r][head.c];

        for (int i = 0; i < R_OFFSETS.length; ++i) {
          int adjR = head.r + R_OFFSETS[i];
          int adjC = head.c + C_OFFSETS[i];
          if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C && !visited[adjR][adjC]) {
            pq.offer(new Element(adjR, adjC, head.height - 1));
          }
        }
      }
    }

    return result;
  }
}

class Element {
  int r;
  int c;
  int height;

  Element(int r, int c, int height) {
    this.r = r;
    this.c = c;
    this.height = height;
  }
}