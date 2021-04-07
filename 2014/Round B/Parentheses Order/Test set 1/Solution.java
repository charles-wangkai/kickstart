import java.util.Scanner;

public class Solution {
  static int foundCount;
  static String solution;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int n = sc.nextInt();
      int k = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(n, k)));
    }

    sc.close();
  }

  static String solve(int n, int k) {
    foundCount = 0;
    solution = null;

    search(k, new char[n * 2], n, n, 0, 0);

    return (solution == null) ? "Doesn't Exist!" : solution;
  }

  static void search(
      int k, char[] sequence, int leftRestNum, int rightRestNum, int depth, int index) {
    if (index == sequence.length) {
      ++foundCount;
      if (foundCount == k) {
        solution = new String(sequence);
      }

      return;
    }

    if (leftRestNum != 0) {
      sequence[index] = '(';
      search(k, sequence, leftRestNum - 1, rightRestNum, depth + 1, index + 1);
    }
    if (rightRestNum != 0 && depth != 0) {
      sequence[index] = ')';
      search(k, sequence, leftRestNum, rightRestNum - 1, depth - 1, index + 1);
    }
  }
}
