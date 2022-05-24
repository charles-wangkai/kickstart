import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  static final int MODULUS = 1_000_000_007;

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

  static int solve(String S) {
    List<Integer> candyNums = new ArrayList<>();
    search(S, candyNums, IntStream.range(0, S.length()).toArray(), 0);

    return divideMod(candyNums.stream().mapToInt(x -> x).sum(), candyNums.size());
  }

  static void search(String S, List<Integer> candyNums, int[] order, int index) {
    if (index == order.length) {
      candyNums.add(computeCandyNum(S, order));

      return;
    }

    for (int i = index; i < order.length; ++i) {
      swap(order, i, index);
      search(S, candyNums, order, index + 1);
      swap(order, i, index);
    }
  }

  static int computeCandyNum(String S, int[] order) {
    boolean[] deleted = new boolean[S.length()];
    int result = 0;
    for (int index : order) {
      deleted[index] = true;
      if (isPalindrome(
          IntStream.range(0, S.length())
              .filter(i -> !deleted[i])
              .map(i -> S.charAt(i))
              .mapToObj(c -> String.valueOf((char) c))
              .collect(Collectors.joining()))) {
        ++result;
      }
    }

    return result;
  }

  static boolean isPalindrome(String s) {
    return new StringBuilder(s).reverse().toString().equals(s);
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }

  static int divideMod(int x, int y) {
    return multiplyMod(x, modInv(y));
  }

  static int modInv(int x) {
    return BigInteger.valueOf(x).modInverse(BigInteger.valueOf(MODULUS)).intValue();
  }
}