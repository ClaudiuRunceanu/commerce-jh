package com.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.commerce.domain.enumeration.CatalogVersion;

/**
 * A Catalog.
 */
@Entity
@Table(name = "catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "is_default")
    private Boolean isDefault;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "version", nullable = false)
    private CatalogVersion version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Catalog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public Catalog isDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public CatalogVersion getVersion() {
        return version;
    }

    public Catalog version(CatalogVersion version) {
        this.version = version;
        return this;
    }

    public void setVersion(CatalogVersion version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalog catalog = (Catalog) o;
        if (catalog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, catalog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Catalog{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", isDefault='" + isDefault + "'" +
            ", version='" + version + "'" +
            '}';
    }
}
