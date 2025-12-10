package com.example.stock.exchange.controller;

import com.example.stock.exchange.dto.StockExchangeDTO;
import com.example.stock.exchange.entity.StockExchange;
import com.example.stock.exchange.mapper.StockExchangeMapper;
import com.example.stock.exchange.service.StockExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exchanges")
public class StockExchangeController {

    @Autowired
    private StockExchangeService service;

    @Autowired
    private StockExchangeMapper stockExchangeMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<StockExchangeDTO> getAll() {
        return service.getAllExchanges()
                .stream()
                .map(stockExchangeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StockExchangeDTO create(@RequestBody StockExchangeDTO exchangeDTO) {
        StockExchange exchange = stockExchangeMapper.exchangeToDTO(exchangeDTO);
        return stockExchangeMapper.toDTO(service.createExchange(exchange));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StockExchangeDTO update(@PathVariable Long id, @RequestBody StockExchangeDTO exchangeDTO) {
        StockExchange exchange = stockExchangeMapper.exchangeToDTO(exchangeDTO);
        return stockExchangeMapper.toDTO(service.updateExchange(id, exchange));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.deleteExchange(id);
    }

    @PostMapping("/{exchangeId}/stocks/{stockId}")
    @PreAuthorize("hasRole('ADMIN')")
    public StockExchangeDTO addStock(@PathVariable Long exchangeId, @PathVariable Long stockId) {
        StockExchange updated = service.addStockToExchange(exchangeId, stockId);
        return stockExchangeMapper.toDTO(updated);
    }

    @DeleteMapping("/{exchangeId}/stocks/{stockId}")
    @PreAuthorize("hasRole('ADMIN')")
    public StockExchangeDTO removeStock(@PathVariable Long exchangeId, @PathVariable Long stockId) {
        StockExchange updated = service.removeStockFromExchange(exchangeId, stockId);
        return stockExchangeMapper.toDTO(updated);
    }
}
