package de.matoatoa.demo.codedays19.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    private final WebClient webClient;

    public Controller(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/marketData")
    public Mono<List<Mono<MarketData>>> getMarketData() {
        final Function<String, Mono<MarketData>> CALL_SERVER =
                id -> webClient.get().uri("/api/market/{id}", id).retrieve().bodyToMono(MarketData.class);
        return Flux.fromIterable(COMPANIES).map(CALL_SERVER).collectList();
    }
}
