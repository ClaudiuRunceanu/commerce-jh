package com.commerce.service.dto;

import com.commerce.domain.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Data class for product.
 */
public class ProductDTO {
    private Long id;
    @NotNull
    @Size(max = 100)
    private String code;
    @NotNull
    @Size(max = 200)
    private String name;
    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;
    private Catalog catalog;
    private Price price;
    private List<StockDto> stocks;
    private Set<Media> media;
    private Set<Category> categories;

    private Double priceValue;
    private Currency currency;
    private String currencySymbol;

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public void setPriceValue(Double priceValue) {
        this.priceValue = priceValue;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public List<StockDto> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDto> stocks) {
        this.stocks = stocks;
    }

    public Double getPriceValue() {
        return priceValue;
    }

    public Currency getCurrency() {
        return currency;
    }


    public Set<Media> getMedia() {
        return media;
    }

    public Set<Category> getCategories() {
        return categories;
    }
}

