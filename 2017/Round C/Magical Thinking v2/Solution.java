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
    int N = S.length;
    int Q = A[0].length();

    int sameCount =
        (int) IntStream.range(0, Q).filter(i -> A[0].charAt(i) == A[1].charAt(i)).count();
    int diffCount = Q - sameCount;

    if (N == 1) {
      return (S[0] <= sameCount) ? (S[0] + diffCount) : (Q - S[0] + sameCount);
    }

    int bothSameCount =
        (int)
            IntStream.range(0, Q)
                .filter(i -> A[2].charAt(i) == A[0].charAt(i) && A[2].charAt(i) == A[1].charAt(i))
                .count();
    int bothDiffCount = sameCount - bothSameCount;

    int bothScore = (S[0] + S[1] - diffCount) / 2;
    int myBothScore =
        Math.min(bothScore, bothSameCount) + Math.min(sameCount - bothScore, bothDiffCount);

    int diffCount0 =
        (int)
            IntStream.range(0, Q)
                .filter(i -> A[2].charAt(i) == A[0].charAt(i) && A[2].charAt(i) != A[1].charAt(i))
                .count();
    int diffCount1 = diffCount - diffCount0;

    int diffCorrect0 = (diffCount + S[0] - S[1]) / 2;
    int diffCorrect1 = diffCount - diffCorrect0;

    return myBothScore + Math.min(diffCount0, diffCorrect0) + Math.min(diffCount1, diffCorrect1);
  }
}
