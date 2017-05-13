package com.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CustomFilter.
 */
@Entity
@Table(name = "custom_filter")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 70)
    @Column(name = "name", length = 70, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "jhi_value", length = 100, nullable = false)
    private String value;

    @NotNull
    @Size(max = 100)
    @Column(name = "display_value", length = 100, nullable = false)
    private String displayValue;

    @Column(name = "active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CustomFilter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public CustomFilter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public CustomFilter displayValue(String displayValue) {
        this.displayValue = displayValue;
        return this;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Boolean isActive() {
        return active;
    }

    public CustomFilter active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomFilter customFilter = (CustomFilter) o;
        if (customFilter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customFilter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomFilter{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", displayValue='" + displayValue + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
