import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] D = new int[N - 1];
      for (int i = 0; i < D.length; ++i) {
        D[i] = sc.nextInt();
      }
      int[] S = new int[Q];
      int[] K = new int[Q];
      for (int i = 0; i < Q; ++i) {
        S[i] = sc.nextInt() - 1;
        K[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(D, S, K)));
    }

    sc.close();
  }

  static String solve(int[] D, int[] S, int[] K) {
    return IntStream.range(0, S.length)
        .mapToObj(i -> String.valueOf(findRoom(D, S[i], K[i])))
        .collect(Collectors.joining(" "));
  }

  static int findRoom(int[] D, int start, int k) {
    int lastRoom = start;
    int leftIndex = start - 1;
    int rightIndex = start;
    for (int i = 0; i < k - 1; ++i) {
      if (rightIndex == D.length || (leftIndex != -1 && D[leftIndex] < D[rightIndex])) {
        lastRoom = leftIndex;
        --leftIndex;
      } else {
        lastRoom = rightIndex + 1;
        ++rightIndex;
      }
    }

    return lastRoom + 1;
  }
}