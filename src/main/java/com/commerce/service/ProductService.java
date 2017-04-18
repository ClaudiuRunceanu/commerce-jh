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
 * Created by Claudiu on 4/18/2017.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productModels = this.productRepository.findAllWithEagerRelationships();
        return ProductConverter.getProductDataList(productModels);
    }

}
