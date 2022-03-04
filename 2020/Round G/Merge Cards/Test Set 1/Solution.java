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
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(A)));
    }

    sc.close();
  }

  static double solve(int[] A) {
    List<Long> totals = new ArrayList<>();
    search(totals, Arrays.stream(A).asLongStream().boxed().collect(Collectors.toList()), 0);

    return totals.stream().mapToLong(x -> x).average().getAsDouble();
  }

  static void search(List<Long> totals, List<Long> rests, long total) {
    if (rests.size() == 1) {
      totals.add(total);

      return;
    }

    for (int i = 0; i < rests.size() - 1; ++i) {
      List<Long> nextRests = new ArrayList<>(rests);
      long rest1 = nextRests.remove(i);
      long rest2 = nextRests.remove(i);
      nextRests.add(i, rest1 + rest2);

      search(totals, nextRests, total + rest1 + rest2);
    }
  }
}