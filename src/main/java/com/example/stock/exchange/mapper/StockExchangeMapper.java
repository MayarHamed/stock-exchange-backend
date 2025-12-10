package com.example.stock.exchange.mapper;

import com.example.stock.exchange.dto.StockExchangeDTO;
import com.example.stock.exchange.entity.StockExchange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockExchangeMapper
{
    StockExchangeDTO toDTO(StockExchange exchange);
    StockExchange exchangeToDTO(StockExchangeDTO exchangeDTO);

}
