// https://codingcompetitions.withgoogle.com/kickstart/submissions/0000000000050e01/00000000004a6466

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] L = new int[Q];
      int[] R = new int[Q];
      for (int i = 0; i < Q; ++i) {
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, L, R)));
    }

    sc.close();
  }

  static int solve(int N, int[] L, int[] R) {
    int[] sortedIndices =
        IntStream.range(0, L.length)
            .boxed()
            .sorted(
                Comparator.comparing((Integer i) -> L[i])
                    .thenComparing(Comparator.comparing((Integer i) -> R[i]).reversed()))
            .mapToInt(x -> x)
            .toArray();

    int result = 0;
    int lower = 1;
    int upper = N;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(L, R, sortedIndices, middle)) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static boolean check(int[] L, int[] R, int[] sortedIndices, int k) {
    int[] lefts = Arrays.copyOf(L, L.length);
    for (int i = 0; i < sortedIndices.length; ++i) {
      int start = lefts[sortedIndices[i]];
      int allowedAfter = R[sortedIndices[i]] + 1;
      int uniqueNum = 0;
      for (int j = i + 1; j < sortedIndices.length; ++j) {
        if (R[sortedIndices[j]] <= R[sortedIndices[i]]) {
          if (L[sortedIndices[j]] > start) {
            uniqueNum += L[sortedIndices[j]] - start;
            if (uniqueNum >= k) {
              allowedAfter = L[sortedIndices[j]] - (uniqueNum - k);

              break;
            }
          }

          start = Math.max(start, R[sortedIndices[j]] + 1);
        }
      }

      if (uniqueNum < k) {
        uniqueNum += R[sortedIndices[i]] - start + 1;
        if (uniqueNum < k) {
          return false;
        }

        allowedAfter = R[sortedIndices[i]] + 1 - (uniqueNum - k);
      }

      for (int j = i + 1; j < sortedIndices.length; ++j) {
        if (L[sortedIndices[j]] >= allowedAfter) {
          break;
        }
        if (R[sortedIndices[j]] > R[sortedIndices[i]]) {
          lefts[sortedIndices[j]] = Math.max(lefts[sortedIndices[j]], R[sortedIndices[i]] + 1);
        }
      }
    }

    return true;
  }
}
