import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  static final int ALPHABET_SIZE = 26;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(S)));
    }

    sc.close();
  }

  static String solve(String S) {
    @SuppressWarnings("unchecked")
    List<Integer>[] indexLists = new List[ALPHABET_SIZE];
    for (int i = 0; i < indexLists.length; ++i) {
      indexLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < S.length(); ++i) {
      indexLists[S.charAt(i) - 'a'].add(i);
    }

    int offset = Arrays.stream(indexLists).mapToInt(List::size).max().getAsInt();
    if (offset * 2 > S.length()) {
      return "IMPOSSIBLE";
    }

    List<Integer> indices = new ArrayList<>();
    for (List<Integer> indexList : indexLists) {
      indices.addAll(indexList);
    }

    char[] result = new char[S.length()];
    for (int i = 0; i < indices.size(); ++i) {
      result[indices.get(i)] = S.charAt(indices.get((i + offset) % indices.size()));
    }

    return new String(result);
  }
}
