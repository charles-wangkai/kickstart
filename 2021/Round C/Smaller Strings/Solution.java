import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      int K = sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S, K)));
    }

    sc.close();
  }

  static int solve(String S, int K) {
    int lessNum = 0;
    int equalNum = 1;
    int greaterNum = 0;
    for (int i = (S.length() - 1) / 2, j = S.length() - 1 - i; i >= 0; --i, ++j) {
      int nextLessNum = 0;
      int nextEqualNum = 0;
      int nextGreaterNum = 0;
      char left = S.charAt(i);
      char right = S.charAt(j);
      for (char c = 'a'; c < 'a' + K; ++c) {
        if (c < left) {
          nextLessNum = addMod(nextLessNum, addMod(addMod(lessNum, equalNum), greaterNum));
        } else if (c > left) {
          nextGreaterNum = addMod(nextGreaterNum, addMod(addMod(lessNum, equalNum), greaterNum));
        } else {
          nextLessNum = addMod(nextLessNum, lessNum);
          nextGreaterNum = addMod(nextGreaterNum, greaterNum);
          if (c < right) {
            nextLessNum = addMod(nextLessNum, equalNum);
          } else if (c > right) {
            nextGreaterNum = addMod(nextGreaterNum, equalNum);
          } else {
            nextEqualNum = addMod(nextEqualNum, equalNum);
          }
        }
      }

      lessNum = nextLessNum;
      equalNum = nextEqualNum;
      greaterNum = nextGreaterNum;
    }

    return lessNum;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }
}