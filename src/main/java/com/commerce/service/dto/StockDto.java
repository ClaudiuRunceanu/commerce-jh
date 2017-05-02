package com.commerce.service.dto;

import com.commerce.domain.Product;
import com.commerce.domain.Warehouse;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Class for stock data.
 */
public class StockDto {
    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer available;

    @Min(value = 0)
    private Integer preOrder;

    @Min(value = 0)
    private Integer reserved;

    @NotNull
    private ZonedDateTime expireDate;

    @NotNull
    private ZonedDateTime creationDate;

    private Warehouse warehouse;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(Integer preOrder) {
        this.preOrder = preOrder;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public ZonedDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(ZonedDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
