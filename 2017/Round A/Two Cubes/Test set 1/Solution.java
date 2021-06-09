import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      int[] Z = new int[N];
      int[] R = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
        Z[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, Y, Z, R)));
    }

    sc.close();
  }

  static int solve(int[] X, int[] Y, int[] Z, int[] R) {
    int N = X.length;

    int result = Integer.MAX_VALUE;
    for (int code = 0; code < 1 << N; ++code) {
      int code_ = code;
      int[] indices1 = IntStream.range(0, N).filter(i -> (code_ & (1 << i)) != 0).toArray();
      int[] indices2 = IntStream.range(0, N).filter(i -> (code_ & (1 << i)) == 0).toArray();

      result =
          Math.min(
              result,
              Math.max(
                  computeEdgeLength(X, Y, Z, R, indices1),
                  computeEdgeLength(X, Y, Z, R, indices2)));
    }

    return result;
  }

  static int computeEdgeLength(int[] X, int[] Y, int[] Z, int[] R, int[] indices) {
    if (indices.length == 0) {
      return 0;
    }

    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;
    int minZ = Integer.MAX_VALUE;
    int maxZ = Integer.MIN_VALUE;
    for (int index : indices) {
      minX = Math.min(minX, X[index] - R[index]);
      maxX = Math.max(maxX, X[index] + R[index]);
      minY = Math.min(minY, Y[index] - R[index]);
      maxY = Math.max(maxY, Y[index] + R[index]);
      minZ = Math.min(minZ, Z[index] - R[index]);
      maxZ = Math.max(maxZ, Z[index] + R[index]);
    }

    return Math.max(Math.max(maxX - minX, maxY - minY), maxZ - minZ);
  }
}
