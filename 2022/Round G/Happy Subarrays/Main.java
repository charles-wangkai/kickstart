import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static long solve(int[] A) {
    int[] prefixSums = new int[A.length + 1];
    for (int i = 0; i < prefixSums.length - 1; ++i) {
      prefixSums[i] = ((i == 0) ? 0 : prefixSums[i - 1]) + A[i];
    }
    prefixSums[prefixSums.length - 1] = Integer.MIN_VALUE;

    long[] cumulatives = new long[A.length];
    for (int i = 0; i < cumulatives.length; ++i) {
      cumulatives[i] = ((i == 0) ? 0 : cumulatives[i - 1]) + prefixSums[i];
    }

    long result = 0;
    List<Integer> indices = new ArrayList<>();
    indices.add(prefixSums.length - 1);
    for (int i = A.length - 1; i >= 0; --i) {
      while (prefixSums[i] <= prefixSums[indices.get(indices.size() - 1)]) {
        indices.remove(indices.size() - 1);
      }
      indices.add(i);

      int endIndex = findEndIndex(prefixSums, indices, (i == 0) ? 0 : prefixSums[i - 1]);
      if (endIndex >= i) {
        result +=
            cumulatives[endIndex]
                - ((i == 0) ? 0 : cumulatives[i - 1])
                - ((i == 0) ? 0 : prefixSums[i - 1]) * (endIndex - i + 1L);
      }
    }

    return result;
  }

  static int findEndIndex(int[] prefixSums, List<Integer> indices, int target) {
    int index = -1;
    int lower = 0;
    int upper = indices.size() - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (prefixSums[indices.get(middle)] < target) {
        index = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return indices.get(index) - 1;
  }
}
