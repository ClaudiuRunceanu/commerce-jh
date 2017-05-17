package com.commerce.service;

import com.commerce.domain.*;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.CatalogDto;
import com.commerce.service.mapper.CatalogConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class used for product synchronization.
 */
@Service
@Transactional
public class SynchronizeService {

    private final CatalogConverter catalogConverter;
    private final ProductRepository productRepository;

    public SynchronizeService(CatalogConverter catalogConverter, ProductRepository productRepository) {
        this.catalogConverter = catalogConverter;
        this.productRepository = productRepository;
    }

    public void synchronizeProducts(CatalogDto currentCatalogDto, CatalogDto sourceCatalogDto) {
        Catalog currentCatalogModel = catalogConverter.getCatalogModel(currentCatalogDto);
        Catalog sourceCatalogModel = catalogConverter.getCatalogModel(sourceCatalogDto);

        List<Product> sourceProducts = productRepository.findByCatalog(sourceCatalogModel);
        List<Product> newlyProductList = new ArrayList<>();
        for (Product sourceProduct : sourceProducts) {
            Product productForCurrentCatalog = new Product();
            productForCurrentCatalog.setCatalog(currentCatalogModel);

            copyProductDetails(sourceProduct, productForCurrentCatalog);
            copyMediaDetails(sourceProduct, productForCurrentCatalog);
            copyStockDetails(sourceProduct, productForCurrentCatalog);


            newlyProductList.add(productForCurrentCatalog);
        }

        productRepository.save(newlyProductList);
    }

    private void copyProductDetails(Product source, Product target) {
        List<Category> categories = new ArrayList<>();
        categories.addAll(source.getCategories());
        target.setCategories(categories);
        target.setDescription(source.getDescription());
        target.setName(source.getName());
        target.setCode(source.getCode());
        target.setPrice(retrivesPrice(source.getPrice()));
    }


    private void copyStockDetails(Product sourceProduct, Product currentProduct) {
        for (Stock stock : sourceProduct.getStocks()) {
            Stock newlyStock = new Stock();

            newlyStock.setAvailable(stock.getAvailable());
            newlyStock.setWarehouse(stock.getWarehouse());
            newlyStock.setExpireDate(stock.getExpireDate());
            newlyStock.setCreationDate(stock.getCreationDate());
            newlyStock.setPreOrder(stock.getPreOrder());
            newlyStock.setReserved(stock.getReserved());
            newlyStock.setProduct(currentProduct);

            currentProduct.addStocks(newlyStock);
        }
    }

    private void copyMediaDetails(Product sourceProduct, Product currentProduct) {

        for (Media media : sourceProduct.getMedia()) {
            Media newlyMedia = new Media();
            newlyMedia.setCode(media.getCode());
            newlyMedia.setImage(media.getImage());
            newlyMedia.setImageContentType(media.getImageContentType());
            newlyMedia.setTitle(media.getTitle());
            newlyMedia.setProduct(currentProduct);
            currentProduct.addMedia(newlyMedia);
        }

    }


    private Price retrivesPrice(Price price) {
        Price newlyPrice = new Price();
        newlyPrice.setCurrency(price.getCurrency());
        newlyPrice.setValue(price.getValue());
        return newlyPrice;
    }
}
