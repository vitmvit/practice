package conf;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConfProvider {

    private static final String EXCHANGE_RATES_CONF = "conf/exchange_rates.conf";
    private static final String ROUNDING_CONF = "conf/rounding.conf";
    private final ConfReader confReader;

    public ConfProvider() {
        this.confReader = new ConfReader();
    }

    public Map<String, BigDecimal> exchangeConf() {
        Map<String, String> map = confReader.getConf(EXCHANGE_RATES_CONF);
        Map<String, BigDecimal> result = new HashMap<>();
        for (Map.Entry<String, String> item : map.entrySet()) {
            result.put(item.getKey(), BigDecimal.valueOf(Double.parseDouble(item.getValue())));
        }
        return result;
    }

    public int getRoundScale() {
        Map<String, String> map = confReader.getConf(ROUNDING_CONF);
        return Integer.parseInt(map.getOrDefault("rounding.scale", "2"));
    }
}
