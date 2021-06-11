import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      sc.nextInt();
      String[] A = new String[N + 1];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.next();
      }
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, S)));
    }

    sc.close();
  }

  static int solve(String[] A, int[] S) {
    int Q = A[0].length();

    int sameCount =
        (int) IntStream.range(0, Q).filter(i -> A[0].charAt(i) == A[1].charAt(i)).count();
    int diffCount = Q - sameCount;

    if (S[0] <= sameCount) {
      return S[0] + diffCount;
    }

    return Q - S[0] + sameCount;
  }
}
