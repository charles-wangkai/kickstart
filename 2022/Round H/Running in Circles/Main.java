import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();
      int N = sc.nextInt();
      int[] D = new int[N];
      char[] C = new char[N];
      for (int i = 0; i < N; ++i) {
        D[i] = sc.nextInt();
        C[i] = sc.next().charAt(0);
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(L, D, C)));
    }

    sc.close();
  }

  static long solve(int L, int[] D, char[] C) {
    long result = 0;
    char direction = 'C';
    int distance = 0;
    for (int i = 0; i < D.length; ++i) {
      if (C[i] == direction) {
        distance = Math.abs(distance) + D[i];

        result += distance / L;
        distance %= L;
      } else {
        distance = -Math.abs(distance) + D[i];

        if (distance >= 0) {
          direction = C[i];

          result += distance / L;
          distance %= L;
        }
      }
    }

    return result;
  }
}
