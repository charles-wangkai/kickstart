import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int Q = sc.nextInt();
      int[] V = new int[M];
      for (int i = 0; i < V.length; ++i) {
        V[i] = sc.nextInt();
      }
      int[] P = new int[N];
      int[] H = new int[N];
      for (int i = 0; i < N; ++i) {
        P[i] = sc.nextInt();
        H[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(P, H, V, Q)));
    }

    sc.close();
  }

  static String solve(int[] P, int[] H, int[] V, int Q) {
    int result = -1;
    int lower = 0;
    int upper = Arrays.stream(P).map(Math::abs).max().getAsInt();
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(P, H, V, Q, middle)) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return (result == -1) ? "IMPOSSIBLE" : String.valueOf(result);
  }

  static boolean check(int[] P, int[] H, int[] V, int Q, int time) {
    int energy = 0;
    for (int i = 0; i < P.length; ++i) {
      int downHeight = H[i];
      int upHeight = H[i];
      while (true) {
        if (downHeight < 0 && upHeight >= V.length) {
          return false;
        }

        if ((downHeight >= 0 && withinTime(P[i], V[downHeight], time))
            || (upHeight < V.length && withinTime(P[i], V[upHeight], time))) {
          break;
        }

        --downHeight;
        ++upHeight;
        ++energy;
      }
    }

    return energy <= Q;
  }

  static boolean withinTime(int p, int v, int time) {
    return p == 0 || (p * v < 0 && (Math.abs(p) + Math.abs(v) - 1) / Math.abs(v) <= time);
  }
}
