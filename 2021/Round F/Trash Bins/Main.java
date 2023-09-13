import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static long solve(String S) {
    int N = S.length();

    int[] leftDistances = new int[N];
    int leftTrashIndex = -1;
    for (int i = 0; i < leftDistances.length; ++i) {
      if (S.charAt(i) == '1') {
        leftTrashIndex = i;
      }

      leftDistances[i] = (leftTrashIndex == -1) ? Integer.MAX_VALUE : (i - leftTrashIndex);
    }

    int[] rightDistances = new int[N];
    int rightTrashIndex = -1;
    for (int i = rightDistances.length - 1; i >= 0; --i) {
      if (S.charAt(i) == '1') {
        rightTrashIndex = i;
      }

      rightDistances[i] = (rightTrashIndex == -1) ? Integer.MAX_VALUE : (rightTrashIndex - i);
    }

    return IntStream.range(0, N)
        .map(i -> Math.min(leftDistances[i], rightDistances[i]))
        .asLongStream()
        .sum();
  }
}
