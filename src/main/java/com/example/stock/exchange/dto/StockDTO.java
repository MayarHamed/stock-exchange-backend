package com.example.stock.exchange.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private LocalDateTime lastUpdate;
}