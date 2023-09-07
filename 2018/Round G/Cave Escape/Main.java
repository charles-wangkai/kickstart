// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000051066/0000000000051135#analysis

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  static final int OBSTACLE = -100000;
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int E = sc.nextInt();
      int Sr = sc.nextInt() - 1;
      int Sc = sc.nextInt() - 1;
      int Tr = sc.nextInt() - 1;
      int Tc = sc.nextInt() - 1;
      int[][] V = new int[N][M];
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
          V[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, E, Sr, Sc, Tr, Tc)));
    }

    sc.close();
  }

  static int solve(int[][] V, int E, int Sr, int Sc, int Tr, int Tc) {
    int N = V.length;
    int M = V[0].length;

    int[] parents = new int[N * M];
    Arrays.fill(parents, -1);

    int[] strengthSums = new int[N * M];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        strengthSums[r * M + c] = V[r][c];
      }
    }
    strengthSums[Sr * M + Sc] += E;

    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        if (V[r][c] >= 0) {
          for (int i = 0; i < R_OFFSETS.length; ++i) {
            int adjR = r + R_OFFSETS[i];
            int adjC = c + C_OFFSETS[i];
            if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < M && V[adjR][adjC] >= 0) {
              int root1 = findRoot(parents, r * M + c);
              int root2 = findRoot(parents, adjR * M + adjC);
              if (root1 != root2) {
                parents[root2] = root1;
                strengthSums[root1] += strengthSums[root2];
              }
            }
          }
        }
      }
    }

    Map<Integer, Integer> rootToIndex = new HashMap<>();
    for (int i = 0; i < parents.length; ++i) {
      if (strengthSums[i] != OBSTACLE) {
        int root = findRoot(parents, i);
        if (!rootToIndex.containsKey(root)) {
          rootToIndex.put(root, rootToIndex.size());
        }
      }
    }
    int sourceIndex = rootToIndex.get(findRoot(parents, Sr * M + Sc));
    int targetIndex = rootToIndex.get(findRoot(parents, Tr * M + Tc));

    int[] strengths = new int[rootToIndex.size()];
    for (int root : rootToIndex.keySet()) {
      strengths[rootToIndex.get(root)] = strengthSums[root];
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[rootToIndex.size()];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        if (V[r][c] != OBSTACLE) {
          for (int i = 0; i < R_OFFSETS.length; ++i) {
            int adjR = r + R_OFFSETS[i];
            int adjC = c + C_OFFSETS[i];
            if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < M && V[adjR][adjC] != OBSTACLE) {
              int index1 = rootToIndex.get(findRoot(parents, r * M + c));
              int index2 = rootToIndex.get(findRoot(parents, adjR * M + adjC));
              if (index1 < index2) {
                adjLists[index1].add(index2);
                adjLists[index2].add(index1);
              }
            }
          }
        }
      }
    }

    Map<Integer, Integer> trapToIndex = new HashMap<>();
    for (int i = 0; i < strengths.length; ++i) {
      if (strengths[i] < 0) {
        trapToIndex.put(i, trapToIndex.size());
      }
    }

    int maskSize = 1 << trapToIndex.size();

    int[] energies = new int[maskSize];
    @SuppressWarnings("unchecked")
    List<Integer>[] nextTrapLists = new List[maskSize];
    for (int i = 0; i < nextTrapLists.length; ++i) {
      nextTrapLists[i] = new ArrayList<>();
    }
    boolean[] exits = new boolean[maskSize];
    for (int mask = 0; mask < maskSize; ++mask) {
      search(
          adjLists,
          strengths,
          trapToIndex,
          energies,
          nextTrapLists,
          exits,
          targetIndex,
          mask,
          new boolean[rootToIndex.size()],
          sourceIndex);
    }

    return computeMaxEnergy(
        new HashMap<>(), strengths, trapToIndex, energies, nextTrapLists, exits, 0);
  }

  static int computeMaxEnergy(
      Map<Integer, Integer> cache,
      int[] strengths,
      Map<Integer, Integer> trapToIndex,
      int[] energies,
      List<Integer>[] nextTrapLists,
      boolean[] exits,
      int mask) {
    if (!cache.containsKey(mask)) {
      int result = -1;

      if (exits[mask]) {
        result = energies[mask];
      }

      for (int nextTrap : nextTrapLists[mask]) {
        if (energies[mask] + strengths[nextTrap] >= 0) {
          result =
              Math.max(
                  result,
                  computeMaxEnergy(
                      cache,
                      strengths,
                      trapToIndex,
                      energies,
                      nextTrapLists,
                      exits,
                      mask | (1 << trapToIndex.get(nextTrap))));
        }
      }

      cache.put(mask, result);
    }

    return cache.get(mask);
  }

  static void search(
      List<Integer>[] adjLists,
      int[] strengths,
      Map<Integer, Integer> trapToIndex,
      int[] energies,
      List<Integer>[] nextTrapLists,
      boolean[] exits,
      int targetIndex,
      int mask,
      boolean[] visited,
      int v) {
    visited[v] = true;

    if (v == targetIndex) {
      exits[mask] = true;
    }

    if (strengths[v] < 0 && (mask & (1 << trapToIndex.get(v))) == 0) {
      nextTrapLists[mask].add(v);

      return;
    }

    energies[mask] += strengths[v];

    for (int adj : adjLists[v]) {
      if (!visited[adj]) {
        search(
            adjLists,
            strengths,
            trapToIndex,
            energies,
            nextTrapLists,
            exits,
            targetIndex,
            mask,
            visited,
            adj);
      }
    }
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
