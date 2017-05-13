package com.commerce.repository;

import com.commerce.domain.OrderEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderEntry entity.
 */
@SuppressWarnings("unused")
public interface OrderEntryRepository extends JpaRepository<OrderEntry,Long> {

}
