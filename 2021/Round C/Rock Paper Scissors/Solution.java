import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    int X = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int W = sc.nextInt();
      int E = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(X, W, E)));
    }

    sc.close();
  }

  static String solve(int X, int W, int E) {
    if (E == W) {
      return "RSP".repeat(60).substring(0, 60);
      // return ("R" + "SSSSSPPPPPPRRRRRR".repeat(60)).substring(0, 60);

      // return "RRRRSSSSSSSSSSSSSSPPPPPPPPPPPPPPPPPPPPPRRRRRRRRRRRRRRRRRRRRR";
    } else if (E == W / 2) {
      // return "RSP".repeat(60).substring(0, 60);

      // return "RRRSSSSSSSSSSSSSPPPPPPPPPPPPPPPPPPPPPPPPRRRRRRRRRRRRRRRRRRRR";

      // return "RSSSSSSSPPPPPPPPPPPPRRRRRRRRRR".repeat(60).substring(0, 60);

      // return "RSSSPPPPRR".repeat(60).substring(0, 60);

      // return ("R" + "SSSPPPPPPPRRRRRRRRRRSSSSSSSSSPPPPPP".repeat(60)).substring(0, 60);
      return ("R" + "SSSPPPPPPPRRRRRRRRRRRRRSSSSSSSSSSSSSSSSPPPPPPP".repeat(60)).substring(0, 60);
    } else if (E == W / 10) {
      // return "RSSSSPPPPRRR".repeat(60).substring(0, 60);

      // return "RSP".repeat(60).substring(0, 60);

      return "RSSSSSSSSSPPPPPPPPPPPPPPPPPPPPPPPPPPRRRRRRRRRRRRRRRRRRRRRRRR";

      // return "RSSSSSSSPPPPPPPPPPPPPRRRRRRRRR".repeat(60).substring(0, 60);
    }

    // return "RSP".repeat(20);

    // return "RSSSPPPPRR".repeat(60);

    return "RSSSSSSSSSPPPPPPPPPPPPPPPPPPPPPPPPPPPRRRRRRRRRRRRRRRRRRRRRRR";
  }
}