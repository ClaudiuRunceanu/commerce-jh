package com.commerce.service;

import com.commerce.domain.Currency;
import com.commerce.repository.CurrencyRepository;
import com.commerce.service.dto.CurrencyDto;
import com.commerce.service.mapper.CurrencyConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for currency.
 */
@Service
@Transactional
public class CurrencyService {
    private CurrencyRepository currencyRepository;
    private CurrencyConverter currencyConverter;

    public CurrencyService(CurrencyRepository currencyRepository, CurrencyConverter currencyConverter) {
        this.currencyRepository = currencyRepository;
        this.currencyConverter = currencyConverter;
    }

    public CurrencyDto createNewCurrency(CurrencyDto currencyDto) {
        Currency currency = this.currencyRepository.save(currencyConverter.getCurrencyModel(currencyDto));
        currencyDto.setId(currency.getId());
        return currencyDto;
    }

    public CurrencyDto updateCurrency(CurrencyDto currencyDto) {
        this.currencyRepository.save(currencyConverter.getCurrencyModel(currencyDto));
        return currencyDto;
    }

    public CurrencyDto findCurrencyById(Long id) {
        return currencyConverter.getCurrencyData(currencyRepository.findOne(id));
    }

    public void deleteCurrency(Long id) {
        this.currencyRepository.delete(id);
    }

    public List<CurrencyDto> getAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        return currencyConverter.getCurrencyDataList(currencies);
    }
}
