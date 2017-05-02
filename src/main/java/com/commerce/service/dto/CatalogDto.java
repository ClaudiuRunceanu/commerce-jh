package com.commerce.service.dto;

import com.commerce.domain.enumeration.CatalogVersion;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data class for catalog.
 */
public class CatalogDto {


    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    private Boolean isDefault;

    @NotNull
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

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsDefault() {
        return isDefault;
    }

    public CatalogDto isDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }


    public CatalogVersion getVersion() {
        return version;
    }

    public void setVersion(CatalogVersion version) {
        this.version = version;
    }
}
