package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.CustomFilter;

import com.commerce.repository.CustomFilterRepository;
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
 * REST controller for managing CustomFilter.
 */
@RestController
@RequestMapping("/api")
public class CustomFilterResource {

    private final Logger log = LoggerFactory.getLogger(CustomFilterResource.class);

    private static final String ENTITY_NAME = "customFilter";
        
    private final CustomFilterRepository customFilterRepository;

    public CustomFilterResource(CustomFilterRepository customFilterRepository) {
        this.customFilterRepository = customFilterRepository;
    }

    /**
     * POST  /custom-filters : Create a new customFilter.
     *
     * @param customFilter the customFilter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customFilter, or with status 400 (Bad Request) if the customFilter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/custom-filters")
    @Timed
    public ResponseEntity<CustomFilter> createCustomFilter(@Valid @RequestBody CustomFilter customFilter) throws URISyntaxException {
        log.debug("REST request to save CustomFilter : {}", customFilter);
        if (customFilter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customFilter cannot already have an ID")).body(null);
        }
        CustomFilter result = customFilterRepository.save(customFilter);
        return ResponseEntity.created(new URI("/api/custom-filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /custom-filters : Updates an existing customFilter.
     *
     * @param customFilter the customFilter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customFilter,
     * or with status 400 (Bad Request) if the customFilter is not valid,
     * or with status 500 (Internal Server Error) if the customFilter couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/custom-filters")
    @Timed
    public ResponseEntity<CustomFilter> updateCustomFilter(@Valid @RequestBody CustomFilter customFilter) throws URISyntaxException {
        log.debug("REST request to update CustomFilter : {}", customFilter);
        if (customFilter.getId() == null) {
            return createCustomFilter(customFilter);
        }
        CustomFilter result = customFilterRepository.save(customFilter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customFilter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /custom-filters : get all the customFilters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customFilters in body
     */
    @GetMapping("/custom-filters")
    @Timed
    public List<CustomFilter> getAllCustomFilters() {
        log.debug("REST request to get all CustomFilters");
        List<CustomFilter> customFilters = customFilterRepository.findAll();
        return customFilters;
    }

    /**
     * GET  /custom-filters/:id : get the "id" customFilter.
     *
     * @param id the id of the customFilter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customFilter, or with status 404 (Not Found)
     */
    @GetMapping("/custom-filters/{id}")
    @Timed
    public ResponseEntity<CustomFilter> getCustomFilter(@PathVariable Long id) {
        log.debug("REST request to get CustomFilter : {}", id);
        CustomFilter customFilter = customFilterRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customFilter));
    }

    /**
     * DELETE  /custom-filters/:id : delete the "id" customFilter.
     *
     * @param id the id of the customFilter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/custom-filters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomFilter(@PathVariable Long id) {
        log.debug("REST request to delete CustomFilter : {}", id);
        customFilterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
