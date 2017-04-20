package com.commerce.service;

import com.commerce.domain.Product;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.ProductDTO;
import com.commerce.service.mapper.ProductConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for product.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public ProductService(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productModels = this.productRepository.findAllWithEagerRelationships();
        return productConverter.getProductDataList(productModels);
    }

    public Product saveNewProduct(ProductDTO productDTO){
        Product product=this.productConverter.getProductModel(productDTO);
        return this.productRepository.save(product);
    }

    public ProductDTO getProductById(Long id){
     Product product = this.productRepository.findOneWithEagerRelationships(id);
        return this.productConverter.getProductData(product);
    }

}
