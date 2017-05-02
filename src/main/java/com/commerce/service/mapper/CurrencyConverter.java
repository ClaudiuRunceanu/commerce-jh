package com.commerce.service.mapper;

import com.commerce.domain.Currency;
import com.commerce.service.dto.CurrencyDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for currency.
 */
@Component
public class CurrencyConverter {
    public CurrencyDto getCurrencyData(Currency currency) {
        CurrencyDto dto = new CurrencyDto();
        dto.setId(currency.getId());
        dto.setCode(currency.getCode());
        dto.setName(currency.getName());
        dto.setSymbol(currency.getSymbol());
        return dto;
    }

    public Currency getCurrencyModel(CurrencyDto currencyDto) {
        Currency model = new Currency();
        if (currencyDto.getId() != null) {
            model.setId(currencyDto.getId());
        }
        model.setCode(currencyDto.getCode());
        model.setName(currencyDto.getName());
        model.setSymbol(currencyDto.getSymbol());
        return model;
    }

    public List<CurrencyDto> getCurrencyDataList(List<Currency> currencyList) {
        List<CurrencyDto> dataList = new ArrayList<>();
        for (Currency currency : currencyList) {
            CurrencyDto dto = getCurrencyData(currency);
            dataList.add(dto);
        }
        return dataList;
    }

}
