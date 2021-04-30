import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int X = sc.nextInt();
      int K = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N, X, K, A, B, C)));
    }

    sc.close();
  }

  static double solve(int N, int X, int K, int A, int B, int C) {
    Map<Integer, Double> valueToProb = Map.of(X, 1.0);
    for (int i = 0; i < N; ++i) {
      Map<Integer, Double> nextValueToProb = new HashMap<>();
      for (int value : valueToProb.keySet()) {
        nextValueToProb.put(
            value & K,
            nextValueToProb.getOrDefault(value & K, 0.0) + valueToProb.get(value) * A / 100);
        nextValueToProb.put(
            value | K,
            nextValueToProb.getOrDefault(value | K, 0.0) + valueToProb.get(value) * B / 100);
        nextValueToProb.put(
            value ^ K,
            nextValueToProb.getOrDefault(value ^ K, 0.0) + valueToProb.get(value) * C / 100);
      }

      valueToProb = nextValueToProb;
    }

    Map<Integer, Double> valueToProb_ = valueToProb;
    return valueToProb.keySet().stream()
        .mapToDouble(value -> value * valueToProb_.get(value))
        .sum();
  }
}
