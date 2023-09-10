import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int H = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, H)));
    }

    sc.close();
  }

  static long solve(int[] A, int[] B, int H) {
    int N = A.length;

    int firstSize = N / 2;
    int secondSize = N - firstSize;

    NavigableMap<Long, List<Long>> aSumToBSums = buildASumToBSums(A, B, secondSize);

    long result = 0;
    for (Element element : buildElements(A, B, 0, firstSize - 1)) {
      result += computeBothGENum(aSumToBSums, H - element.aSum, H - element.bSum);
    }

    return result;
  }

  static long computeBothGENum(
      NavigableMap<Long, List<Long>> aSumToBSums, long minASum, long minBSum) {
    long result = 0;
    for (List<Long> bSums : aSumToBSums.tailMap(minASum).values()) {
      result += computeGENum(bSums, minBSum);
    }

    return result;
  }

  static int computeGENum(List<Long> bSums, long minBSum) {
    int beginIndex = bSums.size();
    int lowerIndex = 0;
    int upperIndex = bSums.size() - 1;
    while (lowerIndex <= upperIndex) {
      int middleIndex = (lowerIndex + upperIndex) / 2;
      if (bSums.get(middleIndex) >= minBSum) {
        beginIndex = middleIndex;
        upperIndex = middleIndex - 1;
      } else {
        lowerIndex = middleIndex + 1;
      }
    }

    return bSums.size() - beginIndex;
  }

  static NavigableMap<Long, List<Long>> buildASumToBSums(int[] A, int[] B, int secondSize) {
    int N = A.length;

    NavigableMap<Long, List<Long>> aSumToBSums = new TreeMap<>();
    for (Element element : buildElements(A, B, N - secondSize, N - 1)) {
      aSumToBSums.putIfAbsent(element.aSum, new ArrayList<>());
      aSumToBSums.get(element.aSum).add(element.bSum);
    }

    for (List<Long> bSums : aSumToBSums.values()) {
      Collections.sort(bSums);
    }

    return aSumToBSums;
  }

  static List<Element> buildElements(int[] A, int[] B, int beginIndex, int endIndex) {
    List<Element> result = new ArrayList<>();
    int size = endIndex - beginIndex + 1;
    for (int mask = powOfThree(size) - 1; mask >= 0; --mask) {
      int[] states = decode(size, mask);

      long aSum = 0;
      long bSum = 0;
      for (int i = 0; i < states.length; ++i) {
        if (states[i] == 0) {
          aSum += A[beginIndex + i];
        } else if (states[i] == 1) {
          bSum += B[beginIndex + i];
        } else {
          aSum += A[beginIndex + i];
          bSum += B[beginIndex + i];
        }
      }
      result.add(new Element(aSum, bSum));
    }

    return result;
  }

  static int powOfThree(int exponent) {
    return IntStream.range(0, exponent).reduce(1, (x, y) -> x * 3);
  }

  static int[] decode(int N, int mask) {
    int[] result = new int[N];
    for (int i = 0; i < result.length; ++i) {
      result[i] = mask % 3;
      mask /= 3;
    }

    return result;
  }
}

class Element {
  long aSum;
  long bSum;

  Element(long aSum, long bSum) {
    this.aSum = aSum;
    this.bSum = bSum;
  }
}
