import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int S = sc.nextInt();
      int Ra = sc.nextInt() - 1;
      int Pa = sc.nextInt() - 1;
      int Rb = sc.nextInt() - 1;
      int Pb = sc.nextInt() - 1;
      int C = sc.nextInt();
      int[] R = new int[C];
      int[] P = new int[C];
      for (int i = 0; i < C; ++i) {
        R[i] = sc.nextInt() - 1;
        P[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, Ra, Pa, Rb, Pb, R, P)));
    }

    sc.close();
  }

  static int solve(int S, int Ra, int Pa, int Rb, int Pb, int[] R, int[] P) {
    boolean[][] visited = new boolean[S][];
    for (int i = 0; i < visited.length; ++i) {
      visited[i] = new boolean[i * 2 + 1];
    }
    visited[Ra][Pa] = true;
    visited[Rb][Pb] = true;
    for (int i = 0; i < R.length; ++i) {
      visited[R[i]][P[i]] = true;
    }

    return search(visited, Ra, Pa, Rb, Pb, true);
  }

  static int search(
      boolean[][] visited, int r, int p, int otherR, int otherP, boolean otherMovable) {
    int[] nextRs;
    int[] nextPs;
    if (p % 2 == 0) {
      nextRs = new int[] {r, r, r + 1};
      nextPs = new int[] {p - 1, p + 1, p + 1};
    } else {
      nextRs = new int[] {r - 1, r, r};
      nextPs = new int[] {p - 1, p - 1, p + 1};
    }

    int result = Integer.MIN_VALUE;
    for (int i = 0; i < nextRs.length; ++i) {
      if (nextRs[i] >= 0
          && nextRs[i] < visited.length
          && nextPs[i] >= 0
          && nextPs[i] < visited[nextRs[i]].length
          && !visited[nextRs[i]][nextPs[i]]) {
        visited[nextRs[i]][nextPs[i]] = true;
        result = Math.max(result, 1 - search(visited, otherR, otherP, nextRs[i], nextPs[i], true));
        visited[nextRs[i]][nextPs[i]] = false;
      }
    }
    if (result != Integer.MIN_VALUE) {
      return result;
    }

    return otherMovable ? -search(visited, otherR, otherP, r, p, false) : 0;
  }
}