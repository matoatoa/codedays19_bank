package de.matoatoa.demo.codedays19.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private final static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final static Consumer<MarketData> LOG_RESULT = datum -> LOGGER.info("Got result:{}", datum);

    private final static List<String> COMPANIES = List.of("Google", "Amazon", "BMV", "Microsoft", "Apple", "SAP", "Audi", "Daimler", "Telekom", "Bosch", "Siemens", "IBM", "Porsche", "VW", "DB");
    private final RestTemplate restTemplate;

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/marketData")
    public List<MarketData> getMarketData() {
        final Function<String, MarketData> CALL_SERVER =
                id -> restTemplate.getForObject("/api/market/" + id, MarketData.class, Collections.emptyMap());
        return COMPANIES.stream().parallel().map(CALL_SERVER).peek(LOG_RESULT).collect(Collectors.toList());
    }
}
