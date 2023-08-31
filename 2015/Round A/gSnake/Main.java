import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int S = sc.nextInt();
      int R = sc.nextInt();
      int C = sc.nextInt();
      int[] X = new int[S];
      char[] A = new char[S];
      for (int i = 0; i < S; ++i) {
        X[i] = sc.nextInt();
        A[i] = sc.next().charAt(0);
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, A, R, C)));
    }

    sc.close();
  }

  static int solve(int[] X, char[] A, int R, int C) {
    int direction = 1;
    Deque<Point> deque = new ArrayDeque<>();
    deque.offer(new Point(0, 0));
    Set<Point> body = new HashSet<>();
    body.add(new Point(0, 0));
    Set<Point> eaten = new HashSet<>();

    int timeLimit = X[X.length - 1] + Math.max(R, C);
    int actionIndex = 0;
    for (int time = 0; time <= timeLimit; ++time) {
      if (actionIndex != X.length && time == X[actionIndex]) {
        if (A[actionIndex] == 'L') {
          direction = (direction - 1 + R_OFFSETS.length) % R_OFFSETS.length;
        } else {
          direction = (direction + 1) % R_OFFSETS.length;
        }

        ++actionIndex;
      }

      Point oldHead = deque.peekFirst();
      Point newHead =
          new Point(
              (oldHead.r + R_OFFSETS[direction] + R) % R,
              (oldHead.c + C_OFFSETS[direction] + C) % C);
      if ((newHead.r + newHead.c) % 2 != 0 && !eaten.contains(newHead)) {
        deque.offerFirst(newHead);
        body.add(newHead);
        eaten.add(newHead);
      } else {
        body.remove(deque.pollLast());

        deque.offerFirst(newHead);
        if (!body.add(newHead)) {
          break;
        }
      }
    }

    return deque.size();
  }
}

class Point {
  int r;
  int c;

  Point(int r, int c) {
    this.r = r;
    this.c = c;
  }

  @Override
  public int hashCode() {
    return Objects.hash(r, c);
  }

  @Override
  public boolean equals(Object obj) {
    Point other = (Point) obj;

    return r == other.r && c == other.c;
  }
}
