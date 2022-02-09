import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] H = new int[N];
      for (int i = 0; i < H.length; ++i) {
        H[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(H)));
    }

    sc.close();
  }

  static int solve(int[] H) {
    return (int)
        IntStream.range(1, H.length - 1).filter(i -> H[i] > H[i - 1] && H[i] > H[i + 1]).count();
  }
}