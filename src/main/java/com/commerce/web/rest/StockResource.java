package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.Stock;

import com.commerce.repository.StockRepository;
import com.commerce.service.StockService;
import com.commerce.service.dto.StockDto;
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
 * REST controller for managing Stock.
 */
@RestController
@RequestMapping("/api")
public class StockResource {

    private final Logger log = LoggerFactory.getLogger(StockResource.class);

    private static final String ENTITY_NAME = "stock";

    private final StockRepository stockRepository;

    private final StockService stockService;

    public StockResource(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }


    /**
     * POST  /stocks : Create a new stock.
     *
     * @param stock the stock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stock, or with status 400 (Bad Request) if the stock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stocks")
    @Timed
    public ResponseEntity<StockDto> createStock(@Valid @RequestBody StockDto stock) throws URISyntaxException {
        log.debug("REST request to save Stock : {}", stock);
        if (stock.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stock cannot already have an ID")).body(null);
        }
     //   Stock result = stockRepository.save(stock);
        StockDto result = stockService.createStockForProduct(stock);
        return ResponseEntity.created(new URI("/api/stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stocks : Updates an existing stock.
     *
     * @param stock the stock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stock,
     * or with status 400 (Bad Request) if the stock is not valid,
     * or with status 500 (Internal Server Error) if the stock couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stocks")
    @Timed
    public ResponseEntity<StockDto> updateStock(@Valid @RequestBody StockDto stock) throws URISyntaxException {
        log.debug("REST request to update Stock : {}", stock);


        if (stock.getId() == null) {
            return createStock(stock);
        }
        StockDto result = stockService.updateStock(stock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stocks : get all the stocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stocks in body
     */
    @GetMapping("/stocks")
    @Timed
    public List<StockDto> getAllStocks() {
        log.debug("REST request to get all Stocks");
        List<StockDto> stocks = stockService.getAllStocks();
        return stocks;
    }

    /**
     * GET  /stocks/:id : get the "id" stock.
     *
     * @param id the id of the stock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stock, or with status 404 (Not Found)
     */
    @GetMapping("/stocks/{id}")
    @Timed
    public ResponseEntity<StockDto> getStock(@PathVariable Long id) {
        log.debug("REST request to get Stock : {}", id);
        StockDto stock = stockService.getStockById(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stock));
    }

    /**
     * DELETE  /stocks/:id : delete the "id" stock.
     *
     * @param id the id of the stock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        log.debug("REST request to delete Stock : {}", id);
        stockService.deleteStock(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
