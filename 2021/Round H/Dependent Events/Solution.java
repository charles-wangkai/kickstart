// https://www.geeksforgeeks.org/lca-in-a-tree-using-binary-lifting-technique/

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  static final int MODULUS = 1_000_000_007;
  static final int DENOMINATOR = 1_000_000;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int K = sc.nextInt();
      int[] P = new int[N - 1];
      int[] A = new int[N - 1];
      int[] B = new int[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        P[i] = sc.nextInt() - 1;
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }
      int[] u = new int[Q];
      int[] v = new int[Q];
      for (int i = 0; i < Q; ++i) {
        u[i] = sc.nextInt() - 1;
        v[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(K, P, A, B, u, v)));
    }

    sc.close();
  }

  static String solve(int K, int[] P, int[] A, int[] B, int[] u, int[] v) {
    int N = P.length + 1;

    @SuppressWarnings("unchecked")
    List<Integer>[] childLists = new List[N];
    for (int i = 0; i < childLists.length; ++i) {
      childLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < P.length; ++i) {
      childLists[P[i]].add(i + 1);
    }

    int[] independentAncestors = new int[N];
    int[] levels = new int[N];
    int[] probs = new int[N];
    probs[0] = multiplyMod(K, modInv(DENOMINATOR));
    int[] baseSums = new int[N];
    baseSums[0] = probs[0];
    int[] diffProducts = new int[N];
    diffProducts[0] = 1;
    for (int child : childLists[0]) {
      search(
          A, B, childLists, independentAncestors, levels, probs, baseSums, diffProducts, 0, child);
    }

    int[][] memos = buildMemos(P);

    return IntStream.range(0, u.length)
        .map(
            i -> {
              int lca = findLCA(levels, memos, u[i], v[i]);

              if (levels[independentAncestors[u[i]]] > levels[lca]
                  || levels[independentAncestors[v[i]]] > levels[lca]) {
                return multiplyMod(probs[u[i]], probs[v[i]]);
              }

              return addMod(
                  multiplyMod(
                      probs[lca],
                      multiplyMod(
                          computeConditionalProb(
                              independentAncestors, probs, baseSums, diffProducts, lca, u[i], 1),
                          computeConditionalProb(
                              independentAncestors, probs, baseSums, diffProducts, lca, v[i], 1))),
                  multiplyMod(
                      addMod(1, -probs[lca]),
                      multiplyMod(
                          computeConditionalProb(
                              independentAncestors, probs, baseSums, diffProducts, lca, u[i], 0),
                          computeConditionalProb(
                              independentAncestors, probs, baseSums, diffProducts, lca, v[i], 0))));
            })
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  static int computeConditionalProb(
      int[] independentAncestors,
      int[] probs,
      int[] baseSums,
      int[] diffProducts,
      int lca,
      int node,
      int p) {
    int rangeDiffProduct = multiplyMod(diffProducts[node], modInv(diffProducts[lca]));

    return addMod(
        addMod(baseSums[node], multiplyMod(-baseSums[lca], rangeDiffProduct)),
        multiplyMod(p, rangeDiffProduct));
  }

  static int findLCA(int[] levels, int[][] memos, int node1, int node2) {
    if (levels[node1] < levels[node2]) {
      return findLCA(levels, memos, node2, node1);
    }

    for (int i = memos[0].length - 1; i >= 0; --i) {
      if ((levels[node1] - (1 << i)) >= levels[node2]) {
        node1 = memos[node1][i];
      }
    }

    if (node1 == node2) {
      return node1;
    }

    for (int i = memos[0].length - 1; i >= 0; --i) {
      if (memos[node1][i] != memos[node2][i]) {
        node1 = memos[node1][i];
        node2 = memos[node2][i];
      }
    }

    return memos[node1][0];
  }

  static int[][] buildMemos(int[] P) {
    int N = P.length + 1;

    int logN = Integer.toBinaryString(N).length();
    int[][] memos = new int[N][logN + 1];
    for (int j = 0; j <= logN; ++j) {
      for (int i = 0; i < N; ++i) {
        memos[i][j] = (j == 0) ? ((i == 0) ? 0 : P[i - 1]) : memos[memos[i][j - 1]][j - 1];
      }
    }

    return memos;
  }

  static void search(
      int[] A,
      int[] B,
      List<Integer>[] childLists,
      int[] independentAncestors,
      int[] levels,
      int[] probs,
      int[] baseSums,
      int[] diffProducts,
      int parent,
      int node) {
    independentAncestors[node] = (A[node - 1] == B[node - 1]) ? node : independentAncestors[parent];

    levels[node] = levels[parent] + 1;

    probs[node] =
        addMod(
            multiplyMod(probs[parent], multiplyMod(A[node - 1], modInv(DENOMINATOR))),
            multiplyMod(addMod(1, -probs[parent]), multiplyMod(B[node - 1], modInv(DENOMINATOR))));

    baseSums[node] =
        (A[node - 1] == B[node - 1])
            ? multiplyMod(B[node - 1], modInv(DENOMINATOR))
            : addMod(
                multiplyMod(
                    baseSums[parent], multiplyMod(A[node - 1] - B[node - 1], modInv(DENOMINATOR))),
                multiplyMod(B[node - 1], modInv(DENOMINATOR)));

    diffProducts[node] =
        (A[node - 1] == B[node - 1])
            ? 1
            : multiplyMod(
                diffProducts[parent], multiplyMod(A[node - 1] - B[node - 1], modInv(DENOMINATOR)));

    for (int child : childLists[node]) {
      search(
          A,
          B,
          childLists,
          independentAncestors,
          levels,
          probs,
          baseSums,
          diffProducts,
          node,
          child);
    }
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }

  static int modInv(int x) {
    return BigInteger.valueOf(x).modInverse(BigInteger.valueOf(MODULUS)).intValue();
  }
}