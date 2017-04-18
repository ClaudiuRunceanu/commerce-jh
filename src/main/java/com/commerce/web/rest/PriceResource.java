package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.Price;

import com.commerce.repository.PriceRepository;
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
 * REST controller for managing Price.
 */
@RestController
@RequestMapping("/api")
public class PriceResource {

    private final Logger log = LoggerFactory.getLogger(PriceResource.class);

    private static final String ENTITY_NAME = "price";
        
    private final PriceRepository priceRepository;

    public PriceResource(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * POST  /prices : Create a new price.
     *
     * @param price the price to create
     * @return the ResponseEntity with status 201 (Created) and with body the new price, or with status 400 (Bad Request) if the price has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prices")
    @Timed
    public ResponseEntity<Price> createPrice(@Valid @RequestBody Price price) throws URISyntaxException {
        log.debug("REST request to save Price : {}", price);
        if (price.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new price cannot already have an ID")).body(null);
        }
        Price result = priceRepository.save(price);
        return ResponseEntity.created(new URI("/api/prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prices : Updates an existing price.
     *
     * @param price the price to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated price,
     * or with status 400 (Bad Request) if the price is not valid,
     * or with status 500 (Internal Server Error) if the price couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prices")
    @Timed
    public ResponseEntity<Price> updatePrice(@Valid @RequestBody Price price) throws URISyntaxException {
        log.debug("REST request to update Price : {}", price);
        if (price.getId() == null) {
            return createPrice(price);
        }
        Price result = priceRepository.save(price);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, price.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prices : get all the prices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prices in body
     */
    @GetMapping("/prices")
    @Timed
    public List<Price> getAllPrices() {
        log.debug("REST request to get all Prices");
        List<Price> prices = priceRepository.findAll();
        return prices;
    }

    /**
     * GET  /prices/:id : get the "id" price.
     *
     * @param id the id of the price to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the price, or with status 404 (Not Found)
     */
    @GetMapping("/prices/{id}")
    @Timed
    public ResponseEntity<Price> getPrice(@PathVariable Long id) {
        log.debug("REST request to get Price : {}", id);
        Price price = priceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(price));
    }

    /**
     * DELETE  /prices/:id : delete the "id" price.
     *
     * @param id the id of the price to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prices/{id}")
    @Timed
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        log.debug("REST request to delete Price : {}", id);
        priceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
