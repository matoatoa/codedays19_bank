package de.matoatoa.demo.codedays19.bank.client;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MarketDataRepository extends ReactiveMongoRepository<MarketData, String> {
}
