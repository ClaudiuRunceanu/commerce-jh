package com.commerce.service.mapper;

import com.commerce.domain.Price;
import com.commerce.service.dto.PriceDto;
import org.springframework.stereotype.Component;

/**
 * Converter class for price.
 */
@Component
public class PriceConverter {
    private CurrencyConverter currencyConverter;

    public PriceConverter(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public PriceDto getPriceData(Price price) {
        PriceDto data = new PriceDto();
        data.setId(price.getId());
        data.setValue(price.getValue());
        data.setCurrency(currencyConverter.getCurrencyData(price.getCurrency()));
        return data;
    }

    public Price getPriceModel(PriceDto priceDto) {
        Price model = new Price();
        if (priceDto.getId() != null) {
            model.setId(priceDto.getId());
        }
        model.setValue(priceDto.getValue());
        model.setCurrency(currencyConverter.getCurrencyModel(priceDto.getCurrency()));
        return model;
    }
}
