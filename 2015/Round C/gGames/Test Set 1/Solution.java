import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] E = new int[M];
      int[] K = new int[M];
      int[][] friends = new int[M][];
      for (int i = 0; i < M; ++i) {
        E[i] = sc.nextInt() - 1;
        K[i] = sc.nextInt();
        int B = sc.nextInt();
        friends[i] = new int[B];
        for (int j = 0; j < friends[i].length; ++j) {
          friends[i][j] = sc.nextInt() - 1;
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, E, K, friends) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int N, int[] E, int[] K, int[][] friends) {
    return search(N, E, K, friends, IntStream.range(0, 1 << N).toArray(), 0);
  }

  static boolean search(int N, int[] E, int[] K, int[][] friends, int[] permutation, int index) {
    if (index == permutation.length) {
      return check(N, E, K, friends, permutation);
    }

    for (int i = index; i < permutation.length; ++i) {
      swap(permutation, i, index);
      if (search(N, E, K, friends, permutation, index + 1)) {
        return true;
      }
      swap(permutation, i, index);
    }

    return false;
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  static boolean check(int N, int[] E, int[] K, int[][] friends, int[] permutation) {
    Map<Integer, Integer> valueToIndex =
        IntStream.range(0, permutation.length)
            .boxed()
            .collect(Collectors.toMap(i -> permutation[i], i -> i));

    for (int i = 0; i < E.length; ++i) {
      int mask = (1 << N) - (1 << K[i]);
      for (int friend : friends[i]) {
        if ((valueToIndex.get(E[i]) & mask) == (valueToIndex.get(friend) & mask)) {
          return false;
        }
      }
    }

    return true;
  }
}
