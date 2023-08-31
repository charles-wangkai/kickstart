import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] sources = new String[N];
      String[] destinations = new String[N];
      for (int i = 0; i < N; ++i) {
        sources[i] = sc.next();
        destinations[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(sources, destinations)));
    }

    sc.close();
  }

  static String solve(String[] sources, String[] destinations) {
    Map<String, String> sourceToDestination =
        IntStream.range(0, sources.length)
            .boxed()
            .collect(Collectors.toMap(i -> sources[i], i -> destinations[i]));
    Map<String, String> destinationToSource =
        IntStream.range(0, sources.length)
            .boxed()
            .collect(Collectors.toMap(i -> destinations[i], i -> sources[i]));

    String start =
        sourceToDestination.keySet().stream()
            .filter(s -> !destinationToSource.containsKey(s))
            .findAny()
            .get();

    List<String> result = new ArrayList<>();
    while (sourceToDestination.containsKey(start)) {
      result.add(String.format("%s-%s", start, sourceToDestination.get(start)));

      start = sourceToDestination.get(start);
    }

    return String.join(" ", result);
  }
}
