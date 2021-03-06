package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.service.WarehouseService;
import com.commerce.service.dto.WarehouseDto;
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
 * REST controller for managing Warehouse.
 */
@RestController
@RequestMapping("/api")
public class WarehouseResource {

    private final Logger log = LoggerFactory.getLogger(WarehouseResource.class);

    private static final String ENTITY_NAME = "warehouse";

    private final WarehouseService warehouseService;

    public WarehouseResource(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * POST  /warehouses : Create a new warehouse.
     *
     * @param warehouse the warehouse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new warehouse, or with status 400 (Bad Request) if the warehouse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/warehouses")
    @Timed
    public ResponseEntity<WarehouseDto> createWarehouse(@Valid @RequestBody WarehouseDto warehouse) throws URISyntaxException {
        log.debug("REST request to save Warehouse : {}", warehouse);
        if (warehouse.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new warehouse cannot already have an ID")).body(null);
        }

        WarehouseDto result = warehouseService.createNewWarehouse(warehouse);
        return ResponseEntity.created(new URI("/api/warehouses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /warehouses : Updates an existing warehouse.
     *
     * @param warehouse the warehouse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated warehouse,
     * or with status 400 (Bad Request) if the warehouse is not valid,
     * or with status 500 (Internal Server Error) if the warehouse couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/warehouses")
    @Timed
    public ResponseEntity<WarehouseDto> updateWarehouse(@Valid @RequestBody WarehouseDto warehouse) throws URISyntaxException {
        log.debug("REST request to update Warehouse : {}", warehouse);
        if (warehouse.getId() == null) {
            return createWarehouse(warehouse);
        }
        WarehouseDto result = warehouseService.updateWarehouse(warehouse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, warehouse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /warehouses : get all the warehouses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of warehouses in body
     */
    @GetMapping("/warehouses")
    @Timed
    public List<WarehouseDto> getAllWarehouses() {
        log.debug("REST request to get all Warehouses");
        return warehouseService.getAllWarehouse();
    }

    /**
     * GET  /warehouses/:id : get the "id" warehouse.
     *
     * @param id the id of the warehouse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the warehouse, or with status 404 (Not Found)
     */
    @GetMapping("/warehouses/{id}")
    @Timed
    public ResponseEntity<WarehouseDto> getWarehouse(@PathVariable Long id) {
        log.debug("REST request to get Warehouse : {}", id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(warehouseService.findWarehouseById(id)));
    }

    /**
     * DELETE  /warehouses/:id : delete the "id" warehouse.
     *
     * @param id the id of the warehouse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/warehouses/{id}")
    @Timed
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        log.debug("REST request to delete Warehouse : {}", id);
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
