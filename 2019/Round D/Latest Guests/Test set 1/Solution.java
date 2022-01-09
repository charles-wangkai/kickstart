import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
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
    @SuppressWarnings("unchecked")
    List<Integer>[] lastLists = new List[N];
    for (int i = 0; i < lastLists.length; ++i) {
      lastLists[i] = List.of();
    }

    for (int time = 0; time <= M; ++time) {
      @SuppressWarnings("unchecked")
      List<Integer>[] nextLastLists = new List[N];
      for (int i = 0; i < nextLastLists.length; ++i) {
        nextLastLists[i] = new ArrayList<>();
      }
      for (int i = 0; i < H.length; ++i) {
        nextLastLists[Math.floorMod(H[i] + ((types[i] == 'C') ? 1 : -1) * time, N)].add(i);
      }

      for (int i = 0; i < nextLastLists.length; ++i) {
        if (!nextLastLists[i].isEmpty()) {
          lastLists[i] = nextLastLists[i];
        }
      }
    }

    int[] result = new int[H.length];
    for (List<Integer> lastList : lastLists) {
      for (int last : lastList) {
        ++result[last];
      }
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }
}