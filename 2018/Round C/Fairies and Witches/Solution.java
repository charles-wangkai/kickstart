import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[][] L = new int[N][N];
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
          L[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L)));
    }

    sc.close();
  }

  static int solve(int[][] L) {
    return search(L, new HashSet<>(), 0, 0, 0);
  }

  static int search(int[][] L, Set<Integer> used, int r, int lengthSum, int lengthMax) {
    if (r == L.length - 1) {
      return (lengthSum - lengthMax > lengthMax) ? 1 : 0;
    }

    int result = search(L, used, r + 1, lengthSum, lengthMax);
    if (!used.contains(r)) {
      used.add(r);
      for (int c = r + 1; c < L.length; ++c) {
        if (!used.contains(c) && L[r][c] != 0) {
          used.add(c);
          result += search(L, used, r + 1, lengthSum + L[r][c], Math.max(lengthMax, L[r][c]));
          used.remove(c);
        }
      }
      used.remove(r);
    }

    return result;
  }
}
