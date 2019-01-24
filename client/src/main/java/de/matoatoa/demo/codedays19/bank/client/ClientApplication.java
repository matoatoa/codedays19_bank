package de.matoatoa.demo.codedays19.bank.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ClientApplication {


    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:9500");
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}

