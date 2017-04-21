package com.commerce.service;

import com.commerce.domain.Stock;
import com.commerce.repository.StockRepository;
import com.commerce.service.dto.StockDto;
import com.commerce.service.mapper.StockConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for stock.
 */
@Service
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final StockConverter stockConverter;

    public StockService(StockRepository stockRepository, StockConverter stockConverter) {
        this.stockRepository = stockRepository;
        this.stockConverter = stockConverter;
    }


    public StockDto createStockForProduct(StockDto stockDto){
        Stock result=this.stockRepository.save(this.stockConverter.getStockModel(stockDto));
        stockDto.setId(result.getId());
        return stockDto;
    }
}
