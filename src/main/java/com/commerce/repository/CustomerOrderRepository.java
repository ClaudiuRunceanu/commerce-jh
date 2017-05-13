package com.commerce.repository;

import com.commerce.domain.CustomerOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOrder entity.
 */
@SuppressWarnings("unused")
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {

    @Query("select customerOrder from CustomerOrder customerOrder where customerOrder.user.login = ?#{principal.username}")
    List<CustomerOrder> findByUserIsCurrentUser();

}
