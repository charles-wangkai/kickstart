import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] L = new int[N];
      for (int i = 0; i < L.length; ++i) {
        L[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L)));
    }

    sc.close();
  }

  static int solve(int[] L) {
    int result = 0;
    for (int i = 0; i < L.length; ++i) {
      for (int j = i + 1; j < L.length; ++j) {
        for (int k = j + 1; k < L.length; ++k) {
          for (int p = k + 1; p < L.length; ++p) {
            if (check(new int[] {L[i], L[j], L[k], L[p]})) {
              ++result;
            }
          }
        }
      }
    }

    return result;
  }

  static boolean check(int[] lengths) {
    Arrays.sort(lengths);

    if (lengths[0] == lengths[1] && lengths[2] == lengths[3]) {
      return false;
    }

    if (lengths[0] == lengths[1]) {
      return isPossible(lengths[0], lengths[2], lengths[3]);
    }
    if (lengths[1] == lengths[2]) {
      return isPossible(lengths[1], lengths[0], lengths[3]);
    }
    if (lengths[2] == lengths[3]) {
      return isPossible(lengths[2], lengths[0], lengths[1]);
    }

    return false;
  }

  static boolean isPossible(int side, int top, int down) {
    return down < (long) top + side + side;
  }
}
