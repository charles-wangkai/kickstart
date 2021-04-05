import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] answered = new String[N];
      for (int i = 0; i < answered.length; ++i) {
        answered[i] = sc.next();
      }
      int Q = sc.nextInt();
      String[] questions = new String[Q];
      for (int i = 0; i < questions.length; ++i) {
        questions[i] = sc.next();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(answered, questions)));
    }

    sc.close();
  }

  static String solve(String[] answered, String[] questions) {
    String[] variables =
        Arrays.stream(answered)
            .flatMap(
                s -> {
                  String[] parts = s.split("[+=]");

                  return Stream.of(parts[0], parts[1]);
                })
            .distinct()
            .toArray(String[]::new);
    int variableNum = variables.length;
    Map<String, Integer> variableToIndex =
        IntStream.range(0, variableNum)
            .boxed()
            .collect(Collectors.toMap(i -> variables[i], i -> i));

    @SuppressWarnings("unchecked")
    Map<Integer, Integer>[] edgeMaps = new Map[variableNum];
    for (int i = 0; i < edgeMaps.length; ++i) {
      edgeMaps[i] = new HashMap<>();
    }
    for (String s : answered) {
      String[] parts = s.split("[+=]");

      int index1 = variableToIndex.get(parts[0]);
      int index2 = variableToIndex.get(parts[1]);
      int sum = Integer.parseInt(parts[2]);

      edgeMaps[index1].put(index2, sum);
      edgeMaps[index2].put(index1, sum);
    }

    List<String> result = new ArrayList<>();
    for (String s : questions) {
      String[] parts = s.split("\\+");
      if (variableToIndex.containsKey(parts[0]) && variableToIndex.containsKey(parts[1])) {
        int index1 = variableToIndex.get(parts[0]);
        int index2 = variableToIndex.get(parts[1]);

        Integer sum = computeSum(edgeMaps, index1, index2);
        if (sum != null) {
          result.add(String.format("%s=%d", s, sum));
        } else {
          Integer sum1 = computeSum(edgeMaps, index1, index1);
          Integer sum2 = computeSum(edgeMaps, index2, index2);
          if (sum1 != null && sum2 != null) {
            result.add(String.format("%s=%d", s, (sum1 + sum2) / 2));
          }
        }
      }
    }

    return String.join("\n", result);
  }

  static Integer computeSum(Map<Integer, Integer>[] edgeMaps, int fromNode, int toNode) {
    int variableNum = edgeMaps.length;

    Integer[] posSums = new Integer[variableNum];
    Integer[] negSums = new Integer[variableNum];
    search(edgeMaps, posSums, negSums, fromNode, false, 0);

    return posSums[toNode];
  }

  static void search(
      Map<Integer, Integer>[] edgeMaps,
      Integer[] posSums,
      Integer[] negSums,
      int node,
      boolean posOrNeg,
      int sum) {
    if (posOrNeg) {
      if (posSums[node] == null) {
        posSums[node] = sum;

        for (int adj : edgeMaps[node].keySet()) {
          search(edgeMaps, posSums, negSums, adj, false, sum - edgeMaps[node].get(adj));
        }
      }
    } else {
      if (negSums[node] == null) {
        negSums[node] = sum;

        for (int adj : edgeMaps[node].keySet()) {
          search(edgeMaps, posSums, negSums, adj, true, sum + edgeMaps[node].get(adj));
        }
      }
    }
  }
}
