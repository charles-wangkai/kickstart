import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int Np = sc.nextInt();
      int Ne = sc.nextInt();
      int Nt = sc.nextInt();
      int[] pGears = new int[Np];
      for (int i = 0; i < pGears.length; ++i) {
        pGears[i] = sc.nextInt();
      }
      int[] eGears = new int[Ne];
      for (int i = 0; i < eGears.length; ++i) {
        eGears[i] = sc.nextInt();
      }
      int[] tGears = new int[Nt];
      for (int i = 0; i < tGears.length; ++i) {
        tGears[i] = sc.nextInt();
      }
      int M = sc.nextInt();
      int[] P = new int[M];
      int[] Q = new int[M];
      for (int i = 0; i < M; ++i) {
        P[i] = sc.nextInt();
        Q[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(pGears, eGears, tGears, P, Q)));

      System.gc();
    }

    sc.close();
  }

  static String solve(int[] pGears, int[] eGears, int[] tGears, int[] P, int[] Q) {
    Set<Rational> extraRatios = new HashSet<>();
    for (int i = 0; i < eGears.length; ++i) {
      for (int j = i + 1; j < eGears.length; ++j) {
        extraRatios.add(new Rational(eGears[i], eGears[j]));
        extraRatios.add(new Rational(eGears[j], eGears[i]));
      }
    }

    return IntStream.range(0, P.length)
        .mapToObj(
            i -> {
              for (int pGear : pGears) {
                for (int tGear : tGears) {
                  if (extraRatios.contains(
                      new Rational((long) P[i] * tGear, (long) Q[i] * pGear))) {
                    return "Yes";
                  }
                }
              }

              return "No";
            })
        .collect(Collectors.joining("\n"));
  }
}

class Rational {
  long numerator;
  long denominator;

  Rational(long numerator, long denominator) {
    long g = gcd(numerator, denominator);

    this.numerator = numerator / g;
    this.denominator = denominator / g;
  }

  long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numerator, denominator);
  }

  @Override
  public boolean equals(Object obj) {
    Rational other = (Rational) obj;

    return numerator == other.numerator && denominator == other.denominator;
  }
}
