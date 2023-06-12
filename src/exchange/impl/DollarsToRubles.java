package exchange.impl;

import conf.ConfProvider;
import exchange.Exchange;
import exeption.ConfigurationExeption;

import java.math.BigDecimal;
import java.util.Map;

public class DollarsToRubles implements Exchange {

    private static final String EXCHANGE_DOLLAR = "exchange.dollar";
    private final ConfProvider confProvider;

    public DollarsToRubles() {
        confProvider = new ConfProvider();
    }

    public BigDecimal exchange(BigDecimal currency) {
        Map<String, BigDecimal> map = confProvider.exchangeConf();
        BigDecimal exchangeRate = map.get(EXCHANGE_DOLLAR);
        if (exchangeRate == null) {
            throw new ConfigurationExeption();
        }
        return currency.multiply(exchangeRate);
    }
}
