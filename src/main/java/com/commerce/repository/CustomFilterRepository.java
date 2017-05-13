package com.commerce.repository;

import com.commerce.domain.CustomFilter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomFilter entity.
 */
@SuppressWarnings("unused")
public interface CustomFilterRepository extends JpaRepository<CustomFilter,Long> {

}
