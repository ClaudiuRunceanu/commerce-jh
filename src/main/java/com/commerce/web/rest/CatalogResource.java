package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.service.CatalogService;
import com.commerce.service.dto.CatalogDto;
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
 * REST controller for managing Catalog.
 */
@RestController
@RequestMapping("/api")
public class CatalogResource {

    private final Logger log = LoggerFactory.getLogger(CatalogResource.class);

    private static final String ENTITY_NAME = "catalog";

    private final CatalogService catalogService;


    public CatalogResource(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * POST  /catalogs : Create a new catalog.
     *
     * @param catalog the catalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new catalog, or with status 400 (Bad Request) if the catalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/catalogs")
    @Timed
    public ResponseEntity<CatalogDto> createCatalog(@Valid @RequestBody CatalogDto catalog) throws URISyntaxException {
        log.debug("REST request to save Catalog : {}", catalog);
        if (catalog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new catalog cannot already have an ID")).body(null);
        }
        CatalogDto result = catalogService.createNewCatalog(catalog);
        return ResponseEntity.created(new URI("/api/catalogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /catalogs : Updates an existing catalog.
     *
     * @param catalog the catalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated catalog,
     * or with status 400 (Bad Request) if the catalog is not valid,
     * or with status 500 (Internal Server Error) if the catalog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/catalogs")
    @Timed
    public ResponseEntity<CatalogDto> updateCatalog(@Valid @RequestBody CatalogDto catalog) throws URISyntaxException {
        log.debug("REST request to update Catalog : {}", catalog);
        if (catalog.getId() == null) {
            return createCatalog(catalog);
        }
        CatalogDto result = catalogService.updateCatalog(catalog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, catalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /catalogs : get all the catalogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of catalogs in body
     */
    @GetMapping("/catalogs")
    @Timed
    public List<CatalogDto> getAllCatalogs() {
        log.debug("REST request to get all Catalogs");
        List<CatalogDto> catalogs = catalogService.getAllCatalogs();
        return catalogs;
    }

    /**
     * GET  /catalogs/:id : get the "id" catalog.
     *
     * @param id the id of the catalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the catalog, or with status 404 (Not Found)
     */
    @GetMapping("/catalogs/{id}")
    @Timed
    public ResponseEntity<CatalogDto> getCatalog(@PathVariable Long id) {
        log.debug("REST request to get Catalog : {}", id);
        CatalogDto catalog = catalogService.findCatalogById(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(catalog));
    }

    /**
     * DELETE  /catalogs/:id : delete the "id" catalog.
     *
     * @param id the id of the catalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/catalogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCatalog(@PathVariable Long id) {
        log.debug("REST request to delete Catalog : {}", id);
        catalogService.deleteCatalog(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /catalogs : Replace products from source catalog in current catalog.
     *
     * @param sourceCatalog the catalog that holds products that will be inserted in current catalog.
     * @return the ResponseEntity with status 201 (Created) and with body the new catalog, or with status 400 (Bad Request) if the catalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/catalogs/synchronize/{id}")
    @Timed
    public ResponseEntity<CatalogDto> synchronizeCatalog(@Valid @RequestBody CatalogDto sourceCatalog, @PathVariable Long currentCatalogId) throws URISyntaxException {
        //todo implement service for synchronization.
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(this.catalogService.findCatalogById(currentCatalogId)));
    }

}
