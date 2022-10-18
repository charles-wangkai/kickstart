import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int E = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      int[] C = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, Y, C, E)));
    }

    sc.close();
  }

  static long solve(int[] X, int[] Y, int[] C, int E) {
    return search(X, Y, C, E, IntStream.range(0, X.length).toArray(), 0);
  }

  static long search(int[] X, int[] Y, int[] C, int E, int[] indices, int index) {
    if (index == indices.length) {
      return computeMaxEnergy(X, Y, C, E, indices);
    }

    long result = 0;
    for (int i = index; i < indices.length; ++i) {
      swap(indices, i, index);
      result = Math.max(result, search(X, Y, C, E, indices, index + 1));
      swap(indices, i, index);
    }

    return result;
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  static long computeMaxEnergy(int[] X, int[] Y, int[] C, int E, int[] indices) {
    long result = 0;
    long energy = 0;
    boolean rightOrLeft = true;
    for (int i = 0; i < indices.length && (i == 0 || Y[indices[i]] <= Y[indices[i - 1]]); ++i) {
      int xDiff = X[indices[i]] - ((i == 0) ? 0 : X[indices[i - 1]]);

      if (rightOrLeft) {
        if (xDiff < 0) {
          energy -= E;
          rightOrLeft = false;
        }
      } else if (xDiff > 0) {
        energy -= E;
        rightOrLeft = true;
      }

      energy += C[indices[i]];
      result = Math.max(result, energy);
    }

    return result;
  }
}
