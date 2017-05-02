package com.commerce.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data class for category.
 */
public class CategoryDto {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 300)
    private String description;

    private CategoryDto parent;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getParent() {
        return parent;
    }

    public void setParent(CategoryDto parent) {
        this.parent = parent;
    }
}
