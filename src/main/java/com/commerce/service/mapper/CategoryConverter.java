package com.commerce.service.mapper;

import com.commerce.domain.Category;
import com.commerce.service.dto.CategoryDto;
import org.springframework.stereotype.Component;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for category
 */
@Component
public class CategoryConverter {

    public CategoryDto getCategoryData(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        if (category.getParent() != null) {
            CategoryDto parent = getCategoryData(category.getParent());
            dto.setParent(parent);
        }
        return dto;
    }

    public Category getCategoryModel(CategoryDto categoryDto) {
        Category model = new Category();
        if (categoryDto.getId() != null) {
            model.setId(categoryDto.getId());
        }
        model.setName(categoryDto.getName());
        model.setDescription(categoryDto.getDescription());
        if (categoryDto.getParent() != null) {
            Category parent = getCategoryModel(categoryDto.getParent());
            model.setParent(parent);
        }
        return model;
    }

    public List<CategoryDto> getCategoryDataList(List<Category> categories) {
        List<CategoryDto> dataList = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto dto = getCategoryData(category);
            dataList.add(dto);
        }
        return dataList;
    }

    public List<Category> getCategoryModelList(List<CategoryDto> categoryDtos) {
        List<Category> categories = new ArrayList<>();
        for (CategoryDto data : categoryDtos) {
            Category category = getCategoryModel(data);
            categories.add(category);
        }
        return categories;
    }
}
