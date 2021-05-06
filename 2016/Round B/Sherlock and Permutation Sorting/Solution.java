import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d: %d", tc, solve(N, M)));
    }

    sc.close();
  }

  static int solve(int N, int M) {
    int[] totalNums = new int[N + 1];
    totalNums[0] = 1;
    for (int i = 1; i < totalNums.length; ++i) {
      totalNums[i] = multiplyMod(totalNums[i - 1], i, M);
    }

    int[] oneChunkNums = new int[N + 1];
    for (int i = 1; i < oneChunkNums.length; ++i) {
      oneChunkNums[i] = totalNums[i];
      for (int j = 1; j < i; ++j) {
        oneChunkNums[i] =
            subtractMod(oneChunkNums[i], multiplyMod(oneChunkNums[j], totalNums[i - j], M), M);
      }
    }

    int[] squareSums = new int[N + 1];
    int[] sums = new int[N + 1];
    for (int i = 1; i <= N; ++i) {
      squareSums[i] = oneChunkNums[i];
      sums[i] = oneChunkNums[i];
      for (int j = 1; j < i; ++j) {
        squareSums[i] =
            addMod(
                squareSums[i],
                multiplyMod(
                    oneChunkNums[j],
                    addMod(
                        addMod(squareSums[i - j], multiplyMod(2, sums[i - j], M), M),
                        totalNums[i - j],
                        M),
                    M),
                M);
        sums[i] =
            addMod(
                sums[i],
                multiplyMod(oneChunkNums[j], addMod(sums[i - j], totalNums[i - j], M), M),
                M);
      }
    }

    return squareSums[N];
  }

  static int addMod(int x, int y, int m) {
    return (x + y) % m;
  }

  static int subtractMod(int x, int y, int m) {
    return (x - y + m) % m;
  }

  static int multiplyMod(int x, int y, int m) {
    return (int) ((long) x * y % m);
  }
}
