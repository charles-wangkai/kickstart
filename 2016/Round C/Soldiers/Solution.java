import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      int[] D = new int[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextInt();
        D[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, D) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] A, int[] D) {
    int[] restIndices = IntStream.range(0, A.length).toArray();
    while (restIndices.length != 0) {
      int maxA = Arrays.stream(restIndices).map(i -> A[i]).max().getAsInt();
      int maxD = Arrays.stream(restIndices).map(i -> D[i]).max().getAsInt();

      if (Arrays.stream(restIndices).anyMatch(i -> A[i] == maxA && D[i] == maxD)) {
        return true;
      }

      restIndices = Arrays.stream(restIndices).filter(i -> A[i] != maxA && D[i] != maxD).toArray();
    }

    return false;
  }
}
