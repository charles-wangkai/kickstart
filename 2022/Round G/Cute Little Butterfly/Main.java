// https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb2e1/0000000000c17b68#analysis

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
  static final int X_LIMIT = 100000;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int E = sc.nextInt();
      int[] X = new int[N];
      int[] Y = new int[N];
      int[] C = new int[N];
      for (int i = 0; i < N; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
        C[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(X, Y, C, E)));
    }

    sc.close();
  }

  static long solve(int[] X, int[] Y, int[] C, int E) {
    SortedMap<Integer, NavigableMap<Integer, Integer>> yToFlowers =
        new TreeMap<>(Comparator.reverseOrder());
    for (int i = 0; i < X.length; ++i) {
      yToFlowers.putIfAbsent(Y[i], new TreeMap<>());
      yToFlowers.get(Y[i]).put(X[i], C[i]);
    }

    NavigableMap<Integer, Long> rightXToEnergy = new TreeMap<>();
    rightXToEnergy.put(0, 0L);
    NavigableMap<Integer, Long> leftXToEnergy = new TreeMap<>();
    leftXToEnergy.put(X_LIMIT, -(long) E);
    for (NavigableMap<Integer, Integer> flowers : yToFlowers.values()) {
      long rightEnergy = rightXToEnergy.lastEntry().getValue();

      boolean rightFirst = true;
      for (int x : flowers.keySet()) {
        long energy = rightXToEnergy.floorEntry(x).getValue() + flowers.get(x);
        if (rightFirst) {
          energy = Math.max(energy, leftXToEnergy.firstEntry().getValue() - E + flowers.get(x));
        }
        rightXToEnergy.put(x, energy);

        while (true) {
          Entry<Integer, Long> entry = rightXToEnergy.higherEntry(x);
          if (entry == null || entry.getValue() > energy) {
            break;
          }

          rightXToEnergy.remove(entry.getKey());
        }

        rightFirst = false;
      }

      boolean leftFirst = true;
      for (int x : flowers.descendingKeySet()) {
        long energy = leftXToEnergy.ceilingEntry(x).getValue() + flowers.get(x);
        if (leftFirst) {
          energy = Math.max(energy, rightEnergy - E + flowers.get(x));
        }
        leftXToEnergy.put(x, energy);

        while (true) {
          Entry<Integer, Long> entry = leftXToEnergy.lowerEntry(x);
          if (entry == null || entry.getValue() > energy) {
            break;
          }

          leftXToEnergy.remove(entry.getKey());
        }

        leftFirst = false;
      }
    }

    return Math.max(
        rightXToEnergy.values().stream().mapToLong(x -> x).max().getAsLong(),
        leftXToEnergy.values().stream().mapToLong(x -> x).max().getAsLong());
  }
}
