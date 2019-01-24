package de.matoatoa.demo.codedays19.bank.client;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class MarketData {
    @Id
    private String name;
    private List<Integer> values;
}
