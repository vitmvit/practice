package exchange.impl;

import conf.ConfProvider;
import exchange.Exchange;
import exeption.ConfigurationExeption;

import java.math.BigDecimal;
import java.util.Map;

public class RublesToDollars implements Exchange {

    private static final String EXCHANGE_RUBLE = "exchange.rubles";
    private final ConfProvider confProvider;

    public RublesToDollars() {
        confProvider = new ConfProvider();
    }

    @Override
    public BigDecimal exchange(BigDecimal currency) {
        Map<String, BigDecimal> map = confProvider.exchangeConf();
        BigDecimal exchangeRate = map.get(EXCHANGE_RUBLE);
        if (exchangeRate == null) {
            throw new ConfigurationExeption();
        }
        return currency.multiply(exchangeRate);
    }
}
