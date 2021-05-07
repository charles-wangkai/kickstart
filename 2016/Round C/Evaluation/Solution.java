import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] assignments = new String[N];
      for (int i = 0; i < assignments.length; ++i) {
        assignments[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(assignments) ? "GOOD" : "BAD"));
    }

    sc.close();
  }

  static boolean solve(String[] assignments) {
    Set<String> assigned = new HashSet<>();
    Map<String, Set<String>> variableToNexts = new HashMap<>();
    for (String assignment : assignments) {
      String[] parts = assignment.split("[=(,)]");
      String next = parts[0];
      assigned.add(next);
      for (int i = 2; i < parts.length; ++i) {
        String variable = parts[i];
        if (!variableToNexts.containsKey(variable)) {
          variableToNexts.put(variable, new HashSet<>());
        }
        variableToNexts.get(variable).add(next);
      }
    }

    List<String> sorted = topologicalSort(variableToNexts);
    Map<String, Integer> variableToIndex =
        IntStream.range(0, sorted.size()).boxed().collect(Collectors.toMap(sorted::get, i -> i));

    for (String assignment : assignments) {
      String[] parts = assignment.split("[=(,)]");
      String next = parts[0];
      for (int i = 2; i < parts.length; ++i) {
        if (variableToIndex.get(parts[i]) >= variableToIndex.get(next)
            || !assigned.contains(parts[i])) {
          return false;
        }
      }
    }

    return true;
  }

  static List<String> topologicalSort(Map<String, Set<String>> variableToNexts) {
    List<String> sorted = new ArrayList<>();
    Set<String> visited = new HashSet<>();
    for (String variable : variableToNexts.keySet()) {
      if (!visited.contains(variable)) {
        search(sorted, variableToNexts, visited, variable);
      }
    }

    Collections.reverse(sorted);

    return sorted;
  }

  static void search(
      List<String> sorted,
      Map<String, Set<String>> variableToNexts,
      Set<String> visited,
      String variable) {
    visited.add(variable);

    for (String next : variableToNexts.getOrDefault(variable, Set.of())) {
      if (!visited.contains(next)) {
        search(sorted, variableToNexts, visited, next);
      }
    }

    sorted.add(variable);
  }
}
