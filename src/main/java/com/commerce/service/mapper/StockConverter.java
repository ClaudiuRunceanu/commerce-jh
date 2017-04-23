package com.commerce.service.mapper;

import com.commerce.domain.Stock;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.StockDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for stock.
 */
@Component
public class StockConverter {

    private final ProductRepository productRepository;

    public StockConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Stock getStockModel(StockDto stockDto) {
        Stock stock = new Stock();
        stock.setAvailable(stockDto.getAvailable());
        stock.setPreOrder(stockDto.getPreOrder());
        stock.setReserved(stockDto.getReserved());
        stock.setCreationDate(stockDto.getCreationDate());
        stock.setExpireDate(stockDto.getExpireDate());
        stock.setWarehouse(stockDto.getWarehouse());
        if (stockDto.getProductId() != null) {
            stock.setProduct(this.productRepository.findOne(stockDto.getProductId()));
        }
        return stock;
    }

    public StockDto getStockData(Stock stock) {
        StockDto stockDto = new StockDto();
        stockDto.setAvailable(stock.getAvailable());
        stockDto.setPreOrder(stock.getPreOrder());
        stockDto.setReserved(stock.getReserved());
        stockDto.setWarehouse(stock.getWarehouse());
        stockDto.setId(stock.getId());
        stockDto.setExpireDate(stock.getExpireDate());
        stockDto.setCreationDate(stock.getCreationDate());

        return stockDto;
    }

    public List<StockDto> getStockDataList(List<Stock> stocks) {
        List<StockDto> stockDtos = new ArrayList<>();
        for (Stock stock : stocks) {
            StockDto data = getStockData(stock);
            stockDtos.add(data);
        }

        return stockDtos;
    }
}
