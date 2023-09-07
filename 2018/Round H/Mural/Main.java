import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String wall = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(wall)));
    }

    sc.close();
  }

  static int solve(String wall) {
    int result = 0;
    int beginIndex = 0;
    int endIndex = (wall.length() - 1) / 2;
    int sum = IntStream.range(0, endIndex).map(i -> wall.charAt(i) - '0').sum();
    while (endIndex != wall.length()) {
      sum += wall.charAt(endIndex) - '0';
      result = Math.max(result, sum);
      sum -= wall.charAt(beginIndex) - '0';
      ++beginIndex;

      ++endIndex;
    }

    return result;
  }
}
