import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
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
    List<Integer> rest = new ArrayList<>();
    for (int ai : a) {
      rest.add(ai);
      if (rest.size() >= 3
          && rest.get(rest.size() - 1).equals(rest.get(rest.size() - 2))
          && rest.get(rest.size() - 2).equals(rest.get(rest.size() - 3))) {
        rest.remove(rest.size() - 1);
        rest.remove(rest.size() - 1);
        rest.remove(rest.size() - 1);
      }
    }

    return rest.size();
  }
}
