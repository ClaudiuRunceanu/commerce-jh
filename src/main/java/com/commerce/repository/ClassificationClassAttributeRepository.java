package com.commerce.repository;

import com.commerce.domain.ClassificationClassAttribute;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClassificationClassAttribute entity.
 */
@SuppressWarnings("unused")
public interface ClassificationClassAttributeRepository extends JpaRepository<ClassificationClassAttribute,Long> {

}
