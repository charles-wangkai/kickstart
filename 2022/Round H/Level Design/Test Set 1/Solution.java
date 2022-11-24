import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] P = new int[N];
      for (int i = 0; i < P.length; ++i) {
        P[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(P)));
    }

    sc.close();
  }

  static String solve(int[] P) {
    int N = P.length;

    List<Integer> cycles = buildCycles(P);

    int[] result = new int[N];
    int prevSum = 0;
    for (int i = 0; i < cycles.size(); ++i) {
      int sum = prevSum + cycles.get(i);
      for (int j = prevSum + 1; j <= sum; ++j) {
        result[j - 1] = i + 1;
      }

      prevSum = sum;
    }

    int[] distances = new int[N + 1];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[0] = 0;
    for (int cycle : cycles) {
      for (int i = distances.length - 1; i >= 0; --i) {
        if (distances[i] != Integer.MAX_VALUE && i + cycle < distances.length) {
          distances[i + cycle] = Math.min(distances[i + cycle], distances[i] + 1);
        }
      }
    }

    for (int i = 0; i < result.length; ++i) {
      if (distances[i + 1] != -1) {
        result[i] = Math.min(result[i], distances[i + 1] - 1);
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static List<Integer> buildCycles(int[] P) {
    List<Integer> result = new ArrayList<>();
    boolean[] visited = new boolean[P.length];
    for (int i = 0; i < P.length; ++i) {
      if (!visited[i]) {
        int cycle = 0;
        for (int index = i; !visited[index]; index = P[index]) {
          visited[index] = true;
          ++cycle;
        }

        result.add(cycle);
      }
    }
    Collections.sort(result, Comparator.reverseOrder());

    return result;
  }
}
