import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int V = sc.nextInt();
      int E = sc.nextInt();
      int[] A = new int[E];
      int[] B = new int[E];
      int[] L = new int[E];
      for (int i = 0; i < E; ++i) {
        A[i] = sc.nextInt() - 1;
        B[i] = sc.nextInt() - 1;
        L[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, A, B, L)));
    }

    sc.close();
  }

  static long solve(int V, int[] A, int[] B, int[] L) {
    int[] parents = new int[V];
    Arrays.fill(parents, -1);

    int[] sortedIndices =
        IntStream.range(0, L.length)
            .boxed()
            .sorted(Comparator.comparing(i -> L[i]))
            .mapToInt(x -> x)
            .toArray();
    boolean[] used = new boolean[V];
    List<Integer> chosenIndices = new ArrayList<>();
    for (int index : sortedIndices) {
      if (!used[A[index]] || !used[B[index]]) {
        int rootA = findRoot(parents, A[index]);
        int rootB = findRoot(parents, B[index]);
        if (rootA != rootB) {
          parents[rootB] = rootA;
          chosenIndices.add(index);

          used[A[index]] = true;
          used[B[index]] = true;
        }
      }
    }

    long result =
        1L << IntStream.range(0, parents.length).map(i -> findRoot(parents, i)).distinct().count();

    int firstIndex = chosenIndices.get(0);
    if (L[firstIndex] == 0) {
      for (int i = 1; i < chosenIndices.size(); ++i) {
        int index = chosenIndices.get(i);
        if (A[index] == A[firstIndex]
            || A[index] == B[firstIndex]
            || B[index] == A[firstIndex]
            || B[index] == B[firstIndex]) {
          result *= 2;
        }
      }
    }

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
