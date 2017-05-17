package com.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "available", nullable = false)
    private Integer available;

    @Min(value = 0)
    @Column(name = "pre_order")
    private Integer preOrder;

    @Min(value = 0)
    @Column(name = "reserved")
    private Integer reserved;

    @NotNull
    @Column(name = "expire_date", nullable = false)
    private ZonedDateTime expireDate;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public Stock available(Integer available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getPreOrder() {
        return preOrder;
    }

    public Stock preOrder(Integer preOrder) {
        this.preOrder = preOrder;
        return this;
    }

    public void setPreOrder(Integer preOrder) {
        this.preOrder = preOrder;
    }

    public Integer getReserved() {
        return reserved;
    }

    public Stock reserved(Integer reserved) {
        this.reserved = reserved;
        return this;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public ZonedDateTime getExpireDate() {
        return expireDate;
    }

    public Stock expireDate(ZonedDateTime expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(ZonedDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Stock creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Stock warehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        return this;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public Stock product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        if (stock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, stock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + id +
            ", available='" + available + "'" +
            ", preOrder='" + preOrder + "'" +
            ", reserved='" + reserved + "'" +
            ", expireDate='" + expireDate + "'" +
            ", creationDate='" + creationDate + "'" +
            '}';
    }
}
