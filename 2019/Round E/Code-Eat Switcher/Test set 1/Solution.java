import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int D = sc.nextInt();
      int S = sc.nextInt();
      int[] C = new int[S];
      int[] E = new int[S];
      for (int i = 0; i < S; ++i) {
        C[i] = sc.nextInt();
        E[i] = sc.nextInt();
      }
      int[] A = new int[D];
      int[] B = new int[D];
      for (int i = 0; i < D; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(C, E, A, B)));
    }

    sc.close();
  }

  static String solve(int[] C, int[] E, int[] A, int[] B) {
    int S = C.length;

    return IntStream.range(0, A.length)
        .mapToObj(
            i ->
                ((S == 1 && (long) A[i] * E[0] <= (long) C[0] * (E[0] - B[i]))
                        || (S == 2
                            && check(C[0], E[0], C[1], E[1], A[i], E[0] + E[1] - B[i])
                            && check(C[1], E[1], C[0], E[0], A[i], E[0] + E[1] - B[i])))
                    ? "Y"
                    : "N")
        .collect(Collectors.joining());
  }

  // c*f + otherC*otherF >= rhs1  ==>  c*f*otherE + otherC*otherE*otherF >= rhs1*otherE
  // e*f + otherE*otherF <= rhs2  ==>  e*f*otherC + otherC*otherE*otherF <= rhs2*otherC
  // ==> rhs1*otherE - c*f*otherE <= rhs2*otherC - e*f*otherC
  // ==> f*(e*otherC - c*otherE) <= rhs2*otherC - rhs1*otherE
  static boolean check(int c, int e, int otherC, int otherE, int rhs1, int rhs2) {
    int factor = e * otherC - c * otherE;
    long right = (long) rhs2 * otherC - (long) rhs1 * otherE;

    return (factor < 0) ? (factor <= right) : (right >= 0);
  }
}