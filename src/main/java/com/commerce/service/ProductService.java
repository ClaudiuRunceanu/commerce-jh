package com.commerce.service;

import com.commerce.domain.*;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.CatalogDto;
import com.commerce.service.dto.ProductDto;
import com.commerce.service.mapper.CatalogConverter;
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
    private final CatalogConverter catalogConverter;

    public ProductService(ProductRepository productRepository, ProductConverter productConverter, CatalogConverter catalogConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.catalogConverter = catalogConverter;
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

    public void synchronizeProducts(CatalogDto currentCatalogDto, CatalogDto sourceCatalogDto){
        Catalog currentCatalogModel=catalogConverter.getCatalogModel(currentCatalogDto);
        Catalog sourceCatalogModel=catalogConverter.getCatalogModel(sourceCatalogDto);

      List<Product> products= productRepository.findByCatalog(sourceCatalogModel);
      List<Product> newlyProductList=new ArrayList<>();
      for(Product product:products){
          Product productForCurrentCatalog= new Product();
          productForCurrentCatalog.setCatalog(currentCatalogModel);
          copyProductDetails(product,productForCurrentCatalog);
          newlyProductList.add(productForCurrentCatalog);
      }

      productRepository.save(newlyProductList);
    }

    private void copyProductDetails(Product source, Product target) {
        List<Category> categories=new ArrayList<>();
        categories.addAll(source.getCategories());
        target.setCategories(categories);
        target.setDescription(source.getDescription());
        target.setName(source.getName());
        target.setCode(source.getCode());
        target.setPrice(retrivesPrice(source.getPrice()));
        target.setMedia(retrievesMediaList(source.getMedia()));
       // target.setStocks(retrievesStock(source.getStocks()));
    }

    private List<Media> retrievesMediaList(List<Media> sourceMedia) {
        List<Media> targetMedia=new ArrayList<>();

        for (Media media:sourceMedia){
            Media newlyMedia= new Media();
            newlyMedia.setCode(media.getCode());
            newlyMedia.setImage(media.getImage());
            newlyMedia.setImageContentType(media.getImageContentType());
            newlyMedia.setTitle(media.getTitle());
//            newlyMedia.setProduct(media.getProduct());

            targetMedia.add(newlyMedia);

        }

        return targetMedia;
    }


    private Price retrivesPrice(Price price) {
        Price newlyPrice= new Price();
        newlyPrice.setCurrency(price.getCurrency());
        newlyPrice.setValue(price.getValue());
        return newlyPrice;
    }


}
