package de.matoatoa.demo.codedays19.bank.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

@SpringBootApplication
@RestController
public class ServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);
    private static final Random RANDOM = new Random();
    private static final String SIZE = "10";

    @GetMapping("/api/market/{name}")
    public MarketData getMarketData(@PathVariable final String name, final @RequestParam(defaultValue = SIZE) int size) {
        LOGGER.info("Incoming requets for {}",name);
        return new MarketData(name, generate(() -> RANDOM.nextInt(5000)).limit(size).map(WAIT).collect(toList()));
    }

    private Function<Integer, Integer> WAIT = number -> {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            LOGGER.warn("Was interrupted: {}", e);
        }
        return number;
    };

    @Getter
    @AllArgsConstructor
    public class MarketData {
        private final String name;
        private final List<Integer> values;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

