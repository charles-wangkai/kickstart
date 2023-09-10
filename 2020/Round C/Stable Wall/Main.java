import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] wall = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          wall[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(wall)));
    }

    sc.close();
  }

  static String solve(char[][] wall) {
    Map<Character, Integer> labelToIndegree = new HashMap<>();
    Map<Character, Set<Character>> labelToNexts = new HashMap<>();
    for (char[] line : wall) {
      for (char label : line) {
        if (!labelToIndegree.containsKey(label)) {
          labelToIndegree.put(label, 0);
          labelToNexts.put(label, new HashSet<>());
        }
      }
    }

    for (int r = 0; r < wall.length - 1; ++r) {
      for (int c = 0; c < wall[r].length; ++c) {
        if (wall[r + 1][c] != wall[r][c]
            && !labelToNexts.get(wall[r + 1][c]).contains(wall[r][c])) {
          labelToIndegree.put(wall[r][c], labelToIndegree.get(wall[r][c]) + 1);
          labelToNexts.get(wall[r + 1][c]).add(wall[r][c]);
        }
      }
    }

    Queue<Character> queue = new ArrayDeque<>();
    for (char label : labelToIndegree.keySet()) {
      if (labelToIndegree.get(label) == 0) {
        queue.offer(label);
      }
    }

    StringBuilder order = new StringBuilder();
    while (!queue.isEmpty()) {
      char head = queue.poll();
      order.append(head);

      for (char next : labelToNexts.get(head)) {
        labelToIndegree.put(next, labelToIndegree.get(next) - 1);
        if (labelToIndegree.get(next) == 0) {
          queue.offer(next);
        }
      }
    }

    return (order.length() == labelToIndegree.size()) ? order.toString() : "-1";
  }
}
