package com.example.stock.exchange.service;

import com.example.stock.exchange.entity.Stock;
import com.example.stock.exchange.entity.StockExchange;
import com.example.stock.exchange.helper.BusinessException;
import com.example.stock.exchange.repository.StockExchangeRepository;
import com.example.stock.exchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockExchangeService {
    @Autowired
    private StockExchangeRepository exchangeRepo;
    @Autowired
    private StockRepository stockRepo;

    public StockExchange addStockToExchange(Long exchangeId, Long stockId) {
        StockExchange exchange = exchangeRepo.findById(exchangeId)
                .orElseThrow(() -> new BusinessException("Exchange not found", HttpStatus.NOT_FOUND));
        Stock stock = stockRepo.findById(stockId)
                .orElseThrow(() -> new BusinessException("Stock not found", HttpStatus.NOT_FOUND));

        if (exchange.getStocks().contains(stock)) {
            throw new BusinessException("Stock already exists in this exchange", HttpStatus.CONFLICT);
        }
        exchange.getStocks().add(stock);
        if(exchange.getStocks() != null && exchange.getStocks().size() >= 10) {
            exchange.setLiveInMarket(true);
        }else
            exchange.setLiveInMarket(false);

        return exchangeRepo.save(exchange);
    }

    public StockExchange removeStockFromExchange(Long exchangeId, Long stockId) {
        StockExchange exchange = exchangeRepo.findById(exchangeId)
                .orElseThrow(() -> new BusinessException("Exchange not found", HttpStatus.NOT_FOUND));
        Stock stock = stockRepo.findById(stockId)
                .orElseThrow(() -> new BusinessException("Stock not found", HttpStatus.NOT_FOUND));

        exchange.getStocks().remove(stock);
        if(exchange.getStocks() != null && exchange.getStocks().size() >= 10) {
            exchange.setLiveInMarket(true);
        }else
            exchange.setLiveInMarket(false);
        return exchangeRepo.save(exchange);
    }

    public List<StockExchange> getAllExchanges() {
        return exchangeRepo.findAll();
    }

    public StockExchange createExchange(StockExchange exchange) {
        if(exchange.getStocks() != null && exchange.getStocks().size() >= 10) {
            exchange.setLiveInMarket(true);
        }else
            exchange.setLiveInMarket(false);
        if(exchange.getStocks() != null) {
            throw new BusinessException("stock exchange creation failed. kindly create stocks separately then add them to the exchange"
                    , HttpStatus.CONFLICT);
        }
        try{
            return exchangeRepo.save(exchange);
        }catch (DataIntegrityViolationException exception){
            throw new BusinessException("A stock exchange with this name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public StockExchange updateExchange(Long exchangeId, StockExchange updatedExchange) {
        StockExchange existingExchange = exchangeRepo.findById(exchangeId)
                .orElseThrow(() -> new BusinessException("StockExchange not found", HttpStatus.NOT_FOUND));

        existingExchange.setName(updatedExchange.getName());
        existingExchange.setDescription(updatedExchange.getDescription());

        if (updatedExchange.getLiveInMarket() && updatedExchange.getStocks().size() < 10) {
            throw new BusinessException("Stock exchange having less than 10 stocks cannot be live in the market"
                    , HttpStatus.BAD_REQUEST);
        }

        existingExchange.setLiveInMarket(updatedExchange.getLiveInMarket());

        return exchangeRepo.save(existingExchange);
    }

    public void deleteExchange(Long id) {
        StockExchange stockExchange = exchangeRepo.findById(id)
                .orElseThrow(() -> new BusinessException("Stock Exchange not found", HttpStatus.NOT_FOUND));
        stockExchange.getStocks().clear();
        exchangeRepo.deleteById(id);
    }
}
