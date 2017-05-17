package com.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "code", length = 100, nullable = false)
    private String code;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Price price;

    @OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Media> media = new ArrayList<>();

    @ManyToOne
    private Catalog catalog;

    @ManyToMany
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_categories",
        joinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private List<Category> categories = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public Product price(Price price) {
        this.price = price;
        return this;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Product stocks(List<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Product addStocks(Stock stock) {
        this.stocks.add(stock);
        stock.setProduct(this);
        return this;
    }

    public Product removeStocks(Stock stock) {
        this.stocks.remove(stock);
        stock.setProduct(null);
        return this;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Media> getMedia() {
        return media;
    }

    public Product media(List<Media> media) {
        this.media = media;
        return this;
    }

    public Product addMedia(Media media) {
        this.media.add(media);
        media.setProduct(this);
        return this;
    }

    public Product removeMedia(Media media) {
        this.media.remove(media);
        media.setProduct(null);
        return this;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Product catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Product categories(List<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Product addCategories(Category category) {
        this.categories.add(category);
        category.getProducts().add(this);
        return this;
    }

    public Product removeCategories(Category category) {
        this.categories.remove(category);
        category.getProducts().remove(this);
        return this;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
