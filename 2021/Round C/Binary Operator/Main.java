import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  static final String SEPARATOR = "-";

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String[] E = new String[N];
      for (int i = 0; i < E.length; ++i) {
        E[i] = sc.next();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(E)));
    }

    sc.close();
  }

  static String solve(String[] E) {
    List<Map<String, BigInteger>> equivalences = new ArrayList<>();
    Map<String, Character> symbolToVariable = new HashMap<>();

    int[] result = new int[E.length];
    for (int i = 0; i < result.length; ++i) {
      Map<String, BigInteger> symbolToCoeff = evaluate(symbolToVariable, E[i]);

      int index = equivalences.indexOf(symbolToCoeff);
      if (index == -1) {
        equivalences.add(symbolToCoeff);
        index = equivalences.size() - 1;
      }

      result[i] = index + 1;
    }

    return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
  }

  static Map<String, BigInteger> simplify(Map<String, BigInteger> symbolToCoeff) {
    return symbolToCoeff.keySet().stream()
        .filter(symbol -> !symbolToCoeff.get(symbol).equals(BigInteger.ZERO))
        .collect(Collectors.toMap(Function.identity(), symbolToCoeff::get));
  }

  static Map<String, BigInteger> evaluate(
      Map<String, Character> symbolToVariable, String expression) {
    Map<String, BigInteger> result;
    if (expression.charAt(0) != '(') {
      result = Map.of("", new BigInteger(expression));
    } else {
      int depth = 0;
      int operatorIndex = 1;
      while (true) {
        char c = expression.charAt(operatorIndex);
        if (c == '(') {
          ++depth;
        } else if (c == ')') {
          --depth;
        } else if (!Character.isDigit(c) && depth == 0) {
          break;
        }

        ++operatorIndex;
      }

      Map<String, BigInteger> operand1 =
          evaluate(symbolToVariable, expression.substring(1, operatorIndex));
      Map<String, BigInteger> operand2 =
          evaluate(
              symbolToVariable, expression.substring(operatorIndex + 1, expression.length() - 1));

      result = merge(symbolToVariable, expression.charAt(operatorIndex), operand1, operand2);
    }
    result = simplify(result);

    return result;
  }

  static Map<String, BigInteger> merge(
      Map<String, Character> symbolToVariable,
      char operator,
      Map<String, BigInteger> operand1,
      Map<String, BigInteger> operand2) {
    if (operator == '+') {
      return add(operand1, operand2);
    } else if (operator == '*') {
      Map<String, BigInteger> result = Map.of();
      for (String symbol1 : operand1.keySet()) {
        BigInteger coeff1 = operand1.get(symbol1);
        result = add(result, multiply(operand2, symbol1, coeff1));
      }

      return result;
    } else {
      String symbol = String.format("((%s)#(%s))", toString(operand1), toString(operand2));
      symbolToVariable.putIfAbsent(symbol, (char) ('A' + symbolToVariable.size()));

      return Map.of(String.valueOf(symbolToVariable.get(symbol)), BigInteger.ONE);
    }
  }

  static Map<String, BigInteger> multiply(
      Map<String, BigInteger> operand, String symbol, BigInteger coeff) {
    return operand.keySet().stream()
        .collect(Collectors.toMap(s -> combine(s, symbol), s -> operand.get(s).multiply(coeff)));
  }

  static String combine(String symbol1, String symbol2) {
    if (symbol1.isEmpty()) {
      return symbol2;
    }
    if (symbol2.isEmpty()) {
      return symbol1;
    }

    return Stream.concat(
            Arrays.stream(symbol1.split(SEPARATOR)), Arrays.stream(symbol2.split(SEPARATOR)))
        .sorted()
        .collect(Collectors.joining(SEPARATOR));
  }

  static String toString(Map<String, BigInteger> symbolToCoeff) {
    return symbolToCoeff.keySet().stream()
        .sorted()
        .map(symbol -> String.format("%s=%d", symbol, symbolToCoeff.get(symbol)))
        .collect(Collectors.joining(",", "{", "}"));
  }

  static Map<String, BigInteger> add(
      Map<String, BigInteger> operand1, Map<String, BigInteger> operand2) {
    return Stream.concat(operand1.keySet().stream(), operand2.keySet().stream())
        .distinct()
        .collect(
            Collectors.toMap(
                Function.identity(),
                symbol ->
                    operand1
                        .getOrDefault(symbol, BigInteger.ZERO)
                        .add(operand2.getOrDefault(symbol, BigInteger.ZERO))));
  }
}
