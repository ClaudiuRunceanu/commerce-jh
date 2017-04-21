package com.commerce.service.mapper;

import com.commerce.domain.Stock;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.StockDto;
import org.springframework.stereotype.Component;

/**
 * Converter class for stock.
 */
@Component
public class StockConverter {

    private final ProductRepository productRepository;

    public StockConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Stock getStockModel(StockDto stockDto){
        Stock stock= new Stock();
        stock.setAvailable(stockDto.getAvailable());
        stock.setPreOrder(stockDto.getPreOrder());
        stock.setReserved(stockDto.getReserved());
        stock.setCreationDate(stockDto.getCreationDate());
        stock.setExpireDate(stockDto.getExpireDate());
        stock.setWarehouse(stockDto.getWarehouse());
        stock.setProduct(this.productRepository.findOne(stockDto.getProductId()));
        return stock;
    }
}
