package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.CustomerOrder;

import com.commerce.repository.CustomerOrderRepository;
import com.commerce.service.OrderService;
import com.commerce.service.dto.OrderDto;
import com.commerce.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerOrder.
 */
@RestController
@RequestMapping("/api")
public class CustomerOrderResource {

    private final Logger log = LoggerFactory.getLogger(CustomerOrderResource.class);

    private static final String ENTITY_NAME = "customerOrder";

    private final OrderService orderService;

    public CustomerOrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * POST  /customer-orders : Create a new customerOrder.
     *
     * @param customerOrder the customerOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerOrder, or with status 400 (Bad Request) if the customerOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-orders")
    @Timed
    public ResponseEntity<OrderDto> createCustomerOrder(@Valid @RequestBody OrderDto customerOrder) throws URISyntaxException {
        log.debug("REST request to save CustomerOrder : {}", customerOrder);
        if (customerOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerOrder cannot already have an ID")).body(null);
        }
        OrderDto result = orderService.createNewOrder(customerOrder);
        return ResponseEntity.created(new URI("/api/customer-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-orders : Updates an existing customerOrder.
     *
     * @param customerOrder the customerOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerOrder,
     * or with status 400 (Bad Request) if the customerOrder is not valid,
     * or with status 500 (Internal Server Error) if the customerOrder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-orders")
    @Timed
    public ResponseEntity<OrderDto> updateCustomerOrder(@Valid @RequestBody OrderDto customerOrder) throws URISyntaxException {
        log.debug("REST request to update CustomerOrder : {}", customerOrder);
        if (customerOrder.getId() == null) {
            return createCustomerOrder(customerOrder);
        }
        OrderDto result = orderService.updateOrder(customerOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-orders : get all the customerOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerOrders in body
     */
    @GetMapping("/customer-orders")
    @Timed
    public List<OrderDto> getAllCustomerOrders() {
        log.debug("REST request to get all CustomerOrders");
        List<OrderDto> customerOrders = orderService.getAllOrders();
        return customerOrders;
    }

    /**
     * GET  /customer-orders/:id : get the "id" customerOrder.
     *
     * @param id the id of the customerOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerOrder, or with status 404 (Not Found)
     */
    @GetMapping("/customer-orders/{id}")
    @Timed
    public ResponseEntity<OrderDto> getCustomerOrder(@PathVariable Long id) {
        log.debug("REST request to get CustomerOrder : {}", id);
        OrderDto customerOrder = orderService.findById(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerOrder));
    }

    /**
     * DELETE  /customer-orders/:id : delete the "id" customerOrder.
     *
     * @param id the id of the customerOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable Long id) {
        log.debug("REST request to delete CustomerOrder : {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
