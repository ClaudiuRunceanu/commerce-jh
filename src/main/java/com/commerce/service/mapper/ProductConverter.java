package com.commerce.service.mapper;

import com.commerce.domain.Price;
import com.commerce.domain.Product;
import com.commerce.domain.Stock;
import com.commerce.repository.StockRepository;
import com.commerce.service.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for product
 */
@Component
public class ProductConverter {

    private StockConverter stockConverter;
    private CurrencyConverter currencyConverter;
    private CategoryConverter categoryConverter;
    private MediaConverter mediaConverter;
    private StockRepository stockRepository;
    private PriceConverter priceConverter;

    public ProductConverter(StockConverter stockConverter, CurrencyConverter currencyConverter, CategoryConverter categoryConverter, MediaConverter mediaConverter, StockRepository stockRepository, PriceConverter priceConverter) {
        this.stockConverter = stockConverter;
        this.currencyConverter = currencyConverter;
        this.categoryConverter = categoryConverter;
        this.mediaConverter = mediaConverter;
        this.stockRepository = stockRepository;
        this.priceConverter = priceConverter;
    }

    public ProductDto getProductData(Product product) {
        ProductDto productDTO = new ProductDto();
        productDTO.setCatalog(product.getCatalog());
        productDTO.setCode(product.getCode());
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());

        populateDataWithCategoriesDetail(product, productDTO);
        populateDataWithMediaDetail(product, productDTO);
        populateDataWithStockDetail(product, productDTO);
        populateDataWithPriceDetail(product, productDTO);

        return productDTO;
    }

    public List<ProductDto> getProductDataList(List<Product> productModels) {
        List<ProductDto> productDTOList = new ArrayList<>();
        for (Product product : productModels) {
            ProductDto productDTO = getProductData(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    public Product getProductModel(ProductDto productDTO) {
        Product product = new Product();
        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCatalog(productDTO.getCatalog());

        if (productDTO.getMedia() != null) {
            populateModelWithMedia(productDTO, product);
        }

        if (productDTO.getStocks() != null) {
            populateModelWithStock(productDTO, product);
        }

        populateModelWithCategoriesDetail(productDTO, product);
        populateModelWithPriceDetail(productDTO, product);
        return product;
    }

    private void populateModelWithStock(ProductDto productDTO, Product product) {
        product.setStocks(stockConverter.getStockModelList(productDTO.getStocks()));
    }

    private void populateModelWithMedia(ProductDto productDTO, Product product) {
      product.setMedia(mediaConverter.getMediaModelList(productDTO.getMedia()));
    }

    private void populateModelWithCategoriesDetail(ProductDto productDTO, Product product) {
        product.setCategories(categoryConverter.getCategoryModelList(productDTO.getCategories()));
    }

    private void populateModelWithPriceDetail(ProductDto productDTO, Product product) {
        product.setPrice(priceConverter.getPriceModel(productDTO.getPrice()));
    }

    private void populateDataWithPriceDetail(Product product, ProductDto productDTO) {
        if (product.getPrice() != null) {
            productDTO.setPrice(priceConverter.getPriceData(product.getPrice()));
        }
    }

    private void populateDataWithStockDetail(Product product, ProductDto productDTO) {
        List<Stock> stocks = this.stockRepository.findStockByProduct(product);
        productDTO.setStocks(this.stockConverter.getStockDataList(stocks));
    }

    private void populateDataWithCategoriesDetail(Product product, ProductDto productDTO) {
        productDTO.setCategories(categoryConverter.getCategoryDataList(product.getCategories()));
    }

    private void populateDataWithMediaDetail(Product product, ProductDto productDTO) {
        if (product.getMedia() != null) {
            productDTO.setMedia(mediaConverter.getMediaDataList(product.getMedia()));
        }
    }

}
