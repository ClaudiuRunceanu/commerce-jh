package com.commerce.service.mapper;

import com.commerce.domain.ClassificationClassAttribute;
import com.commerce.service.dto.ClassificationClassAttributeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for ClassificationClassAttribute.
 */
@Component
public class ClassificationClassAttributeConverter {

    private CategoryConverter categoryConverter;

    public ClassificationClassAttributeConverter(CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    public ClassificationClassAttributeDto getData(ClassificationClassAttribute model){
        ClassificationClassAttributeDto data= new ClassificationClassAttributeDto();
        data.setId(model.getId());
        data.setAttributeName(model.getAttributeName());
        data.setCategory(categoryConverter.getCategoryData(model.getCategory()));
        data.setDescription(model.getDescription());
        data.setValue(model.getValue());
        data.setType(model.getType());
      return data;
    }

    public ClassificationClassAttribute getModel(ClassificationClassAttributeDto data){
        ClassificationClassAttribute model=new ClassificationClassAttribute();
        if(data.getId()!=null){
            model.setId(data.getId());
        }
        model.setAttributeName(data.getAttributeName());
        model.setDescription(data.getDescription());
        model.setType(data.getType());
        model.setCategory(categoryConverter.getCategoryModel(data.getCategory()));

        return model;
    }

    public List<ClassificationClassAttributeDto> getAllData(List<ClassificationClassAttribute> models){
        List<ClassificationClassAttributeDto> dataList = new ArrayList<>();
        for (ClassificationClassAttribute model:models){
            ClassificationClassAttributeDto data=getData(model);
            dataList.add(data);
        }
        return dataList;
    }
}
