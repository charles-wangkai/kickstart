import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int G = sc.nextInt();
      int M = sc.nextInt();
      int[] H = new int[G];
      char[] types = new char[G];
      for (int i = 0; i < G; ++i) {
        H[i] = sc.nextInt() - 1;
        types[i] = sc.next().charAt(0);
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, H, types, M)));
    }

    sc.close();
  }

  static String solve(int N, int[] H, char[] types, int M) {
    Map<Integer, Integer> posGuestToDelegate = new HashMap<>();
    NavigableMap<Integer, Integer> posLastPosToIndex = new TreeMap<>();
    Map<Integer, Integer> negGuestToDelegate = new HashMap<>();
    NavigableMap<Integer, Integer> negLastPosToIndex = new TreeMap<>();
    for (int i = 0; i < H.length; ++i) {
      if (types[i] == 'C') {
        int lastPos = (H[i] + M) % N;
        if (posLastPosToIndex.containsKey(lastPos)) {
          posGuestToDelegate.put(i, posLastPosToIndex.get(lastPos));
        } else {
          posLastPosToIndex.put(lastPos, i);
        }
      } else {
        int lastPos = Math.floorMod(H[i] - M, N);
        if (negLastPosToIndex.containsKey(lastPos)) {
          negGuestToDelegate.put(i, negLastPosToIndex.get(lastPos));
        } else {
          negLastPosToIndex.put(lastPos, i);
        }
      }
    }

    int[] result = new int[H.length];
    for (int i = 0; i < N; ++i) {
      Integer posLastPos = posLastPosToIndex.ceilingKey(i);
      if (posLastPos == null && !posLastPosToIndex.isEmpty()) {
        posLastPos = posLastPosToIndex.firstKey();
      }
      int posDistance =
          (posLastPos == null) ? Integer.MAX_VALUE : computeDistance(N, i, posLastPos);

      Integer negLastPos = negLastPosToIndex.floorKey(i);
      if (negLastPos == null && !negLastPosToIndex.isEmpty()) {
        negLastPos = negLastPosToIndex.lastKey();
      }
      int negDistance =
          (negLastPos == null) ? Integer.MAX_VALUE : computeDistance(N, negLastPos, i);

      if (posLastPos != null
          && posDistance <= M
          && (negLastPos == null || posDistance <= negDistance)) {
        ++result[posLastPosToIndex.get(posLastPos)];
      }
      if (negLastPos != null
          && negDistance <= M
          && (posLastPos == null || negDistance <= posDistance)) {
        ++result[negLastPosToIndex.get(negLastPos)];
      }
    }

    for (int posGuest : posGuestToDelegate.keySet()) {
      result[posGuest] = result[posGuestToDelegate.get(posGuest)];
    }
    for (int negGuest : negGuestToDelegate.keySet()) {
      result[negGuest] = result[negGuestToDelegate.get(negGuest)];
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static int computeDistance(int N, int from, int to) {
    return (to >= from) ? (to - from) : (to + N - from);
  }
}
