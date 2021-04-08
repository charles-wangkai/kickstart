import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] SN = new int[N];
      int[] W = new int[N];
      int[][] Time = new int[N][];
      for (int i = 0; i < N; ++i) {
        SN[i] = sc.nextInt();
        W[i] = sc.nextInt();
        Time[i] = new int[SN[i] - 1];
        for (int j = 0; j < Time[i].length; ++j) {
          Time[i][j] = sc.nextInt();
        }
      }
      int M = sc.nextInt();
      int[] m1 = new int[M];
      int[] s1 = new int[M];
      int[] m2 = new int[M];
      int[] s2 = new int[M];
      int[] t = new int[M];
      for (int i = 0; i < M; ++i) {
        m1[i] = sc.nextInt() - 1;
        s1[i] = sc.nextInt() - 1;
        m2[i] = sc.nextInt() - 1;
        s2[i] = sc.nextInt() - 1;
        t[i] = sc.nextInt();
      }
      int Q = sc.nextInt();
      int[] x1 = new int[Q];
      int[] y1 = new int[Q];
      int[] x2 = new int[Q];
      int[] y2 = new int[Q];
      for (int i = 0; i < Q; ++i) {
        x1[i] = sc.nextInt() - 1;
        y1[i] = sc.nextInt() - 1;
        x2[i] = sc.nextInt() - 1;
        y2[i] = sc.nextInt() - 1;
      }

      System.out.println(
          String.format("Case #%d:\n%s", tc, solve(Time, W, m1, s1, m2, s2, t, x1, y1, x2, y2)));
    }

    sc.close();
  }

  static String solve(
      int[][] Time,
      int[] W,
      int[] m1,
      int[] s1,
      int[] m2,
      int[] s2,
      int[] t,
      int[] x1,
      int[] y1,
      int[] x2,
      int[] y2) {
    return IntStream.range(0, x1.length)
        .mapToObj(
            i ->
                String.valueOf(
                    computeMinDistance(Time, W, m1, s1, m2, s2, t, x1[i], y1[i], x2[i], y2[i])))
        .collect(Collectors.joining("\n"));
  }

  static int computeMinDistance(
      int[][] Time,
      int[] W,
      int[] m1,
      int[] s1,
      int[] m2,
      int[] s2,
      int[] t,
      int beginX,
      int beginY,
      int endX,
      int endY) {
    int[][] distances = new int[Time.length][];
    for (int i = 0; i < distances.length; ++i) {
      distances[i] = new int[Time[i].length + 1];
      Arrays.fill(distances[i], Integer.MAX_VALUE);
    }

    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.distance));
    pq.offer(new Element(beginX, beginY, 0));

    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (distances[head.line][head.station] == Integer.MAX_VALUE) {
        distances[head.line][head.station] = head.distance;

        int travel = W[head.line];
        for (int i = head.station - 1;
            i >= 0 && distances[head.line][i] == Integer.MAX_VALUE;
            --i) {
          travel += Time[head.line][i];
          pq.offer(new Element(head.line, i, distances[head.line][head.station] + travel));
        }

        travel = W[head.line];
        for (int i = head.station + 1;
            i < distances[head.line].length && distances[head.line][i] == Integer.MAX_VALUE;
            ++i) {
          travel += Time[head.line][i - 1];
          pq.offer(new Element(head.line, i, distances[head.line][head.station] + travel));
        }

        for (int i = 0; i < m1.length; ++i) {
          if (head.line == m1[i] && head.station == s1[i]) {
            pq.offer(new Element(m2[i], s2[i], distances[head.line][head.station] + t[i]));
          } else if (head.line == m2[i] && head.station == s2[i]) {
            pq.offer(new Element(m1[i], s1[i], distances[head.line][head.station] + t[i]));
          }
        }
      }
    }

    return (distances[endX][endY] == Integer.MAX_VALUE) ? -1 : distances[endX][endY];
  }
}

class Element {
  int line;
  int station;
  int distance;

  Element(int line, int station, int distance) {
    this.line = line;
    this.station = station;
    this.distance = distance;
  }
}
