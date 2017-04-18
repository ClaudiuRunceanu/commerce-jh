package com.commerce.repository;

import com.commerce.domain.Price;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Price entity.
 */
@SuppressWarnings("unused")
public interface PriceRepository extends JpaRepository<Price,Long> {

}
