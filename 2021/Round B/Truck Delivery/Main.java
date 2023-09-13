// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000435a5b/000000000077a885#analysis

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main implements Runnable {
  static final int SIZE = 1 << 18;

  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 26).start();
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] X = new int[N - 1];
      int[] Y = new int[N - 1];
      int[] L = new int[N - 1];
      long[] A = new long[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        X[i] = sc.nextInt() - 1;
        Y[i] = sc.nextInt() - 1;
        L[i] = sc.nextInt();
        A[i] = sc.nextLong();
      }
      int[] C = new int[Q];
      int[] W = new int[Q];
      for (int i = 0; i < Q; ++i) {
        C[i] = sc.nextInt() - 1;
        W[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(X, Y, L, A, C, W)));
    }

    sc.close();
  }

  static String solve(int[] X, int[] Y, int[] L, long[] A, int[] C, int[] W) {
    int N = X.length + 1;

    long[] queryAnswers = new long[C.length];

    @SuppressWarnings("unchecked")
    List<Integer>[] queryIndexLists = new List[N];
    for (int i = 0; i < queryIndexLists.length; ++i) {
      queryIndexLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < C.length; ++i) {
      queryIndexLists[C[i]].add(i);
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] edgeIndexLists = new List[N];
    for (int i = 0; i < edgeIndexLists.length; ++i) {
      edgeIndexLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < X.length; ++i) {
      edgeIndexLists[X[i]].add(i);
      edgeIndexLists[Y[i]].add(i);
    }

    search(
        X,
        Y,
        L,
        A,
        C,
        W,
        queryAnswers,
        queryIndexLists,
        edgeIndexLists,
        new long[SIZE * 2 - 1],
        new boolean[N],
        0);

    return Arrays.stream(queryAnswers).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static void search(
      int[] X,
      int[] Y,
      int[] L,
      long[] A,
      int[] C,
      int[] W,
      long[] queryAnswers,
      List<Integer>[] queryIndexLists,
      List<Integer>[] edgeIndexLists,
      long[] segmentTree,
      boolean[] visited,
      int node) {
    visited[node] = true;

    for (int queryIndex : queryIndexLists[node]) {
      queryAnswers[queryIndex] = rangeQuery(segmentTree, 1, W[queryIndex], 0, 1, SIZE);
    }

    for (int edgeIndex : edgeIndexLists[node]) {
      int other = (node == X[edgeIndex]) ? Y[edgeIndex] : X[edgeIndex];
      if (!visited[other]) {
        update(segmentTree, segmentTree.length - (SIZE + 1 - L[edgeIndex]), A[edgeIndex]);
        search(
            X,
            Y,
            L,
            A,
            C,
            W,
            queryAnswers,
            queryIndexLists,
            edgeIndexLists,
            segmentTree,
            visited,
            other);
        update(segmentTree, segmentTree.length - (SIZE + 1 - L[edgeIndex]), 0);
      }
    }
  }

  static void update(long[] segmentTree, int index, long value) {
    segmentTree[index] = value;
    while (index != 0) {
      int parent = (index - 1) / 2;
      segmentTree[parent] = gcd(segmentTree[parent * 2 + 1], segmentTree[parent * 2 + 2]);

      index = parent;
    }
  }

  static long rangeQuery(
      long[] segmentTree, int targetLower, int targetUpper, int index, int lower, int upper) {
    if (targetLower == lower && targetUpper == upper) {
      return segmentTree[index];
    }

    int middle = (lower + upper) / 2;
    if (targetUpper <= middle) {
      return rangeQuery(segmentTree, targetLower, targetUpper, index * 2 + 1, lower, middle);
    } else if (targetLower >= middle + 1) {
      return rangeQuery(segmentTree, targetLower, targetUpper, index * 2 + 2, middle + 1, upper);
    }

    return gcd(
        rangeQuery(segmentTree, targetLower, middle, index * 2 + 1, lower, middle),
        rangeQuery(segmentTree, middle + 1, targetUpper, index * 2 + 2, middle + 1, upper));
  }

  static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }
}
