import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] E = new int[M];
      int[] K = new int[M];
      int[][] friends = new int[M][];
      for (int i = 0; i < M; ++i) {
        E[i] = sc.nextInt() - 1;
        K[i] = sc.nextInt();
        int B = sc.nextInt();
        friends[i] = new int[B];
        for (int j = 0; j < friends[i].length; ++j) {
          friends[i][j] = sc.nextInt() - 1;
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(N, E, K, friends) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int N, int[] E, int[] K, int[][] friends) {
    @SuppressWarnings("unchecked")
    List<Friendship>[] friendshipLists = new List[1 << N];
    for (int i = 0; i < friendshipLists.length; ++i) {
      friendshipLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < E.length; ++i) {
      for (int friend : friends[i]) {
        friendshipLists[E[i]].add(new Friendship(friend, K[i]));
        friendshipLists[friend].add(new Friendship(E[i], K[i]));
      }
    }

    int[] values =
        IntStream.range(0, 1 << N)
            .boxed()
            .sorted(
                Comparator.comparing(
                        (Integer i) ->
                            friendshipLists[i].stream()
                                .mapToInt(friendship -> friendship.kRound)
                                .sum())
                    .reversed())
            .mapToInt(x -> x)
            .toArray();

    return search(
        friendshipLists, values, IntStream.range(0, 1 << N).toArray(), new HashMap<>(), 0);
  }

  static boolean search(
      List<Friendship>[] friendshipLists,
      int[] values,
      int[] indices,
      Map<Integer, Integer> valueToIndex,
      int depth) {
    if (depth == values.length) {
      return true;
    }

    for (int i = depth; i < ((depth == 0) ? 1 : values.length); ++i) {
      if (check(friendshipLists, valueToIndex, indices[i], values[depth])) {
        swap(indices, depth, i);
        valueToIndex.put(values[depth], indices[depth]);

        if (search(friendshipLists, values, indices, valueToIndex, depth + 1)) {
          return true;
        }

        valueToIndex.remove(values[depth]);
        swap(indices, depth, i);
      }
    }

    return false;
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  static boolean check(
      List<Friendship>[] friendshipLists,
      Map<Integer, Integer> valueToIndex,
      int index,
      int value) {
    int N = friendshipLists.length;

    for (Friendship friendship : friendshipLists[value]) {
      int mask = (1 << N) - (1 << friendship.kRound);
      if (valueToIndex.containsKey(friendship.friend)
          && (index & mask) == (valueToIndex.get(friendship.friend) & mask)) {
        return false;
      }
    }

    return true;
  }
}

class Friendship {
  int friend;
  int kRound;

  Friendship(int friend, int kRound) {
    this.friend = friend;
    this.kRound = kRound;
  }
}
