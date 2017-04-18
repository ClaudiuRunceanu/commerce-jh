package com.commerce.repository;

import com.commerce.domain.Warehouse;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Warehouse entity.
 */
@SuppressWarnings("unused")
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {

}
