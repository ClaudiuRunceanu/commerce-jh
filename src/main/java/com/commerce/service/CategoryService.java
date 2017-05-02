package com.commerce.service;

import com.commerce.domain.Category;
import com.commerce.repository.CategoryRepository;
import com.commerce.service.dto.CategoryDto;
import com.commerce.service.mapper.CategoryConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for category.
 */
@Service
@Transactional
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;

    public CategoryService(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    public CategoryDto createNewCategory(CategoryDto categoryDto){
        Category category=categoryRepository.save(categoryConverter.getCategoryModel(categoryDto));
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    public CategoryDto updateCategory(CategoryDto categoryDto){
        this.categoryRepository.save(categoryConverter.getCategoryModel(categoryDto));
        return categoryDto;
    }

    public List<CategoryDto> getAllCategories(){
        List<CategoryDto> dataList=categoryConverter.getCategoryDataList(categoryRepository.findAll());
       return dataList;
    }

    public CategoryDto findCategoryById(Long id){
        Category category=categoryRepository.findOne(id);
        return categoryConverter.getCategoryData(category);
    }

    public void deleteCategory(Long id){
        categoryRepository.delete(id);
    }

}
