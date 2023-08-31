import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int P = sc.nextInt();
      int[] S = new int[P];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }
      int N = sc.nextInt();
      int[] W = new int[N];
      String[][] names = new String[N][P];
      for (int i = 0; i < N; ++i) {
        W[i] = sc.nextInt();
        for (int j = 0; j < P; ++j) {
          names[i][j] = sc.next();
        }
      }
      int M = sc.nextInt();

      System.out.println(String.format("Case #%d:\n%s", tc, solve(S, W, names, M)));
    }

    sc.close();
  }

  static String solve(int[] S, int[] W, String[][] names, int M) {
    int P = S.length;
    int N = W.length;

    Map<String, List<Integer>> nameToScores = new HashMap<>();
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < P; ++j) {
        if (!nameToScores.containsKey(names[i][j])) {
          nameToScores.put(names[i][j], new ArrayList<>());
        }
        nameToScores.get(names[i][j]).add(W[i] * S[j]);
      }
    }
    Element[] elements =
        nameToScores.keySet().stream()
            .map(
                name ->
                    new Element(
                        name,
                        nameToScores.get(name).stream()
                            .sorted(Comparator.reverseOrder())
                            .limit(M)
                            .mapToInt(x -> x)
                            .sum()))
            .sorted(
                Comparator.comparing((Element e) -> e.totalScore)
                    .reversed()
                    .thenComparing(e -> e.name))
            .toArray(Element[]::new);

    int prevRank = -1;
    int prevTotalScore = -1;
    List<String> result = new ArrayList<>();
    for (int i = 0; i < elements.length; ++i) {
      int rank = (elements[i].totalScore == prevTotalScore) ? prevRank : (i + 1);

      result.add(String.format("%d: %s", rank, elements[i].name));

      prevRank = rank;
      prevTotalScore = elements[i].totalScore;
    }

    return String.join("\n", result);
  }
}

class Element {
  String name;
  int totalScore;

  Element(String name, int totalScore) {
    this.name = name;
    this.totalScore = totalScore;
  }
}
