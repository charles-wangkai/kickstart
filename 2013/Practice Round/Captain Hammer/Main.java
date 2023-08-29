import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int V = sc.nextInt();
      int D = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(V, D)));
    }

    sc.close();
  }

  static double solve(int V, int D) {
    return Math.toDegrees(
        Math.asin(
            Math.sqrt((1 - Math.sqrt(Math.max(0, 1 - 4 * Math.pow(D * 9.8 / 2 / V / V, 2)))) / 2)));
  }
}
