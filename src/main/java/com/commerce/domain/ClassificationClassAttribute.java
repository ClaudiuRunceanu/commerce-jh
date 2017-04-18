package com.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.commerce.domain.enumeration.ClassificationAttributeType;

/**
 * A ClassificationClassAttribute.
 */
@Entity
@Table(name = "classification_class_attribute")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassificationClassAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 300)
    @Column(name = "description", length = 300)
    private String description;

    @NotNull
    @Size(max = 100)
    @Column(name = "attribute_name", length = 100, nullable = false)
    private String attributeName;

    @NotNull
    @Size(max = 200)
    @Column(name = "jhi_value", length = 200, nullable = false)
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private ClassificationAttributeType type;

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ClassificationClassAttribute description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public ClassificationClassAttribute attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getValue() {
        return value;
    }

    public ClassificationClassAttribute value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ClassificationAttributeType getType() {
        return type;
    }

    public ClassificationClassAttribute type(ClassificationAttributeType type) {
        this.type = type;
        return this;
    }

    public void setType(ClassificationAttributeType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public ClassificationClassAttribute category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassificationClassAttribute classificationClassAttribute = (ClassificationClassAttribute) o;
        if (classificationClassAttribute.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, classificationClassAttribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassificationClassAttribute{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", attributeName='" + attributeName + "'" +
            ", value='" + value + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
