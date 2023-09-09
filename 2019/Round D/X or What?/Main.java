import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {
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
    SortedSet<Integer> oddIndices = new TreeSet<>();
    for (int i = 0; i < A.length; ++i) {
      if (Integer.bitCount(A[i]) % 2 != 0) {
        oddIndices.add(i);
      }
    }

    int[] result = new int[P.length];
    for (int i = 0; i < result.length; ++i) {
      oddIndices.remove(P[i]);
      if (Integer.bitCount(V[i]) % 2 != 0) {
        oddIndices.add(P[i]);
      }

      if (oddIndices.size() % 2 == 0) {
        result[i] = A.length;
      } else {
        result[i] = Math.max(A.length - (oddIndices.first() + 1), oddIndices.last());
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}
