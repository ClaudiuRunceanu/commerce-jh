package com.commerce.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data class for currency.
 */
public class CurrencyDto {

    private Long id;

    @NotNull
    @Size(max = 70)
    private String code;

    @NotNull
    @Size(max = 70)
    private String name;

    @NotNull
    @Size(max = 10)
    private String symbol;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
