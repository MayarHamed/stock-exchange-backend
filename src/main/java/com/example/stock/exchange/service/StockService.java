package com.example.stock.exchange.service;

import com.example.stock.exchange.entity.Stock;
import com.example.stock.exchange.helper.BusinessException;
import com.example.stock.exchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepo;

    public Stock updatePrice(Long stockId, BigDecimal newPrice) {
        Stock stock = stockRepo.findById(stockId)
                .orElseThrow(() -> new BusinessException("Stock not found", HttpStatus.NOT_FOUND));
        stock.setCurrentPrice(newPrice);
        stock.setLastUpdate(LocalDateTime.now());
        return stockRepo.save(stock);
    }

    public List<Stock> getAllStocks() {
        return stockRepo.findAll();
    }

    public Stock createStock(Stock stock) {
        try{
            return stockRepo.save(stock);
        }catch (DataIntegrityViolationException exception){
            throw new BusinessException("A stock with this name already exists", HttpStatus.CONFLICT);
        }
    }

    public void deleteStock(Long id) {
        try{
            stockRepo.deleteById(id);
        }catch (DataIntegrityViolationException exception){
            throw new BusinessException("Cannot delete stock included in exchanges", HttpStatus.BAD_REQUEST);
        }
    }
}
