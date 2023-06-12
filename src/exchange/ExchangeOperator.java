package exchange;

import conf.ConfProvider;
import exchange.impl.DollarsToRubles;
import exchange.impl.RublesToDollars;
import exeption.IncompatibleCurrencyTypesException;
import exeption.IncorrectStringFormatException;
import exeption.UnknownCurrencyTypeException;
import util.ExpressionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static constants.Constants.*;

public class ExchangeOperator {

    private final Exchange dollarConverter;
    private final Exchange rubleConverter;
    private final ConfProvider confProvider;
    MathSolution mathSolution;

    public ExchangeOperator() {
        dollarConverter = new DollarsToRubles();
        rubleConverter = new RublesToDollars();
        confProvider = new ConfProvider();
        mathSolution = new MathSolution();
    }

    public String exchangeOperation(String exchangeRequest) {
        exchangeRequest = getPreparedLine(exchangeRequest.toLowerCase());
        checkRequest(exchangeRequest);
        return round(getResult(exchangeRequest));
    }

    private void checkRequest(String exchangeRequest) {
        // если указан тип конвертации, но при этом валюты в операции участвуют разные - ошибка
        if (!exchangeRequest.contains(TO_DOLLARS) || !exchangeRequest.contains(TO_RUBLES)) {
            if (exchangeRequest.contains(DOLLAR) && exchangeRequest.contains(RUBLE)) {
                throw new IncompatibleCurrencyTypesException();
            }
        }

        // если операция сложная, считаем количество скобок, при несоотвествии - ошибка
        ExpressionUtils.correctCountBrackets(exchangeRequest);
    }

    private String getPreparedLine(String exchangeRequest) {
        // уберём все пробелы и заменим все английские символы "р" на русские
        return exchangeRequest.trim().replace(" ", "").replace("p", RUBLE);
    }

    private String getResult(String exchangeRequest) {
        // если в строке нет toDollars или toRubles - Solution
        if (!exchangeRequest.contains(TO_DOLLARS) & !exchangeRequest.contains(TO_RUBLES)) {
            return mathSolution.calculate(exchangeRequest);
        }
        // если в строке есть toDollars/toRubles
        if (exchangeRequest.contains(TO_DOLLARS) || exchangeRequest.contains(TO_RUBLES)) {
            return mathProcessing(exchangeRequest);
        }
        throw new IncorrectStringFormatException();
    }

    private String mathProcessing(String exchangeRequest) {
        while (exchangeRequest.contains(TO_DOLLARS) || exchangeRequest.contains(TO_RUBLES)) {
            String part = ExpressionUtils.partExpression(exchangeRequest);
            String leftPart = exchangeRequest.substring(0, exchangeRequest.lastIndexOf(part));
            String rightPart = exchangeRequest.substring(exchangeRequest.lastIndexOf(part) + part.length());
            part = convertProcessing(part);
            exchangeRequest = leftPart + part + rightPart;
        }
        return exchangeRequest;
    }

    // toDollars($7) => $7
    // toDollars(7p) => надо посчитать
    // toDollars($1+$2) => $3
    // toDollars(1p+9p) => надо посчитать
    private String convertProcessing(String request) {
        if (request.contains(TO_DOLLARS)) {
            String result = mathSolution.calculate(request.substring(TO_DOLLARS.length() + 1, request.length() - 1));
            if (result.contains(DOLLAR)) {
                return result;
            }
            return DOLLAR + dollarConverter.exchange(new BigDecimal(result.replace(RUBLE, "")));
        } else if (request.contains(TO_RUBLES)) {
            String result = mathSolution.calculate(request.substring(TO_RUBLES.length() + 1, request.length() - 1));
            if (result.contains(RUBLE)) {
                return result;
            }
            return rubleConverter.exchange(new BigDecimal(result.replace(DOLLAR, ""))) + RUBLE;
        }
        throw new IncorrectStringFormatException();
    }

    private String round(String expression) {
        confProvider.getRoundScale();
        if (expression.startsWith(DOLLAR)) {
            expression = expression.replace(DOLLAR, "");
            return DOLLAR + new BigDecimal(expression).setScale(confProvider.getRoundScale(), RoundingMode.HALF_UP);
        } else if (expression.endsWith(RUBLE)) {
            expression = expression.replace(RUBLE, "");
            return new BigDecimal(expression).setScale(confProvider.getRoundScale(), RoundingMode.HALF_UP) + RUBLE;
        }
        throw new UnknownCurrencyTypeException();
    }
}
