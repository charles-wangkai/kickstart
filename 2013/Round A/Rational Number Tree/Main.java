import java.math.BigInteger;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    sc.nextLine();
    for (int tc = 1; tc <= T; ++tc) {
      String query = sc.nextLine();

      System.out.println(String.format("Case #%d: %s", tc, solve(query)));
    }

    sc.close();
  }

  static String solve(String query) {
    String[] parts = query.split(" ");
    if (parts[0].equals("1")) {
      Node node = searchNode(new BigInteger(parts[1]));

      return String.format("%d %d", node.p, node.q);
    } else {
      return searchIndex(new BigInteger(parts[1]), new BigInteger(parts[2])).toString();
    }
  }

  static Node searchNode(BigInteger index) {
    if (index.equals(BigInteger.ONE)) {
      return new Node(BigInteger.ONE, BigInteger.ONE);
    }

    Node parent = searchNode(index.divide(BigInteger.TWO));

    return index.mod(BigInteger.TWO).equals(BigInteger.ZERO)
        ? new Node(parent.p, parent.p.add(parent.q))
        : new Node(parent.p.add(parent.q), parent.q);
  }

  static BigInteger searchIndex(BigInteger p, BigInteger q) {
    if (p.compareTo(q) < 0) {
      return searchIndex(p, q.subtract(p)).multiply(BigInteger.TWO);
    } else if (p.compareTo(q) > 0) {
      return searchIndex(p.subtract(q), q).multiply(BigInteger.TWO).add(BigInteger.ONE);
    } else {
      return BigInteger.ONE;
    }
  }
}

class Node {
  BigInteger p;
  BigInteger q;

  Node(BigInteger p, BigInteger q) {
    this.p = p;
    this.q = q;
  }
}
