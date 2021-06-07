// https://codeforces.com/blog/entry/48245?#comment-324065

import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int P = sc.nextInt();
      int[] X = new int[N];
      for (int i = 0; i < X.length; ++i) {
        X[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, P)));
    }

    sc.close();
  }

  static int solve(int[] X, int P) {
    int N = X.length;

    int[][] partitionNums = new int[N + 1][N + 1];
    int[][] minValues = new int[N + 1][N + 1];
    int[][] maxValues = new int[N + 1][N + 1];
    int[] lasts = new int[N + 1];
    for (int i = 1; i <= N; ++i) {
      partitionNums[i][i] = 1;
      minValues[i][i] = X[i - 1];
      maxValues[i][i] = X[i - 1];
      lasts[i] = i;

      for (int j = i + 1; j <= N; ++j) {
        minValues[i][j] = Math.min(minValues[i][j - 1], X[j - 1]);
        maxValues[i][j] = Math.max(maxValues[i][j - 1], X[j - 1]);
      }
    }

    for (int length = 2; length <= N; ++length) {
      for (int i = 1; i + length - 1 <= N; ++i) {
        int j = i + length - 1;
        if (maxValues[i][j] - minValues[i][j] == j - i) {
          if (minValues[i][j] < minValues[i][lasts[i]]) {
            partitionNums[i][j] = 1;
          } else {
            partitionNums[i][j] = partitionNums[i][lasts[i]] + partitionNums[lasts[i] + 1][j];
          }

          lasts[i] = j;
        }
      }
    }

    int result = partitionNums[1][N];
    for (int l1 = 1; l1 <= N; ++l1) {
      for (int r1 = l1; r1 <= N; ++r1) {
        if (partitionNums[l1][r1] != 0
            && (l1 == 1 || (partitionNums[1][l1 - 1] != 0 && minValues[1][l1 - 1] == 1))) {
          int r2 = maxValues[l1][r1];
          if (r2 == N || (partitionNums[r2 + 1][N] != 0 && maxValues[r2 + 1][N] == N)) {
            for (int l2 = r2; l2 > r1; --l2) {
              if (partitionNums[l2][r2] != 0 && minValues[l2][r2] == l1) {
                result =
                    Math.max(
                        result,
                        getPartitionNum(partitionNums, 1, l1 - 1)
                            + getPartitionNum(partitionNums, r1 + 1, l2 - 1)
                            + getPartitionNum(partitionNums, r2 + 1, N)
                            + 2);
              }
            }
          }
        }
      }
    }

    return result;
  }

  static int getPartitionNum(int[][] partitionNums, int beginIndex, int endIndex) {
    return (beginIndex <= endIndex) ? partitionNums[beginIndex][endIndex] : 0;
  }
}
