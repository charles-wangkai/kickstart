import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int D = sc.nextInt();
      int[] V = new int[N];
      for (int i = 0; i < V.length; ++i) {
        V[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(V, D)));
    }

    sc.close();
  }

  static int solve(int[] V, int D) {
    int result = 0;
    int leftIndex = 0;
    int rightIndex = V.length - 1;
    int target = 1;
    while (true) {
      while (leftIndex <= rightIndex && V[leftIndex] != target) {
        ++leftIndex;
      }
      while (rightIndex >= leftIndex && V[rightIndex] != target) {
        --rightIndex;
      }

      if (leftIndex > rightIndex) {
        break;
      }

      ++result;
      ++leftIndex;
      --rightIndex;
      target = 1 - target;
    }

    return result;
  }
}