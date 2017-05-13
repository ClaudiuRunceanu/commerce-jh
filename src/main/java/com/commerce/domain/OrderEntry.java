package com.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OrderEntry.
 */
@Entity
@Table(name = "order_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "jhi_value", nullable = false)
    private Double value;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private CustomerOrder customerOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderEntry quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getValue() {
        return value;
    }

    public OrderEntry value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public OrderEntry product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public OrderEntry cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public OrderEntry customerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
        return this;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderEntry orderEntry = (OrderEntry) o;
        if (orderEntry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderEntry{" +
            "id=" + id +
            ", quantity='" + quantity + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
