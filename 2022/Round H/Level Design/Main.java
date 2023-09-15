// https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb1b6/0000000000c47792#analysis

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
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

    int[] distances = computeDistances(N, cycles);

    for (int i = 0; i < result.length; ++i) {
      if (distances[i + 1] != Integer.MAX_VALUE) {
        result[i] = Math.min(result[i], distances[i + 1] - 1);
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static int[] computeDistances(int N, List<Integer> cycles) {
    Map<Integer, Integer> cycleToCount = new HashMap<>();
    for (int cycle : cycles) {
      cycleToCount.put(cycle, cycleToCount.getOrDefault(cycle, 0) + 1);
    }

    int[] distances = new int[N + 1];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[0] = 0;
    for (int cycle : cycleToCount.keySet()) {
      int count = cycleToCount.get(cycle);
      for (int offset = 0; offset < cycle; ++offset) {
        SortedMap<Integer, Integer> valueToCount = new TreeMap<>();
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 1; i <= count - 1; ++i) {
          add(valueToCount, dq, distances, N - offset - i * cycle, i);
        }

        for (int i = 0; N - offset >= i * cycle; ++i) {
          add(valueToCount, dq, distances, N - offset - (i + count) * cycle, i + count);

          int prevMin = valueToCount.firstKey();
          if (prevMin != Integer.MAX_VALUE) {
            distances[N - offset - i * cycle] =
                Math.min(distances[N - offset - i * cycle], prevMin - i);
          }

          remove(valueToCount, dq);
        }
      }
    }

    return distances;
  }

  static void add(
      SortedMap<Integer, Integer> valueToCount,
      Deque<Integer> dq,
      int[] distances,
      int index,
      int additive) {
    int value =
        (index >= 0 && distances[index] != Integer.MAX_VALUE)
            ? (distances[index] + additive)
            : Integer.MAX_VALUE;

    dq.offer(value);
    valueToCount.put(value, valueToCount.getOrDefault(value, 0) + 1);
  }

  static void remove(SortedMap<Integer, Integer> valueToCount, Deque<Integer> dq) {
    int head = dq.poll();

    valueToCount.put(head, valueToCount.get(head) - 1);
    valueToCount.remove(head, 0);
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
