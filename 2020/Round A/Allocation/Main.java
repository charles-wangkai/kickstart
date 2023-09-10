import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int B = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B)));
    }

    sc.close();
  }

  static int solve(int[] A, int B) {
    int[] sorted = Arrays.stream(A).boxed().sorted().mapToInt(x -> x).toArray();

    int result = 0;
    int rest = B;
    for (int cost : sorted) {
      if (rest >= cost) {
        ++result;
        rest -= cost;
      }
    }

    return result;
  }
}
