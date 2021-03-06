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
public class ProductDto {
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
    private List<StockDto> stocks;
    private List<MediaDto> media;
    private List<CategoryDto> categories;
    private PriceDto price;

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(PriceDto price) {
        this.price = price;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<StockDto> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDto> stocks) {
        this.stocks = stocks;
    }

    public List<MediaDto> getMedia() {
        return media;
    }

    public void setMedia(List<MediaDto> media) {
        this.media = media;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}

