import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      int D = sc.nextInt();
      int C = sc.nextInt();
      int M = sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %s", tc, solve(S, D, C, M) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(String S, int D, int C, int M) {
    int restDog = (int) S.chars().filter(c -> c == 'D').count();
    int restDogFood = D;
    long restCatFood = C;
    for (char c : S.toCharArray()) {
      if (c == 'D') {
        if (restDogFood == 0) {
          break;
        }

        --restDog;
        --restDogFood;
        restCatFood += M;
      } else {
        if (restCatFood == 0) {
          break;
        }

        --restCatFood;
      }
    }

    return restDog == 0;
  }
}
