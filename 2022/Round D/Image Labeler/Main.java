import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(A, M)));
    }

    sc.close();
  }

  static double solve(int[] A, int M) {
    Arrays.sort(A);

    return computeMedian(A, A.length - M)
        + IntStream.range(A.length - M + 1, A.length).map(i -> A[i]).sum();
  }

  static double computeMedian(int[] A, int endIndex) {
    return (endIndex % 2 == 0) ? A[endIndex / 2] : (A[endIndex / 2] + A[endIndex / 2 + 1]) / 2.0;
  }
}
