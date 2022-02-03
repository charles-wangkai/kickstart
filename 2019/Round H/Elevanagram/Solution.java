// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edd/00000000001a286d#analysis

import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int[] A = new int[9];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] A) {
    int[] sortedIndices =
        IntStream.range(0, A.length)
            .boxed()
            .sorted(Comparator.comparing(i -> A[i]))
            .mapToInt(x -> x)
            .toArray();
    if (A[sortedIndices[sortedIndices.length - 2]] >= 10
        || A[sortedIndices[sortedIndices.length - 3]] >= 6) {
      return true;
    }

    return search(A, sortedIndices, 0, 0, 0);
  }

  static boolean search(int[] A, int[] sortedIndices, int index, int countDiff, int sumDiff) {
    if (index == sortedIndices.length) {
      return sumDiff % 11 == 0;
    }

    if (index == sortedIndices.length - 1) {
      if (A[sortedIndices[index]] % 2 == Math.floorMod(countDiff, 2)) {
        return A[sortedIndices[index]] >= Math.abs(countDiff)
            && search(
                A, sortedIndices, index + 1, 0, sumDiff - (sortedIndices[index] + 1) * countDiff);
      } else {
        return (A[sortedIndices[index]] >= Math.abs(countDiff) - 1
                && search(
                    A,
                    sortedIndices,
                    index + 1,
                    1,
                    sumDiff - (sortedIndices[index] + 1) * (countDiff - 1)))
            || (A[sortedIndices[index]] >= Math.abs(countDiff) + 1
                && search(
                    A,
                    sortedIndices,
                    index + 1,
                    -1,
                    sumDiff - (sortedIndices[index] + 1) * (countDiff + 1)));
      }
    }

    for (int i = 0; i <= A[sortedIndices[index]]; ++i) {
      if (search(
          A,
          sortedIndices,
          index + 1,
          countDiff + (i - (A[sortedIndices[index]] - i)),
          sumDiff + (sortedIndices[index] + 1) * (i - (A[sortedIndices[index]] - i)))) {
        return true;
      }
    }

    return false;
  }
}
