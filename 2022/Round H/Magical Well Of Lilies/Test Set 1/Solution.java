import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int L = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(L)));
    }

    sc.close();
  }

  static int solve(int L) {
    return search(0, 0, L);
  }

  static int search(int basket, int note, int lily) {
    if (lily == 0) {
      return 0;
    }

    int result = 1 + search(basket + 1, note, lily - 1);
    if (lily >= note && note != 0) {
      result = Math.min(result, 2 + search(basket + note, note, lily - note));
    }
    if (note != basket) {
      result = Math.min(result, 4 + search(basket, basket, lily));
    }

    return result;
  }
}
