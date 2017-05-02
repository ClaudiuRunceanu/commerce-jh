package com.commerce.service.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Data class for price.
 */
public class PriceDto {
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Double value;

    private CurrencyDto currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }
}
