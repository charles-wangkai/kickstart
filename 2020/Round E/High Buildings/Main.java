import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(N, A, B, C)));
    }

    sc.close();
  }

  static String solve(int N, int A, int B, int C) {
    if (A + B - C > N || (N != 1 && A == 1 && B == 1 && C == 1)) {
      return "IMPOSSIBLE";
    }

    int[] heights = new int[N];
    Arrays.fill(heights, 1);
    for (int i = 0; i < A - C; ++i) {
      heights[i] = N - 1;
    }
    for (int i = heights.length - 1; i >= heights.length - (B - C); --i) {
      heights[i] = N - 1;
    }
    if (A == C && B == C) {
      for (int i = 0; i < C - 1; ++i) {
        heights[i] = N;
      }
      heights[heights.length - 1] = N;

    } else if (A == C) {
      for (int i = 0; i < C; ++i) {
        heights[i] = N;
      }
    } else {
      for (int i = heights.length - (B - C) - 1; i >= heights.length - B; --i) {
        heights[i] = N;
      }
    }

    return Arrays.stream(heights).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}
