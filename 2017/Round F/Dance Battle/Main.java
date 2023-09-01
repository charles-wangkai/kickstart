import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int E = sc.nextInt();
      int N = sc.nextInt();
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(E, S)));
    }

    sc.close();
  }

  static int solve(int E, int[] S) {
    Arrays.sort(S);

    int result = 0;
    int honor = 0;
    int leftIndex = 0;
    int rightIndex = S.length - 1;
    while (leftIndex <= rightIndex) {
      if (E > S[leftIndex]) {
        E -= S[leftIndex];
        ++leftIndex;

        ++honor;
        result = Math.max(result, honor);
      } else if (honor != 0) {
        E += S[rightIndex];
        --rightIndex;

        --honor;
      } else {
        break;
      }
    }

    return result;
  }
}
