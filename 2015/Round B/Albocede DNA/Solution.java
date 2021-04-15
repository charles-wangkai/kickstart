import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static int solve(String S) {
    int n = S.length();

    int[][][] wayNums = new int[n + 1][n + 1][4];
    wayNums[0][0][3] = 1;

    for (char ch : S.toCharArray()) {
      int[][][] nextWayNums = new int[n + 1][n + 1][];
      for (int acDiff = 0; acDiff <= n; ++acDiff) {
        for (int bdDiff = 0; bdDiff <= n; ++bdDiff) {
          nextWayNums[acDiff][bdDiff] =
              Arrays.copyOf(wayNums[acDiff][bdDiff], wayNums[acDiff][bdDiff].length);
        }
      }

      for (int acDiff = 0; acDiff <= n; ++acDiff) {
        for (int bdDiff = 0; bdDiff <= n; ++bdDiff) {
          if (ch == 'a') {
            if (acDiff == 0 && bdDiff == 0) {
              nextWayNums[1][0][0] = addMod(nextWayNums[1][0][0], wayNums[0][0][3]);
            }
            if (acDiff != n && bdDiff == 0) {
              nextWayNums[acDiff + 1][0][0] =
                  addMod(nextWayNums[acDiff + 1][0][0], wayNums[acDiff][0][0]);
            }
          } else if (ch == 'b') {
            if (bdDiff == 1) {
              nextWayNums[acDiff][1][1] = addMod(nextWayNums[acDiff][1][1], wayNums[acDiff][0][0]);
            }
            if (bdDiff != n) {
              nextWayNums[acDiff][bdDiff + 1][1] =
                  addMod(nextWayNums[acDiff][bdDiff + 1][1], wayNums[acDiff][bdDiff][1]);
            }
          } else if (ch == 'c') {
            if (acDiff != 0) {
              nextWayNums[acDiff - 1][bdDiff][2] =
                  addMod(nextWayNums[acDiff - 1][bdDiff][2], wayNums[acDiff][bdDiff][1]);

              nextWayNums[acDiff - 1][bdDiff][2] =
                  addMod(nextWayNums[acDiff - 1][bdDiff][2], wayNums[acDiff][bdDiff][2]);
            }
          } else {
            if (acDiff == 0 && bdDiff != 0) {
              nextWayNums[0][bdDiff - 1][3] =
                  addMod(nextWayNums[0][bdDiff - 1][3], wayNums[0][bdDiff][2]);

              nextWayNums[0][bdDiff - 1][3] =
                  addMod(nextWayNums[0][bdDiff - 1][3], wayNums[0][bdDiff][3]);
            }
          }
        }
      }

      wayNums = nextWayNums;
    }

    return subtractMod(wayNums[0][0][3], 1);
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }

  static int subtractMod(int x, int y) {
    return (x - y + MODULUS) % MODULUS;
  }
}
