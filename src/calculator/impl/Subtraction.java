package calculator.impl;

import calculator.Calculator;

import java.math.BigDecimal;

public class Subtraction implements Calculator {

    @Override
    public BigDecimal calculate(BigDecimal var1, BigDecimal var2) {
        return var1.subtract(var2);
    }
}
