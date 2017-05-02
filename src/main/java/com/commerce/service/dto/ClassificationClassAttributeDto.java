package com.commerce.service.dto;

import com.commerce.domain.enumeration.ClassificationAttributeType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data class for ClassificationClassAttribute.
 */
public class ClassificationClassAttributeDto {

    private Long id;

    @Size(max = 300)
    private String description;

    @NotNull
    @Size(max = 100)
    private String attributeName;

    @NotNull
    @Size(max = 200)
    private String value;

    @NotNull
    private ClassificationAttributeType type;

    private CategoryDto category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ClassificationAttributeType getType() {
        return type;
    }

    public void setType(ClassificationAttributeType type) {
        this.type = type;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
