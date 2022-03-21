import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] A = new int[N];
      int[] B = new int[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }
      int[] S = new int[M];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, B, S)));
    }

    sc.close();
  }

  static String solve(int[] A, int[] B, int[] S) {
    boolean[] problems = new boolean[Arrays.stream(B).max().getAsInt() + 1];
    for (int i = 0; i < A.length; ++i) {
      Arrays.fill(problems, A[i], B[i] + 1, true);
    }

    int[] result = new int[S.length];
    for (int i = 0; i < result.length; ++i) {
      int i_ = i;
      result[i] =
          IntStream.range(0, problems.length)
              .filter(j -> problems[j])
              .boxed()
              .min(
                  Comparator.comparing((Integer j) -> Math.abs(j - S[i_]))
                      .thenComparing(Function.identity()))
              .get();

      problems[result[i]] = false;
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}