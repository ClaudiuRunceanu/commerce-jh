package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.Product;

import com.commerce.repository.ProductRepository;
import com.commerce.service.ProductService;
import com.commerce.service.dto.ProductDTO;
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
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";


    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    /**
     * POST  /products : Create a new product.
     *
     * @param product the product to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product, or with status 400 (Bad Request) if the product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    @Timed
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new product cannot already have an ID")).body(null);
        }
       // Product result = productRepository.save(product);
        Product result = productService.saveNewProduct(product);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products : Updates an existing product.
     *
     * @param product the product to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product,
     * or with status 400 (Bad Request) if the product is not valid,
     * or with status 500 (Internal Server Error) if the product couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    @Timed
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDTO product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);
        if (product.getId() == null) {
            return createProduct(product);
        }
     //   Product result = productRepository.save(new Product());
        Product result = productService.saveNewProduct(product);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, product.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products : get all the products.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/products")
    @Timed
    public List<ProductDTO> getAllProducts() {
        log.debug("REST request to get all Products");
//        List<Product> products = productRepository.findAllWithEagerRelationships();
        return productService.getAllProducts();
    }


    /**
     * GET  /products/:id : get the "id" product.
     *
     * @param id the id of the product to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    @Timed
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
       // Product product = productRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productService.getProductById(id)));
    }

    /**
     * DELETE  /products/:id : delete the "id" product.
     *
     * @param id the id of the product to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.deleteProductById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
