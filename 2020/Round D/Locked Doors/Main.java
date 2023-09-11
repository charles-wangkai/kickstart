import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      int[] D = new int[N - 1];
      for (int i = 0; i < D.length; ++i) {
        D[i] = sc.nextInt();
      }
      int[] S = new int[Q];
      int[] K = new int[Q];
      for (int i = 0; i < Q; ++i) {
        S[i] = sc.nextInt() - 1;
        K[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(D, S, K)));
    }

    sc.close();
  }

  static String solve(int[] D, int[] S, int[] K) {
    int[] sortedIndices =
        IntStream.range(0, S.length)
            .boxed()
            .sorted(Comparator.comparing(i -> S[i]))
            .mapToInt(x -> x)
            .toArray();

    int[] rightGreaterIndices = new int[D.length];
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = rightGreaterIndices.length - 1; i >= 0; --i) {
      while (!stack.isEmpty() && D[i] > D[stack.peek()]) {
        stack.pop();
      }

      rightGreaterIndices[i] = stack.isEmpty() ? D.length : stack.peek();
      stack.push(i);
    }

    int start = 0;
    List<Integer> leftIndices = new ArrayList<>();
    List<Integer> rightIndices = new ArrayList<>();
    for (int i = 0; i < D.length; ++i) {
      if (rightIndices.isEmpty() || D[i] > D[rightIndices.get(rightIndices.size() - 1)]) {
        rightIndices.add(i);
      }
    }
    Collections.reverse(rightIndices);

    int[] result = new int[S.length];
    for (int index : sortedIndices) {
      while (start != S[index]) {
        ++start;
        int movedIndex = start - 1;

        while (!leftIndices.isEmpty()
            && D[leftIndices.get(leftIndices.size() - 1)] < D[movedIndex]) {
          leftIndices.remove(leftIndices.size() - 1);
        }
        leftIndices.add(movedIndex);

        rightIndices.remove(rightIndices.size() - 1);
        List<Integer> tail = new ArrayList<>();
        int current = movedIndex + 1;
        while (current
            < (rightIndices.isEmpty() ? D.length : rightIndices.get(rightIndices.size() - 1))) {
          tail.add(current);
          current = rightGreaterIndices[current];
        }
        Collections.reverse(tail);
        rightIndices.addAll(tail);
      }

      result[index] = findRoom(D, S[index], K[index], leftIndices, rightIndices);
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static int findRoom(
      int[] D, int start, int k, List<Integer> leftIndices, List<Integer> rightIndices) {
    if (k == 1) {
      return start + 1;
    }

    int room = findFromLeft(D, start, k, leftIndices, rightIndices);
    if (room == -1) {
      room = findFromRight(D, start, k, leftIndices, rightIndices);
    }

    return room + 1;
  }

  static int findFromLeft(
      int[] D, int start, int k, List<Integer> leftIndices, List<Integer> rightIndices) {
    int lower = 0;
    int upper = leftIndices.size() - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;

      int rightIndex = findGreater(D, rightIndices, leftIndices.get(middle));
      int rightNum = ((rightIndex == -1) ? D.length : rightIndices.get(rightIndex)) - start;

      int leftNum = start - 1 - leftIndices.get(middle);
      if (leftNum + rightNum + 1 >= k) {
        lower = middle + 1;
      } else {
        int nextLeftNum = start - 1 - ((middle == 0) ? -1 : leftIndices.get(middle - 1));
        if (nextLeftNum + rightNum + 1 >= k) {
          return leftIndices.get(middle) + 1 - (k - (leftNum + rightNum + 1));
        }

        upper = middle - 1;
      }
    }

    return -1;
  }

  static int findFromRight(
      int[] D, int start, int k, List<Integer> leftIndices, List<Integer> rightIndices) {
    int lower = 0;
    int upper = rightIndices.size() - 1;
    while (true) {
      int middle = (lower + upper) / 2;

      int leftIndex = findGreater(D, leftIndices, rightIndices.get(middle));
      int leftNum = start - 1 - ((leftIndex == -1) ? -1 : leftIndices.get(leftIndex));

      int rightNum = rightIndices.get(middle) - start;
      if (leftNum + rightNum + 1 >= k) {
        lower = middle + 1;
      } else {
        int nextRightNum = ((middle == 0) ? D.length : rightIndices.get(middle - 1)) - start;
        if (leftNum + nextRightNum + 1 >= k) {
          return rightIndices.get(middle) + (k - (leftNum + rightNum + 1));
        }

        upper = middle - 1;
      }
    }
  }

  static int findGreater(int[] D, List<Integer> indices, int baseIndex) {
    int result = -1;
    int lower = 0;
    int upper = indices.size() - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (D[indices.get(middle)] > D[baseIndex]) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }
}
