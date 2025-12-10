package com.example.stock.exchange.mapper;

import com.example.stock.exchange.dto.StockDTO;
import com.example.stock.exchange.entity.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDTO ToDTO(Stock stock);
    Stock dtoToStock(StockDTO stockDTO);
}
