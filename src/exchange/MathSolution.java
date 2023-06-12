package exchange;

import calculator.Calculator;
import calculator.impl.Addition;
import calculator.impl.Subtraction;
import exeption.IncompatibleCurrencyTypesException;
import exeption.IncorrectStringFormatException;
import exeption.NegativeResultException;
import exeption.UnknownCurrencyTypeException;
import util.ExpressionUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.Constants.DOLLAR;
import static constants.Constants.RUBLE;

public class MathSolution {
    /*
     * Данное регулярное выражение соответствует строкам, которые начинаются с произвольного количества повторений
     * числовых значений, начинающихся с символа "$", разделенных знаками минус или плюс и точками (если есть десятичная
     * часть), и заканчиваются одним числом, начинающимся с символа "$" и имеющим опциональную десятичную часть
     * (с точкой). Например, строка "$12.34+56.78-$90" соответствует данному регулярному выражению, а строка "$ab+cd-ef"
     * не соответствует.
     */
    private static final String DOLLAR_PATTERN = "^(\\$[0-9]+[.]{0,1}[0-9]*[-+])*\\$[0-9]+[.]{0,1}[0-9]*$";
    /*
     * Это регулярное выражение ищет числа с точкой, а также знак плюс или минус перед числом, заканчивающимся на символ
     * "р" и возможно, имеющим знак "+-" в конце строки. Например, строка "12.345р-67.89р+10р" будет соответствовать
     * данному регулярному выражению.
     */
    private static final String RUBLE_PATTERN = "^(-?\\d+(\\.\\d+)?р[+-])*(-?\\d+(\\.\\d+)?р)$";
    private static final String MATH_OPERATOR_PATTERN = "[\\+\\-]";

    public String calculate(String expression) {
        if (expression.contains(DOLLAR) && expression.contains(RUBLE)) {
            throw new IncompatibleCurrencyTypesException();
        }
        if (expression.contains(DOLLAR)) {
            boolean isValid = expression.matches(DOLLAR_PATTERN);
            if (isValid) {
                expression = expression.replace(DOLLAR, "");
                if (!expression.contains("+") && !expression.contains("-")) {
                    return DOLLAR + expression;
                }
                return DOLLAR + calculateFullExpression(expression);
            } else {
                throw new IncorrectStringFormatException();
            }
        } else if (expression.contains(RUBLE)) {
            if (expression.matches(RUBLE_PATTERN)) {
                expression = expression.replace(RUBLE, "");
                if (!expression.contains("+") && !expression.contains("-")) {
                    return expression + RUBLE;
                }
                return calculateFullExpression(expression) + RUBLE;
            } else {
                throw new IncorrectStringFormatException();
            }
        } else {
            throw new UnknownCurrencyTypeException();
        }
    }

    /**
     * решает сложные, составные выражения
     * пример: 1+2-3+4-5
     */
    private String calculateFullExpression(String expression) {
        if (!expression.contains("+") && !expression.contains("-")) {
            return expression;
        }
        while (expression.contains("+") || expression.contains("-")) {
            Matcher matcher = Pattern.compile(MATH_OPERATOR_PATTERN).matcher(expression);
            int coord = -1;
            if (matcher.find()) {
                coord = matcher.start();
            }
            if (coord <= 0) {
                // todo
                throw new NegativeResultException();
            }
            String rightNumber = ExpressionUtils.getRightNumber(expression, coord);
            String tail = expression.substring(coord + rightNumber.length() + 1);
            BigDecimal resultPair = calculatePairExpression(expression, coord);
            expression = resultPair.toString() + tail;
        }
        return expression;
    }

    /**
     * решает парные (простые) выражения сложения/вычитания
     * пример: 1+2
     */
    private BigDecimal calculatePairExpression(String expression, int coord) {
        Calculator calculator;
        if (expression.charAt(coord) == '+') {
            calculator = new Addition();
            return calculator.calculate(
                    new BigDecimal(ExpressionUtils.getLeftNumber(expression, coord)),
                    new BigDecimal(ExpressionUtils.getRightNumber(expression, coord))
            );
        } else if (expression.charAt(coord) == '-') {
            calculator = new Subtraction();
            return calculator.calculate(
                    new BigDecimal(ExpressionUtils.getLeftNumber(expression, coord)),
                    new BigDecimal(ExpressionUtils.getRightNumber(expression, coord))
            );
        } else {
            throw new NegativeResultException();
        }
    }
}
