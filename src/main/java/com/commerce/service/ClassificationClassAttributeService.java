package com.commerce.service;

import com.commerce.domain.ClassificationClassAttribute;
import com.commerce.repository.ClassificationClassAttributeRepository;
import com.commerce.service.dto.ClassificationClassAttributeDto;
import com.commerce.service.mapper.ClassificationClassAttributeConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for ClassificationClassAttribute.
 */
@Service
@Transactional
public class ClassificationClassAttributeService {
    private ClassificationClassAttributeRepository classificationClassAttributeRepository;
    private ClassificationClassAttributeConverter classificationClassAttributeConverter;

    public ClassificationClassAttributeService(ClassificationClassAttributeRepository classificationClassAttributeRepository, ClassificationClassAttributeConverter classificationClassAttributeConverter) {
        this.classificationClassAttributeRepository = classificationClassAttributeRepository;
        this.classificationClassAttributeConverter = classificationClassAttributeConverter;
    }

    public ClassificationClassAttributeDto createNewClassification(ClassificationClassAttributeDto data) {
        ClassificationClassAttribute model = classificationClassAttributeRepository.save(classificationClassAttributeConverter.getModel(data));
        data.setId(model.getId());
        return data;
    }

    public ClassificationClassAttributeDto updateClassification(ClassificationClassAttributeDto data) {
        classificationClassAttributeRepository.save(classificationClassAttributeConverter.getModel(data));
        return data;
    }

    public ClassificationClassAttributeDto findClassificationById(Long id) {
        return classificationClassAttributeConverter.getData(classificationClassAttributeRepository.findOne(id));
    }

    public void deleteClassification(Long id) {
        classificationClassAttributeRepository.delete(id);
    }

    public List<ClassificationClassAttributeDto> getAllClassifications() {
        return classificationClassAttributeConverter.getAllData(classificationClassAttributeRepository.findAll());
    }

}
