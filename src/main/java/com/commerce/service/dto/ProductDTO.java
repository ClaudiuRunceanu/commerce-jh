package com.commerce.service.dto;

import com.commerce.domain.*;

import java.util.Set;

/**
 * Created by Claudiu on 4/15/2017.
 */
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Catalog catalog;
    private Price price;
    private Set<Stock> stocks;
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

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
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

    public Double getPriceValue() {
        return priceValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public Set<Category> getCategories() {
        return categories;
    }
}

