import java.util.Scanner;

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
    int result = -1;
    int lower = 1;
    int upper = 400_000_000;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(X, Y, Z, R, middle)) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }

  static boolean check(int[] X, int[] Y, int[] Z, int[] R, int edgeLength) {
    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;
    int minZ = Integer.MAX_VALUE;
    int maxZ = Integer.MIN_VALUE;
    for (int i = 0; i < X.length; ++i) {
      minX = Math.min(minX, X[i] - R[i]);
      maxX = Math.max(maxX, X[i] + R[i]);
      minY = Math.min(minY, Y[i] - R[i]);
      maxY = Math.max(maxY, Y[i] + R[i]);
      minZ = Math.min(minZ, Z[i] - R[i]);
      maxZ = Math.max(maxZ, Z[i] + R[i]);
    }

    return isEnoughEdgeLength(X, Y, Z, R, edgeLength, minX, minY, minZ)
        || isEnoughEdgeLength(X, Y, Z, R, edgeLength, minX, minY, maxZ - edgeLength)
        || isEnoughEdgeLength(X, Y, Z, R, edgeLength, minX, maxY - edgeLength, minZ)
        || isEnoughEdgeLength(X, Y, Z, R, edgeLength, minX, maxY - edgeLength, maxZ - edgeLength)
        || isEnoughEdgeLength(X, Y, Z, R, edgeLength, maxX - edgeLength, minY, minZ)
        || isEnoughEdgeLength(X, Y, Z, R, edgeLength, maxX - edgeLength, minY, maxZ - edgeLength)
        || isEnoughEdgeLength(X, Y, Z, R, edgeLength, maxX - edgeLength, maxY - edgeLength, minZ)
        || isEnoughEdgeLength(
            X, Y, Z, R, edgeLength, maxX - edgeLength, maxY - edgeLength, maxZ - edgeLength);
  }

  static boolean isEnoughEdgeLength(
      int[] X,
      int[] Y,
      int[] Z,
      int[] R,
      int edgeLength,
      int cubeMinX,
      int cubeMinY,
      int cubeMinZ) {
    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxY = Integer.MIN_VALUE;
    int minZ = Integer.MAX_VALUE;
    int maxZ = Integer.MIN_VALUE;
    for (int i = 0; i < X.length; ++i) {
      if (!(X[i] - R[i] >= cubeMinX
          && X[i] + R[i] <= cubeMinX + edgeLength
          && Y[i] - R[i] >= cubeMinY
          && Y[i] + R[i] <= cubeMinY + edgeLength
          && Z[i] - R[i] >= cubeMinZ
          && Z[i] + R[i] <= cubeMinZ + edgeLength)) {
        minX = Math.min(minX, X[i] - R[i]);
        maxX = Math.max(maxX, X[i] + R[i]);
        minY = Math.min(minY, Y[i] - R[i]);
        maxY = Math.max(maxY, Y[i] + R[i]);
        minZ = Math.min(minZ, Z[i] - R[i]);
        maxZ = Math.max(maxZ, Z[i] + R[i]);
      }
    }

    return minX == Integer.MAX_VALUE
        || (maxX - minX <= edgeLength && maxY - minY <= edgeLength && maxZ - minZ <= edgeLength);
  }
}
