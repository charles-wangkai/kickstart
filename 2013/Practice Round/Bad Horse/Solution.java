import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int M = sc.nextInt();
      String[] u = new String[M];
      String[] v = new String[M];
      for (int i = 0; i < M; ++i) {
        u[i] = sc.next();
        v[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(u, v) ? "Yes" : "No"));
    }

    sc.close();
  }

  static boolean solve(String[] u, String[] v) {
    Map<String, List<String>> nameToAdjs = new HashMap<>();
    for (int i = 0; i < u.length; ++i) {
      addMap(nameToAdjs, u[i], v[i]);
      addMap(nameToAdjs, v[i], u[i]);
    }

    Map<String, Boolean> nameToGroupOne = new HashMap<>();
    for (String name : nameToAdjs.keySet()) {
      if (!nameToGroupOne.containsKey(name) && !assign(nameToGroupOne, nameToAdjs, name, true)) {
        return false;
      }
    }

    return true;
  }

  static boolean assign(
      Map<String, Boolean> nameToGroupOne,
      Map<String, List<String>> nameToAdjs,
      String name,
      boolean groupOne) {
    if (nameToGroupOne.containsKey(name)) {
      return nameToGroupOne.get(name) == groupOne;
    }
    nameToGroupOne.put(name, groupOne);

    for (String adj : nameToAdjs.get(name)) {
      if (!assign(nameToGroupOne, nameToAdjs, adj, !groupOne)) {
        return false;
      }
    }

    return true;
  }

  static void addMap(Map<String, List<String>> nameToAdjs, String name, String adj) {
    if (!nameToAdjs.containsKey(name)) {
      nameToAdjs.put(name, new ArrayList<>());
    }

    nameToAdjs.get(name).add(adj);
  }
}
