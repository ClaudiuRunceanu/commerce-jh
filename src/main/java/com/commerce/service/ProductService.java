package com.commerce.service;

import com.commerce.domain.Product;
import com.commerce.domain.Stock;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.ProductDto;
import com.commerce.service.mapper.ProductConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ProductDto> getAllProducts() {
        List<Product> productModels = this.productRepository.findAllWithEagerRelationships();
        return productConverter.getProductDataList(productModels);
    }

    public ProductDto saveNewProduct(ProductDto productDTO) {
        Product product = this.productRepository.save(this.productConverter.getProductModel(productDTO));
        return this.productConverter.getProductData(product);
    }

    public void deleteProductById(Long id) {
        this.productRepository.delete(id);
    }

    public ProductDto getProductById(Long id) {
        Product product = this.productRepository.findOneWithEagerRelationships(id);
        return this.productConverter.getProductData(product);
    }

    public Product updateProductStockInvolvedInOrder(Long id, Integer quantity){
        Product product=this.productRepository.findOneWithEagerRelationships(id);
        return  updateProductStockInvolvedInOrder(product,quantity);
    }

    public Product updateProductStockInvolvedInOrder(Product product, Integer quantity) {

        for (Stock stock : product.getStocks()) {
            if (stock.getAvailable() > 0) {
                Integer currentAvailable = stock.getAvailable();
                Integer currentReserved=0;

                if( stock.getReserved()!=null){
                    currentReserved=stock.getReserved();
                }

                stock.setAvailable(currentAvailable - quantity);
                stock.setReserved(currentReserved + quantity);

                break;
            }
        }

        return product;
    }

    public Page<ProductDto> getAllProductPages(Pageable pageable) {
        return productRepository.findAll(pageable).map(productConverter::getProductData);
    }


}
