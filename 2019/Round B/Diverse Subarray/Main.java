// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050eda/00000000001198c1#analysis

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
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
    Node[] nodes = new Node[1 << (Integer.toBinaryString(A.length).length() + 1)];
    for (int i = 0; i < nodes.length; ++i) {
      nodes[i] = new Node();
    }

    Map<Integer, List<Integer>> typeToIndices = new HashMap<>();
    Map<Integer, Integer> typeToBegin = new HashMap<>();
    for (int i = 0; i < A.length; ++i) {
      if (!typeToIndices.containsKey(A[i])) {
        typeToIndices.put(A[i], new ArrayList<>());
        typeToBegin.put(A[i], 0);
      }
      typeToIndices.get(A[i]).add(i);

      int event;
      if (typeToIndices.get(A[i]).size() <= S) {
        event = 1;
      } else if (typeToIndices.get(A[i]).size() == S + 1) {
        event = -S;
      } else {
        event = 0;
      }
      update(nodes, i, event, 0, 0, A.length - 1);
    }

    int result = nodes[0].maxPrefixSum;
    for (int i = 1; i < A.length; ++i) {
      update(nodes, i - 1, 0, 0, 0, A.length - 1);

      typeToBegin.put(A[i - 1], typeToBegin.get(A[i - 1]) + 1);

      List<Integer> indices = typeToIndices.get(A[i - 1]);
      if (typeToBegin.get(A[i - 1]) + S - 1 < indices.size()) {
        update(nodes, indices.get(typeToBegin.get(A[i - 1]) + S - 1), 1, 0, 0, A.length - 1);
      }
      if (typeToBegin.get(A[i - 1]) + S < indices.size()) {
        update(nodes, indices.get(typeToBegin.get(A[i - 1]) + S), -S, 0, 0, A.length - 1);
      }

      result = Math.max(result, nodes[0].maxPrefixSum);
    }

    return result;
  }

  static void update(Node[] nodes, int pos, int value, int index, int lower, int upper) {
    if (lower == upper) {
      nodes[index].sum = value;
      nodes[index].maxPrefixSum = value;

      return;
    }

    int middle = (lower + upper) / 2;
    if (pos <= middle) {
      update(nodes, pos, value, index * 2 + 1, lower, middle);
    } else {
      update(nodes, pos, value, index * 2 + 2, middle + 1, upper);
    }

    nodes[index].sum = nodes[index * 2 + 1].sum + nodes[index * 2 + 2].sum;
    nodes[index].maxPrefixSum =
        Math.max(
            nodes[index * 2 + 1].maxPrefixSum,
            nodes[index * 2 + 1].sum + nodes[index * 2 + 2].maxPrefixSum);
  }
}

class Node {
  int sum;
  int maxPrefixSum;
}
