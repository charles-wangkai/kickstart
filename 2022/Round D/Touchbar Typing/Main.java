import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }
      int M = sc.nextInt();
      int[] K = new int[M];
      for (int i = 0; i < K.length; ++i) {
        K[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, K)));
    }

    sc.close();
  }

  static int solve(int[] S, int[] K) {
    Map<Integer, NavigableSet<Integer>> keyToIndices = new HashMap<>();
    for (int i = 0; i < K.length; ++i) {
      keyToIndices.putIfAbsent(K[i], new TreeSet<>());
      keyToIndices.get(K[i]).add(i);
    }

    Map<Integer, Integer> indexToTime =
        keyToIndices.get(S[0]).stream().collect(Collectors.toMap(index -> index, index -> 0));
    for (int i = 1; i < S.length; ++i) {
      NavigableSet<Integer> indices = keyToIndices.get(S[i]);

      Map<Integer, Integer> nextIndexToTime = new HashMap<>();
      for (int index : indexToTime.keySet()) {
        Integer floor = indices.floor(index);
        if (floor != null) {
          nextIndexToTime.put(
              floor,
              Math.min(
                  nextIndexToTime.getOrDefault(floor, Integer.MAX_VALUE),
                  indexToTime.get(index) + index - floor));
        }

        Integer ceiling = indices.ceiling(index);
        if (ceiling != null) {
          nextIndexToTime.put(
              ceiling,
              Math.min(
                  nextIndexToTime.getOrDefault(ceiling, Integer.MAX_VALUE),
                  indexToTime.get(index) + ceiling - index));
        }
      }

      indexToTime = nextIndexToTime;
    }

    return indexToTime.values().stream().mapToInt(x -> x).min().getAsInt();
  }
}
