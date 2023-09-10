import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A)));
    }

    sc.close();
  }

  static String solve(int[] A) {
    List<Integer> result = new ArrayList<>();
    PriorityQueue<Integer> larges = new PriorityQueue<>();
    PriorityQueue<Integer> smalls = new PriorityQueue<>(Comparator.reverseOrder());
    for (int Ai : A) {
      if (!larges.isEmpty() && Ai > larges.peek()) {
        smalls.offer(larges.poll());
        larges.offer(Ai);
      } else {
        smalls.offer(Ai);
      }

      if (smalls.peek() >= larges.size() + 1) {
        larges.offer(smalls.poll());
      }

      result.add(larges.size());
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining(" "));
  }
}
