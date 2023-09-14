import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int T = Integer.parseInt(st.nextToken());
    for (int tc = 1; tc <= T; ++tc) {
      st = new StringTokenizer(br.readLine());
      int D = Integer.parseInt(st.nextToken());
      int N = Integer.parseInt(st.nextToken());
      int K = Integer.parseInt(st.nextToken());
      int[] h = new int[N];
      int[] s = new int[N];
      int[] e = new int[N];
      for (int i = 0; i < N; ++i) {
        st = new StringTokenizer(br.readLine());
        h[i] = Integer.parseInt(st.nextToken());
        s[i] = Integer.parseInt(st.nextToken());
        e[i] = Integer.parseInt(st.nextToken());
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(D, h, s, e, K)));
    }
  }

  static long solve(int D, int[] h, int[] s, int[] e, int K) {
    int[] sortedAttractionIndices =
        IntStream.range(0, h.length)
            .flatMap(i -> IntStream.of(i, -1 - i))
            .boxed()
            .sorted(Comparator.comparing(i -> getDay(s, e, i)))
            .mapToInt(Integer::intValue)
            .toArray();

    long result = -1;
    Comparator<Integer> attractionComparator =
        Comparator.comparing((Integer i) -> h[i]).thenComparing(Function.identity());
    SortedSet<Integer> chosen = new TreeSet<>(attractionComparator);
    long chosenSum = 0;
    SortedSet<Integer> nonChosen = new TreeSet<>(attractionComparator);
    int index = 0;
    for (int day = 1; day <= D; ++day) {
      while (index != sortedAttractionIndices.length
          && getDay(s, e, sortedAttractionIndices[index]) == day) {
        if (sortedAttractionIndices[index] >= 0) {
          chosen.add(sortedAttractionIndices[index]);
          chosenSum += h[sortedAttractionIndices[index]];

          if (chosen.size() == K + 1) {
            int first = chosen.first();
            chosen.remove(first);
            chosenSum -= h[first];

            nonChosen.add(first);
          }
        } else if (nonChosen.contains(-1 - sortedAttractionIndices[index])) {
          nonChosen.remove(-1 - sortedAttractionIndices[index]);
        } else {
          chosen.remove(-1 - sortedAttractionIndices[index]);
          chosenSum -= h[-1 - sortedAttractionIndices[index]];

          if (chosen.size() == K - 1 && !nonChosen.isEmpty()) {
            int last = nonChosen.last();
            nonChosen.remove(last);

            chosen.add(last);
            chosenSum += h[last];
          }
        }

        ++index;
      }

      result = Math.max(result, chosenSum);
    }

    return result;
  }

  static int getDay(int[] s, int[] e, int attractionIndex) {
    return (attractionIndex >= 0) ? s[attractionIndex] : (e[-1 - attractionIndex] + 1);
  }
}
