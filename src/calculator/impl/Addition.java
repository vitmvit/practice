package calculator.impl;

import calculator.Calculator;

import java.math.BigDecimal;

public class Addition implements Calculator {

    @Override
    public BigDecimal calculate(BigDecimal var1, BigDecimal var2) {
        return var1.add(var2);
    }
}
