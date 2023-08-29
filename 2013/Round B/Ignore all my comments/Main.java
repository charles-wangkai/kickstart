import java.util.Scanner;

public class Main {
  static final String LEFT_COMMENT = "/*";
  static final String RIGHT_COMMENT = "*/";

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    StringBuilder text = new StringBuilder();
    while (sc.hasNextLine()) {
      text.append(sc.nextLine()).append("\n");
    }
    System.out.println(String.format("Case #1:\n%s", solve(text.toString())));

    sc.close();
  }

  static String solve(String text) {
    StringBuilder result = new StringBuilder();
    int fromIndex = 0;
    while (true) {
      int leftIndex = text.indexOf(LEFT_COMMENT, fromIndex);
      if (leftIndex == -1) {
        result.append(text.substring(fromIndex));

        break;
      }

      result.append(text.substring(fromIndex, leftIndex));
      fromIndex = leftIndex + LEFT_COMMENT.length();

      int depth = 1;
      int rightIndex;
      while (true) {
        int nextLeftIndex = text.indexOf(LEFT_COMMENT, fromIndex);
        int nextRightIndex = text.indexOf(RIGHT_COMMENT, fromIndex);

        if (nextLeftIndex == -1 && nextRightIndex == -1) {
          rightIndex = -1;

          break;
        } else if (nextRightIndex == -1
            || (nextLeftIndex != -1 && nextLeftIndex < nextRightIndex)) {
          ++depth;
          fromIndex = nextLeftIndex + LEFT_COMMENT.length();
        } else {
          --depth;
          fromIndex = nextRightIndex + RIGHT_COMMENT.length();

          if (depth == 0) {
            rightIndex = nextRightIndex;

            break;
          }
        }
      }

      if (rightIndex == -1) {
        result.append(text.substring(leftIndex));
      }

      fromIndex = rightIndex + RIGHT_COMMENT.length();
    }

    return result.toString();
  }
}
