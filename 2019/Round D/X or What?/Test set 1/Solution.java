import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] P = new int[Q];
      int[] V = new int[Q];
      for (int i = 0; i < Q; ++i) {
        P[i] = sc.nextInt();
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, P, V)));
    }

    sc.close();
  }

  static String solve(int[] A, int[] P, int[] V) {
    int[] result = new int[P.length];
    for (int i = 0; i < P.length; ++i) {
      A[P[i]] = V[i];

      for (int beginIndex = 0; beginIndex < A.length; ++beginIndex) {
        int xor = 0;
        for (int endIndex = beginIndex; endIndex < A.length; ++endIndex) {
          xor ^= A[endIndex];

          if (Integer.bitCount(xor) % 2 == 0) {
            result[i] = Math.max(result[i], endIndex - beginIndex + 1);
          }
        }
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}
