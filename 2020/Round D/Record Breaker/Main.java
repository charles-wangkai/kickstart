import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] V = new int[N];
      for (int i = 0; i < V.length; ++i) {
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V)));
    }

    sc.close();
  }

  static int solve(int[] V) {
    int result = 0;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < V.length; ++i) {
      if (V[i] > max && (i == V.length - 1 || V[i] > V[i + 1])) {
        ++result;
      }

      max = Math.max(max, V[i]);
    }

    return result;
  }
}
