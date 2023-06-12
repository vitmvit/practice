package util;

import exeption.IncorrectStringFormatException;

import static constants.Constants.TO_DOLLARS;
import static constants.Constants.TO_RUBLES;

public class ExpressionUtils {

    /**
     * ищем последнее вхождение указателя на конвертацию (toDollars/toRubles)
     */
    public static String partExpression(String expression) {
        int coord1 = expression.lastIndexOf(TO_DOLLARS);
        int coord2 = expression.lastIndexOf(TO_RUBLES);
        return coord1 > coord2
                ? expression.substring(coord1, expression.indexOf(")", coord1) + 1)
                : expression.substring(coord2, expression.indexOf(")", coord2) + 1);
    }

    public static void correctCountBrackets(String expression) {
        if (expression.contains("(")) {
            if (!expression.contains(")")) {
                throw new IncorrectStringFormatException();
            }
            int open = 0;
            int close = 0;
            for (char c : expression.toCharArray()) {
                if (c == '(') {
                    open++;
                }
                if (c == ')') {
                    close++;
                }
            }
            if (open != close) {
                throw new IncorrectStringFormatException();
            }
        }
    }

    public static String getLeftNumber(String line, int coordinate) {
        StringBuilder result = new StringBuilder();
        for (int i = coordinate - 1; i >= 0; --i) {
            if (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.' || line.charAt(i) == '-') {
                result.append(line.charAt(i));
                if (i != 0 && line.charAt(i) == '-' && Character.isDigit(line.charAt(i - 1))) {
                    result.setLength(result.length() - 1);
                    break;
                }
            } else {
                break;
            }
        }
        return result.reverse().toString();
    }

    public static String getRightNumber(String line, int coordinate) {
        StringBuilder result = new StringBuilder();
        for (int i = coordinate + 1; i < line.length(); ++i) {
            if (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.' || line.charAt(i) == '-') {
                result.append(line.charAt(i));
                if (line.charAt(i) == '-') {
                    result.setLength(result.length() - 1);
                    break;
                }
            } else {
                break;
            }
        }
        return result.toString();
    }
}
