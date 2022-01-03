import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int A = sc.nextInt();
      int B = sc.nextInt();
      sc.nextInt();

      solve(sc, A, B);
    }

    sc.close();
  }

  static void solve(Scanner sc, int A, int B) {
    int lower = A + 1;
    int upper = B;
    while (true) {
      int middle = (lower + upper) / 2;
      System.out.println(middle);
      System.out.flush();

      String response = sc.next();
      if (response.equals("CORRECT")) {
        return;
      }

      if (response.equals("TOO_SMALL")) {
        lower = middle + 1;
      } else if (response.equals("TOO_BIG")) {
        upper = middle - 1;
      }
    }
  }
}