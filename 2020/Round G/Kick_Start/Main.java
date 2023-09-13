import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static long solve(String S) {
    int[] beginIndices = find(S, "KICK");
    int[] endIndices = find(S, "START");

    long result = 0;
    int endPos = 0;
    for (int beginIndex : beginIndices) {
      while (endPos != endIndices.length && endIndices[endPos] < beginIndex) {
        ++endPos;
      }

      result += endIndices.length - endPos;
    }

    return result;
  }

  static int[] find(String S, String substring) {
    return IntStream.range(0, S.length()).filter(i -> S.startsWith(substring, i)).toArray();
  }
}
