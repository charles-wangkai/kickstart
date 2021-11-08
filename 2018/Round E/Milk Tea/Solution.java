import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      sc.nextInt();
      String[] friends = new String[N];
      for (int i = 0; i < friends.length; ++i) {
        friends[i] = sc.next();
      }
      String[] forbiddens = new String[M];
      for (int i = 0; i < forbiddens.length; ++i) {
        forbiddens[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(friends, forbiddens)));
    }

    sc.close();
  }

  static int solve(String[] friends, String[] forbiddens) {
    int P = friends[0].length();

    int[][] counts = new int[P][2];
    for (String friend : friends) {
      for (int i = 0; i < P; ++i) {
        ++counts[i][friend.charAt(i) - '0'];
      }
    }

    Set<String> forbiddenSet = Arrays.stream(forbiddens).collect(Collectors.toSet());

    Set<String> seen = new HashSet<>();
    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.complaintNum));
    Element minElement = buildMinElement(counts);
    seen.add(minElement.type);
    pq.offer(minElement);
    while (true) {
      Element head = pq.poll();
      if (!forbiddenSet.contains(head.type)) {
        return head.complaintNum;
      }

      for (int i = 0; i < P; ++i) {
        int nextDigit = 1 - (head.type.charAt(i) - '0');
        String nextType =
            String.format(
                "%s%d%s", head.type.substring(0, i), nextDigit, head.type.substring(i + 1));
        if (!seen.contains(nextType)) {
          int nextComplaintNum =
              head.complaintNum + (counts[i][1 - nextDigit] - counts[i][nextDigit]);

          seen.add(nextType);
          pq.offer(new Element(nextType, nextComplaintNum));
        }
      }
    }
  }

  static Element buildMinElement(int[][] counts) {
    StringBuilder type = new StringBuilder();
    int complaintNum = 0;
    for (int i = 0; i < counts.length; ++i) {
      if (counts[i][0] >= counts[i][1]) {
        type.append(0);
        complaintNum += counts[i][1];
      } else {
        type.append(1);
        complaintNum += counts[i][0];
      }
    }

    return new Element(type.toString(), complaintNum);
  }
}

class Element {
  String type;
  int complaintNum;

  Element(String type, int complaintNum) {
    this.type = type;
    this.complaintNum = complaintNum;
  }
}
