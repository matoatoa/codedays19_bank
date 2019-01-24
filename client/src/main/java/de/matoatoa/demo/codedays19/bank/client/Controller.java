package de.matoatoa.demo.codedays19.bank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@RestController
public class Controller {
    private final static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final static Consumer<MarketData> LOG_RESULT = datum -> LOGGER.info("Got result:{}", datum);

    private final static List<String> COMPANIES = List.of("Google", "Amazon", "BMV", "Microsoft", "Apple", "SAP", "Audi", "Daimler", "Telekom", "Bosch", "Siemens", "IBM", "Porsche", "VW", "DB");
    private final WebClient webClient;
    private final MarketDataRepository repository;

    public Controller(WebClient webClient, MarketDataRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    @GetMapping("/marketData")
    public Mono<List<MarketData>> getMarketData() {
        final Function<String, Mono<MarketData>> CALL_SERVER =
                id -> webClient.get().uri("/api/market/{id}", id).retrieve().bodyToMono(MarketData.class);
        return Flux.fromIterable(COMPANIES).flatMap(CALL_SERVER).collectList();
    }

    @GetMapping("/db")
    public Flux<MarketData> getDbData() {
        return repository.findAll();
    }

    @GetMapping("/db/{id}")
    public Mono<ResponseEntity<MarketData>> getById(@PathVariable String id) {
        return repository.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/db")
    public Mono<ResponseEntity<MarketData>> createOrUpdate(@RequestBody MarketData marketData) {
        return repository.save(marketData).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.unprocessableEntity().build());
    }
}
