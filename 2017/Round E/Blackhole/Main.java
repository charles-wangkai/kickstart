// https://github.com/kamyu104/GoogleKickStart-2017/blob/main/Round%20E/blackhole.py

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int ITERATION_NUM = 100;
  static final Point Z_AXIS = new Point(0, 0, 1);

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int[] X = new int[3];
      int[] Y = new int[3];
      int[] Z = new int[3];
      for (int i = 0; i < 3; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
        Z[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(X, Y, Z)));
    }

    sc.close();
  }

  static double solve(int[] X, int[] Y, int[] Z) {
    Point[] points = rotateToXYPlane(X, Y, Z);

    double result = -1;
    double lower = 0;
    double upper =
        Math.max(
            Math.max(length(vector(points[0], points[1])), length(vector(points[1], points[2]))),
            length(vector(points[2], points[0])));
    for (int i = 0; i < ITERATION_NUM; ++i) {
      double middle = (lower + upper) / 2;
      if (check(points[0], points[1], points[2], middle)) {
        result = middle;
        upper = middle;
      } else {
        lower = middle;
      }
    }

    return result;
  }

  static boolean check(Point a, Point b, Point c, double r) {
    return checkOrder(a, b, c, r) || checkOrder(b, c, a, r) || checkOrder(c, a, b, r);
  }

  static boolean checkOrder(Point a, Point b, Point c, double r) {
    return hasCommon(new Circle(a, r), new Circle(b, 3 * r), new Circle(c, 3 * r))
        || hasCommon(new Circle(a, 5 * r), new Circle(b, r), new Circle(c, r));
  }

  static boolean hasCommon(Circle c1, Circle c2, Circle c3) {
    return intersect(c1, c2, c3) || intersect(c2, c3, c1) || intersect(c3, c1, c2);
  }

  static boolean intersect(Circle c1, Circle c2, Circle c3) {
    if (c1.radius > c2.radius) {
      return intersect(c2, c1, c3);
    }

    Point[] intersections = circleIntersect(c1, c2);
    if (intersections.length == 0) {
      return false;
    }
    if (intersections.length == 2) {
      return circleContain(c3, intersections[0]) || circleContain(c3, intersections[1]);
    }

    return circleIntersect(c1, c3).length != 0;
  }

  // http://paulbourke.net/geometry/circlesphere/
  static Point[] circleIntersect(Circle c1, Circle c2) {
    double dx = c2.center.x - c1.center.x;
    double dy = c2.center.y - c1.center.y;
    double d = Math.sqrt(dx * dx + dy * dy);
    if (d > c1.radius + c2.radius) {
      return new Point[0];
    }
    if (d > c2.radius - c1.radius) {
      double chordDist = (c1.radius * c1.radius - c2.radius * c2.radius + d * d) / (2 * d);
      double halfChordLen = Math.sqrt(c1.radius * c1.radius - chordDist * chordDist);

      double chordMidX = c1.center.x + chordDist * dx / d;
      double chordMidY = c1.center.y + chordDist * dy / d;

      return new Point[] {
        new Point(chordMidX + halfChordLen * dy / d, chordMidY - halfChordLen * dx / d, 0),
        new Point(chordMidX - halfChordLen * dy / d, chordMidY + halfChordLen * dx / d, 0)
      };
    }

    return new Point[3];
  }

  static boolean circleContain(Circle c, Point p) {
    return Math.sqrt(
            (p.x - c.center.x) * (p.x - c.center.x) + (p.y - c.center.y) * (p.y - c.center.y))
        <= c.radius;
  }

  static Point[] rotateToXYPlane(int[] X, int[] Y, int[] Z) {
    Point[] points =
        IntStream.range(0, X.length)
            .mapToObj(i -> new Point(X[i], Y[i], Z[i]))
            .toArray(Point[]::new);

    Point v = normalize(normalVector(vector(points[0], points[1]), vector(points[0], points[2])));
    Point u = normalize(normalVector(v, Z_AXIS));
    double theta = angle(v, Z_AXIS, u);

    return Arrays.stream(points).map(p -> rotate(u, -theta, p)).toArray(Point[]::new);
  }

  static Point rotate(Point u, double theta, Point p) {
    double sinx = Math.sin(theta);
    double cosx = Math.cos(theta);

    double[][] rotationMatrix = {
      {
        cosx + u.x * u.x * (1 - cosx),
        u.x * u.y * (1 - cosx) - u.z * sinx,
        u.x * u.z * (1 - cosx) + u.y * sinx
      },
      {
        u.x * u.y * (1 - cosx) + u.z * sinx,
        cosx + u.y * u.y * (1 - cosx),
        u.y * u.z * (1 - cosx) - u.x * sinx
      },
      {
        u.x * u.z * (1 - cosx) - u.y * sinx,
        u.y * u.z * (1 - cosx) + u.x * sinx,
        cosx + u.z * u.z * (1 - cosx)
      }
    };
    double[] rotated = matrixMulti(new double[] {p.x, p.y, p.z}, rotationMatrix);

    return new Point(rotated[0], rotated[1], rotated[2]);
  }

  static double[] matrixMulti(double[] v, double[][] m) {
    double[] result = new double[m[0].length];
    for (int i = 0; i < result.length; ++i) {
      for (int j = 0; j < v.length; ++j) {
        result[i] += v[j] * m[j][i];
      }
    }

    return result;
  }

  static double innerProduct(Point a, Point b) {
    return a.x * b.x + a.y * b.y + a.z * b.z;
  }

  // https://stackoverflow.com/questions/5188561/signed-angle-between-two-3d-vectors-with-same-origin-within-the-same-plane/33920320#33920320
  static double angle(Point a, Point b, Point normal) {
    return Math.atan2(innerProduct(outerProduct(a, b), normal), innerProduct(a, b));
  }

  static Point normalize(Point a) {
    double l = length(a);

    return new Point(a.x / l, a.y / l, a.z / l);
  }

  static Point outerProduct(Point a, Point b) {
    return new Point(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
  }

  static Point normalVector(Point a, Point b) {
    Point normal = outerProduct(a, b);
    if (normal.x != 0 || normal.y != 0 || normal.z != 0) {
      return normal;
    }

    if (a.x == 0) {
      return new Point(1, 0, 0);
    }
    if (a.y == 0) {
      return new Point(0, 1, 0);
    }
    if (a.z == 0) {
      return new Point(0, 0, 1);
    }

    return new Point(a.y, -a.x, 0);
  }

  static double length(Point p) {
    return Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
  }

  static Point vector(Point a, Point b) {
    return new Point(b.x - a.x, b.y - a.y, b.z - a.z);
  }
}

class Point {
  double x;
  double y;
  double z;

  Point(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}

class Circle {
  Point center;
  double radius;

  Circle(Point center, double radius) {
    this.center = center;
    this.radius = radius;
  }
}
