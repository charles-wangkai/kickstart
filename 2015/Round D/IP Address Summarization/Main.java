import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
  static final int GROUP_BIT_NUM = 8;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] subnets = new String[N];
      for (int i = 0; i < subnets.length; ++i) {
        subnets[i] = sc.next();
      }

      System.out.println(String.format("Case #%d:\n%s", tc, solve(subnets)));
    }

    sc.close();
  }

  static String solve(String[] subnets) {
    Set<String> result = Arrays.stream(subnets).map(Main::normalize).collect(Collectors.toSet());

    for (String s : new ArrayList<>(result)) {
      if (buildAncestors(s).stream().anyMatch(result::contains)) {
        result.remove(s);
      }
    }

    while (true) {
      boolean changed = false;

      for (String s :
          result.stream()
              .sorted(Comparator.comparing((String s) -> buildParts(s)[4]).reversed())
              .collect(Collectors.toList())) {
        int[] parts = buildParts(s);
        if (parts[4] != 0) {
          String other = computeOther(s);
          if (result.contains(s) && result.contains(other)) {
            result.remove(s);
            result.remove(other);

            --parts[4];
            result.add(normalize(convertToSubnet(parts)));

            changed = true;
          }
        }
      }

      if (!changed) {
        break;
      }
    }

    return result.stream()
        .sorted(
            (s1, s2) -> {
              int[] parts1 = buildParts(s1);
              int[] parts2 = buildParts(s2);

              for (int i = 0; ; ++i) {
                if (parts1[i] != parts2[i]) {
                  return Integer.compare(parts1[i], parts2[i]);
                }
              }
            })
        .collect(Collectors.joining("\n"));
  }

  static String computeOther(String s) {
    int[] parts = buildParts(s);
    long value =
        (parts[0] * (1L << 24) + parts[1] * (1 << 16) + parts[2] * (1 << 8) + parts[3])
            ^ (1L << (32 - parts[4]));

    return convertToSubnet(
        new int[] {
          (int) (value / (1 << 24)),
          (int) (value / (1 << 16) % (1 << 8)),
          (int) (value / (1 << 8) % (1 << 8)),
          (int) (value % (1 << 8)),
          parts[4]
        });
  }

  static List<String> buildAncestors(String s) {
    int[] parts = buildParts(s);

    List<String> result = new ArrayList<>();
    while (parts[4] != 0) {
      --parts[4];
      result.add(normalize(convertToSubnet(parts)));
    }

    return result;
  }

  static String normalize(String s) {
    int[] parts = buildParts(s);

    int rest = parts[4];
    for (int i = 0; i < 4; ++i) {
      int prefix = Math.min(GROUP_BIT_NUM, rest);
      parts[i] = parts[i] / (1 << (GROUP_BIT_NUM - prefix)) * (1 << (GROUP_BIT_NUM - prefix));

      rest -= prefix;
    }

    return convertToSubnet(parts);
  }

  static int[] buildParts(String s) {
    return Arrays.stream(s.split("[./]")).mapToInt(Integer::parseInt).toArray();
  }

  static String convertToSubnet(int[] parts) {
    return String.format("%d.%d.%d.%d/%d", parts[0], parts[1], parts[2], parts[3], parts[4]);
  }
}
