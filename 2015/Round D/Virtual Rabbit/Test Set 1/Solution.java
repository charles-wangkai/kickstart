import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  static final int DAY_SECOND_NUM = 86400;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String G = sc.next();
      String W = sc.next();
      String H = sc.next();
      String B = sc.next();
      String X = sc.next();
      long D = sc.nextLong();

      System.out.println(String.format("Case #%d: %d", tc, solve(G, W, H, B, X, D)));
    }

    sc.close();
  }

  static long solve(String G, String W, String H, String B, String X, long D) {
    int up = convertToSecond(G);
    int work = convertToSecond(W);
    int home = convertToSecond(H);
    int bed = convertToSecond(B);
    int duration = convertToSecond(X);

    int result = 0;
    int current = 0;
    while (D * DAY_SECOND_NUM - current > duration) {
      int next = current + duration;
      while (next != current && !isValid(up, work, home, bed, next)) {
        --next;
      }
      if (next == current) {
        return -1;
      }

      ++result;
      current = next;
    }

    return result;
  }

  static int convertToSecond(String s) {
    int[] parts = Arrays.stream(s.split(":")).mapToInt(Integer::parseInt).toArray();

    return parts[0] * 3600 + parts[1] * 60 + parts[2];
  }

  static boolean isValid(int up, int work, int home, int bed, int time) {
    int dayTime = time % DAY_SECOND_NUM;

    return (dayTime >= up && dayTime < work) || (dayTime >= home && dayTime < bed);
  }
}
