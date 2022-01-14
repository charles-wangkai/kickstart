import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] C = new int[M];
      int[] D = new int[M];
      for (int i = 0; i < M; ++i) {
        C[i] = sc.nextInt() - 1;
        D[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, C, D)));
    }

    sc.close();
  }

  static int solve(int N, int[] C, int[] D) {
    int[] parents = new int[N];
    Arrays.fill(parents, -1);

    int result = 0;
    for (int i = 0; i < C.length; ++i) {
      int root1 = findRoot(parents, C[i]);
      int root2 = findRoot(parents, D[i]);
      if (root1 != root2) {
        parents[root2] = root1;
        ++result;
      }
    }
    result += 2 * (IntStream.range(0, N).map(i -> findRoot(parents, i)).distinct().count() - 1);

    return result;
  }

  static int findRoot(int[] parents, int node) {
    int root = node;
    while (parents[root] != -1) {
      root = parents[root];
    }

    int p = node;
    while (p != root) {
      int next = parents[p];
      parents[p] = root;

      p = next;
    }

    return root;
  }
}