package de.matoatoa.demo.codedays19.bank.reactive;

import lombok.Data;

import java.util.List;

@Data
public class MarketData {
    private String name;
    private List<Integer> values;
}
