import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] A = readArray(sc, N);
      int[] B = readArray(sc, N);
      int[] C = readArray(sc, N);
      int[] D = readArray(sc, N);

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, C, D, K)));
    }

    sc.close();
  }

  static int[] readArray(Scanner sc, int N) {
    int[] result = new int[N];
    for (int i = 0; i < result.length; ++i) {
      result[i] = sc.nextInt();
    }

    return result;
  }

  static long solve(int[] A, int[] B, int[] C, int[] D, int K) {
    Map<Integer, Integer> abXorToCount = buildXorToCount(A, B);
    Map<Integer, Integer> cdXorToCount = buildXorToCount(C, D);

    long result = 0;
    for (int abXor : abXorToCount.keySet()) {
      result += (long) abXorToCount.get(abXor) * cdXorToCount.getOrDefault(abXor ^ K, 0);
    }

    return result;
  }

  static Map<Integer, Integer> buildXorToCount(int[] values1, int[] values2) {
    Map<Integer, Integer> xorToCount = new HashMap<>();
    for (int value1 : values1) {
      for (int value2 : values2) {
        int xor = value1 ^ value2;
        xorToCount.put(xor, xorToCount.getOrDefault(xor, 0) + 1);
      }
    }

    return xorToCount;
  }
}
