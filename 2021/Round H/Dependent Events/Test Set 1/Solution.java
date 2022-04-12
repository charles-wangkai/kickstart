import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  static final int MODULUS = 1_000_000_007;
  static final int DENOMINATOR_INV =
      BigInteger.valueOf(1_000_000).modInverse(BigInteger.valueOf(MODULUS)).intValue();

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

    int[][] probs = new int[N][2];
    probs[0][1] = multiplyMod(K, DENOMINATOR_INV);
    for (int i = 1; i < probs.length; ++i) {
      probs[i][0] = multiplyMod(B[i - 1], DENOMINATOR_INV);
      probs[i][1] = multiplyMod(A[i - 1], DENOMINATOR_INV);
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] childLists = new List[N];
    for (int i = 0; i < childLists.length; ++i) {
      childLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < P.length; ++i) {
      childLists[P[i]].add(i + 1);
    }

    return IntStream.range(0, u.length)
        .map(
            i -> {
              List<Integer> uPath = buildPath(childLists, new ArrayList<>(), 0, u[i]);
              List<Integer> vPath = buildPath(childLists, new ArrayList<>(), 0, v[i]);

              if (uPath.size() > vPath.size()) {
                List<Integer> temp = uPath;
                uPath = vPath;
                vPath = temp;
              }

              int beginIndex = 0;
              int[] p = {0, 1};
              while (beginIndex != uPath.size()
                  && uPath.get(beginIndex).equals(vPath.get(beginIndex))) {
                transfer(p, probs, uPath.get(beginIndex));

                ++beginIndex;
              }

              return addMod(
                  multiplyMod(
                      p[0],
                      multiplyMod(
                          computeProb(probs, uPath, beginIndex, new int[] {1, 0}),
                          computeProb(probs, vPath, beginIndex, new int[] {1, 0}))),
                  multiplyMod(
                      p[1],
                      multiplyMod(
                          computeProb(probs, uPath, beginIndex, new int[] {0, 1}),
                          computeProb(probs, vPath, beginIndex, new int[] {0, 1}))));
            })
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }

  static void transfer(int[] p, int[][] probs, int node) {
    p[1] = addMod(multiplyMod(p[1], probs[node][1]), multiplyMod(p[0], probs[node][0]));
    p[0] = 1 - p[1];
  }

  static int computeProb(int[][] probs, List<Integer> path, int beginIndex, int[] p) {
    for (int i = beginIndex; i < path.size(); ++i) {
      transfer(p, probs, path.get(i));
    }

    return p[1];
  }

  static List<Integer> buildPath(
      List<Integer>[] childLists, List<Integer> path, int node, int target) {
    path.add(node);
    if (node == target) {
      return path;
    }

    for (int child : childLists[node]) {
      List<Integer> subResult = buildPath(childLists, path, child, target);
      if (subResult != null) {
        return subResult;
      }
    }

    path.remove(path.size() - 1);

    return null;
  }
}