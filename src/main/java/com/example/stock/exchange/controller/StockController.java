package com.example.stock.exchange.controller;

import com.example.stock.exchange.dto.StockDTO;
import com.example.stock.exchange.entity.Stock;
import com.example.stock.exchange.mapper.StockMapper;
import com.example.stock.exchange.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService service;

    @Autowired
    private StockMapper stockMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StockDTO> getAll() {
        return service.getAllStocks()
                .stream()
                .map(stockMapper::ToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StockDTO create(@RequestBody StockDTO stockDTO) {
        Stock stock = stockMapper.dtoToStock(stockDTO);
        return stockMapper.ToDTO(service.createStock(stock));
    }

    @PutMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public StockDTO updatePrice(@PathVariable Long id, @RequestBody BigDecimal price) {
        Stock updated = service.updatePrice(id, price);
        return stockMapper.ToDTO(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.deleteStock(id);
    }
}
