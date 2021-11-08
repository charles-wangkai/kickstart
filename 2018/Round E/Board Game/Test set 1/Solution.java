import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  static final int BATTLEFIELD_NUM = 3;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[3 * N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[3 * N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N, A, B)));
    }

    sc.close();
  }

  static double solve(int N, int[] A, int[] B) {
    List<int[]> aSumArrays = buildSumArrays(N, A);
    List<int[]> bSumArrays = buildSumArrays(N, B);

    double result = 0;
    for (int[] aSumArray : aSumArrays) {
      int winCount = 0;
      for (int[] bSumArray : bSumArrays) {
        if (isWin(aSumArray, bSumArray)) {
          ++winCount;
        }
      }

      result = Math.max(result, (double) winCount / bSumArrays.size());
    }

    return result;
  }

  static boolean isWin(int[] aSumArray, int[] bSumArray) {
    int count = 0;
    for (int i = 0; i < aSumArray.length; ++i) {
      if (aSumArray[i] > bSumArray[i]) {
        ++count;
      }
    }

    return count >= 2;
  }

  static List<int[]> buildSumArrays(int N, int[] values) {
    List<int[]> sumArrays = new ArrayList<>();
    search(sumArrays, N, values, IntStream.range(0, values.length).toArray(), 0);

    return sumArrays;
  }

  static void search(List<int[]> sumArrays, int N, int[] values, int[] indices, int depth) {
    if (depth == indices.length) {
      int[] sumArray = new int[BATTLEFIELD_NUM];
      for (int i = 0; i < sumArray.length; ++i) {
        for (int j = 0; j < N; ++j) {
          sumArray[i] += values[indices[i * BATTLEFIELD_NUM + j]];
        }
      }

      sumArrays.add(sumArray);

      return;
    }

    for (int i = depth; i < indices.length; ++i) {
      if (depth % N == 0 || indices[i] >= indices[depth - 1]) {
        swap(indices, i, depth);
        search(sumArrays, N, values, indices, depth + 1);
        swap(indices, i, depth);
      }
    }
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }
}
