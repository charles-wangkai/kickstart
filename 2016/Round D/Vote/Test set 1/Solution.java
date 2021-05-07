import java.util.Scanner;

public class Solution {
  static int totalNum;
  static int leadNum;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %.9f", tc, solve(N, M)));
    }

    sc.close();
  }

  static double solve(int N, int M) {
    totalNum = 0;
    leadNum = 0;
    search(new int[N + M], N, M, 0);

    return (double) leadNum / totalNum;
  }

  static void search(int[] votes, int restA, int restB, int index) {
    if (index == votes.length) {
      ++totalNum;
      if (isLead(votes)) {
        ++leadNum;
      }

      return;
    }

    if (restA != 0) {
      votes[index] = 1;
      search(votes, restA - 1, restB, index + 1);
    }
    if (restB != 0) {
      votes[index] = -1;
      search(votes, restA, restB - 1, index + 1);
    }
  }

  static boolean isLead(int[] votes) {
    int sum = 0;
    for (int vote : votes) {
      sum += vote;
      if (sum == 0) {
        return false;
      }
    }

    return true;
  }
}
