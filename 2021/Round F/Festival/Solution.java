import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int D = sc.nextInt();
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] h = new int[N];
      int[] s = new int[N];
      int[] e = new int[N];
      for (int i = 0; i < N; ++i) {
        h[i] = sc.nextInt();
        s[i] = sc.nextInt();
        e[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(D, h, s, e, K)));
    }

    sc.close();
  }

  static long solve(int D, int[] h, int[] s, int[] e, int K) {
    PriorityQueue<Event> events = new PriorityQueue<>(Comparator.comparing(event -> event.day));
    for (int i = 0; i < h.length; ++i) {
      events.add(new Event(s[i], true, i));
      events.add(new Event(e[i] + 1, false, i));
    }

    long result = -1;
    Comparator<Integer> attractionComparator =
        Comparator.comparing((Integer i) -> h[i]).thenComparing(Function.identity());
    SortedSet<Integer> chosen = new TreeSet<>(attractionComparator);
    long chosenSum = 0;
    SortedSet<Integer> nonChosen = new TreeSet<>(attractionComparator);
    for (int day = 1; day <= D; ++day) {
      while (!events.isEmpty() && events.peek().day == day) {
        Event event = events.poll();
        if (event.startOrEnd) {
          chosen.add(event.attractionIndex);
          chosenSum += h[event.attractionIndex];

          if (chosen.size() == K + 1) {
            int first = chosen.first();
            chosen.remove(first);
            chosenSum -= h[first];

            nonChosen.add(first);
          }
        } else if (nonChosen.contains(event.attractionIndex)) {
          nonChosen.remove(event.attractionIndex);
        } else {
          chosen.remove(event.attractionIndex);
          chosenSum -= h[event.attractionIndex];

          if (chosen.size() == K - 1 && !nonChosen.isEmpty()) {
            int last = nonChosen.last();
            nonChosen.remove(last);

            chosen.add(last);
            chosenSum += h[last];
          }
        }
      }

      result = Math.max(result, chosenSum);
    }

    return result;
  }
}

class Event {
  int day;
  boolean startOrEnd;
  int attractionIndex;

  Event(int day, boolean startOrEnd, int attractionIndex) {
    this.day = day;
    this.startOrEnd = startOrEnd;
    this.attractionIndex = attractionIndex;
  }
}