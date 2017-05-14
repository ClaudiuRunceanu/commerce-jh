package com.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;

import com.commerce.domain.enumeration.OrderStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * A CustomerOrder.
 */
@Entity
@Table(name = "customer_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 70)
    @Column(name = "code", length = 70, nullable = false)
    private String code;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @DecimalMin(value = "0")
    @Column(name = "tax_cost")
    private Double taxCost;

    @DecimalMin(value = "0")
    @Column(name = "payment_cost")
    private Double paymentCost;

    @DecimalMin(value = "0")
    @Column(name = "delivery_cost")
    private Double deliveryCost;

    @DecimalMin(value = "0")
    @Column(name = "discount_value")
    private Double discountValue;

    @Min(value = 0)
    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @OneToMany(mappedBy = "customerOrder", orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OrderEntry> entries = new ArrayList<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CustomerOrder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public CustomerOrder date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public CustomerOrder totalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public CustomerOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTaxCost() {
        return taxCost;
    }

    public CustomerOrder taxCost(Double taxCost) {
        this.taxCost = taxCost;
        return this;
    }

    public void setTaxCost(Double taxCost) {
        this.taxCost = taxCost;
    }

    public Double getPaymentCost() {
        return paymentCost;
    }

    public CustomerOrder paymentCost(Double paymentCost) {
        this.paymentCost = paymentCost;
        return this;
    }

    public void setPaymentCost(Double paymentCost) {
        this.paymentCost = paymentCost;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public CustomerOrder deliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
        return this;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public CustomerOrder discountValue(Double discountValue) {
        this.discountValue = discountValue;
        return this;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public CustomerOrder discountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public List<OrderEntry> getEntries() {
        return entries;
    }

    public CustomerOrder entries(List<OrderEntry> orderEntries) {
        this.entries = orderEntries;
        return this;
    }

    public CustomerOrder addEntries(OrderEntry orderEntry) {
        this.entries.add(orderEntry);
        orderEntry.setCustomerOrder(this);
        return this;
    }

    public CustomerOrder removeEntries(OrderEntry orderEntry) {
        this.entries.remove(orderEntry);
        orderEntry.setCustomerOrder(null);
        return this;
    }

    public void setEntries(List<OrderEntry> orderEntries) {
        this.entries = orderEntries;
    }

    public User getUser() {
        return user;
    }

    public CustomerOrder user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerOrder customerOrder = (CustomerOrder) o;
        if (customerOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", date='" + date + "'" +
            ", totalCost='" + totalCost + "'" +
            ", status='" + status + "'" +
            ", taxCost='" + taxCost + "'" +
            ", paymentCost='" + paymentCost + "'" +
            ", deliveryCost='" + deliveryCost + "'" +
            ", discountValue='" + discountValue + "'" +
            ", discountPercentage='" + discountPercentage + "'" +
            '}';
    }
}
