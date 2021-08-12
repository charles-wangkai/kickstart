import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int V1 = sc.nextInt();
      int H1 = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();
      int D = sc.nextInt();
      int E = sc.nextInt();
      int F = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, V1, H1, A, B, C, D, E, F, M)));
    }

    sc.close();
  }

  static int solve(int N, int V1, int H1, int A, int B, int C, int D, int E, int F, int M) {
    int[] V = new int[N];
    int[] H = new int[N];
    V[0] = V1;
    H[0] = H1;
    for (int i = 1; i < N; ++i) {
      V[i] = (A * V[i - 1] + B * H[i - 1] + C) % M;
      H[i] = (D * V[i - 1] + E * H[i - 1] + F) % M;
    }

    int result = 0;
    for (int i = 0; i < N; ++i) {
      for (int j = i + 1; j < N; ++j) {
        for (int k = j + 1; k < N; ++k) {
          if (check(V[i], H[i], V[j], H[j], V[k], H[k])) {
            ++result;
          }
        }
      }
    }

    return result;
  }

  static boolean check(int v1, int h1, int v2, int h2, int v3, int h3) {
    int minV = Math.min(Math.min(v1, v2), v3);
    int maxV = Math.max(Math.max(v1, v2), v3);
    int minH = Math.min(Math.min(h1, h2), h3);
    int maxH = Math.max(Math.max(h1, h2), h3);

    return isOn(minV, maxV, minH, maxH, v1, h1)
        && isOn(minV, maxV, minH, maxH, v2, h2)
        && isOn(minV, maxV, minH, maxH, v3, h3);
  }

  static boolean isOn(int minV, int maxV, int minH, int maxH, int v, int h) {
    return ((v == minV || v == maxV) && h >= minH && h <= maxH)
        || ((h == minH || h == maxH) && v >= minV && v <= maxV);
  }
}
