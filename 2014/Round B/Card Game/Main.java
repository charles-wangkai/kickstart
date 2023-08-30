import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] a = new int[N];
      for (int i = 0; i < a.length; ++i) {
        a[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(a, K)));
    }

    sc.close();
  }

  static int solve(int[] a, int K) {
    List<Integer> rest = Arrays.stream(a).boxed().collect(Collectors.toList());
    while (true) {
      int[] candidateBeginIndices =
          IntStream.range(0, rest.size() - 2)
              .filter(
                  i -> rest.get(i) + K == rest.get(i + 1) && rest.get(i + 1) + K == rest.get(i + 2))
              .toArray();
      if (candidateBeginIndices.length == 0) {
        break;
      }

      int beginIndex =
          Arrays.stream(candidateBeginIndices).boxed().max(Comparator.comparing(rest::get)).get();
      rest.remove(beginIndex);
      rest.remove(beginIndex);
      rest.remove(beginIndex);
    }

    return rest.size();
  }
}
