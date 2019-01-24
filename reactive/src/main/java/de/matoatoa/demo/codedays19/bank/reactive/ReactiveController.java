package de.matoatoa.demo.codedays19.bank.reactive;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
@RestController
public class ReactiveController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestController.class);
    private final static Consumer<MarketData> LOG_RESULT = datum -> LOGGER.info("Got result:{}", datum);
    private final List<String> COMPANIES = List.of("Google", "Amazon", "BMV", "Microsoft", "Apple", "SAP", "Audi", "Daimler", "Telekom", "Bosch", "Siemens", "IBM", "Porsche", "VW", "DB");
    private final WebClient webClient;

    @GetMapping("/marketData")
    public Mono<List<MarketData>> getMarketData() {
        final Function<String, Mono<MarketData>> CALL_SERVER =
                id -> webClient.get()
                        .uri("/api/market/{name}", id)
                        .retrieve()
                        .bodyToMono(MarketData.class)
                        .doOnNext(LOG_RESULT);
        return Flux.fromIterable(COMPANIES).flatMap(CALL_SERVER).collectList();
    }


}
