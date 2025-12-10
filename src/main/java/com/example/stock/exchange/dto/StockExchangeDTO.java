package com.example.stock.exchange.dto;


import lombok.Data;
import java.util.Set;

@Data
public class StockExchangeDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean liveInMarket;
    private Set<StockDTO> stocks;
}