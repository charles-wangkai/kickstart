import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int S = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, S)));
    }

    sc.close();
  }

  static int solve(int[] A, int S) {
    int result = 0;
    for (int i = 0; i < A.length; ++i) {
      int takeNum = 0;
      Map<Integer, Integer> typeToCount = new HashMap<>();
      for (int j = i; j < A.length; ++j) {
        typeToCount.put(A[j], typeToCount.getOrDefault(A[j], 0) + 1);
        if (typeToCount.get(A[j]) <= S) {
          ++takeNum;
          result = Math.max(result, takeNum);
        } else if (typeToCount.get(A[j]) == S + 1) {
          takeNum -= S;
        }
      }
    }

    return result;
  }
}