// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000436140/000000000068c2c3#analysis

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int T = Integer.parseInt(st.nextToken());
    for (int tc = 1; tc <= T; ++tc) {
      st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int[][] A = new int[N][N];
      for (int r = 0; r < N; ++r) {
        st = new StringTokenizer(br.readLine());
        for (int c = 0; c < N; ++c) {
          A[r][c] = Integer.parseInt(st.nextToken());
        }
      }
      int[][] B = new int[N][N];
      for (int r = 0; r < N; ++r) {
        st = new StringTokenizer(br.readLine());
        for (int c = 0; c < N; ++c) {
          B[r][c] = Integer.parseInt(st.nextToken());
        }
      }
      st = new StringTokenizer(br.readLine());
      int[] R = new int[N];
      for (int i = 0; i < R.length; ++i) {
        R[i] = Integer.parseInt(st.nextToken());
      }
      st = new StringTokenizer(br.readLine());
      int[] C = new int[N];
      for (int i = 0; i < C.length; ++i) {
        C[i] = Integer.parseInt(st.nextToken());
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, R, C)));
    }
  }

  static int solve(int[][] A, int[][] B, int[] R, int[] C) {
    int N = A.length;

    int[] sortedIndices =
        IntStream.range(0, N * N)
            .filter(i -> B[i / N][i % N] != 0)
            .boxed()
            .sorted(Comparator.<Integer, Integer>comparing(i -> B[i / N][i % N]).reversed())
            .mapToInt(Integer::intValue)
            .toArray();

    int costSum = 0;
    int[] parents = new int[2 * N];
    Arrays.fill(parents, -1);
    for (int index : sortedIndices) {
      int root1 = findRoot(parents, index / N);
      int root2 = findRoot(parents, index % N + N);
      if (root1 != root2) {
        parents[root2] = root1;
        costSum += B[index / N][index % N];
      }
    }

    return Arrays.stream(B).mapToInt(line -> Arrays.stream(line).sum()).sum() - costSum;
  }

  static int findRoot(int[] parents, int v) {
    if (parents[v] == -1) {
      return v;
    }

    parents[v] = findRoot(parents, parents[v]);

    return parents[v];
  }
}
