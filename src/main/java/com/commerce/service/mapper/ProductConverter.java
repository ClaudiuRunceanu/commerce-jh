package com.commerce.service.mapper;

import com.commerce.domain.Price;
import com.commerce.domain.Product;
import com.commerce.domain.Stock;
import com.commerce.repository.StockRepository;
import com.commerce.service.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for product
 */
@Component
public class ProductConverter {

    private StockConverter stockConverter;
    private StockRepository stockRepository;

    public ProductConverter(StockConverter stockConverter, StockRepository stockRepository) {
        this.stockConverter = stockConverter;
        this.stockRepository = stockRepository;
    }

    public ProductDTO getProductData(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setCatalog(product.getCatalog());
        productDTO.setCode(product.getCode());
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setMedia(product.getMedia());
//        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategories(product.getCategories());

        populateDataWithStockDetail(product, productDTO);
        populateDataWithPriceDetail(product, productDTO);


        return productDTO;
    }

    public List<ProductDTO> getProductDataList(List<Product> productModels) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productModels) {
            ProductDTO productDTO = getProductData(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    public Product getProductModel(ProductDTO productDTO) {
        Product product = new Product();
        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCatalog(productDTO.getCatalog());
        product.setCategories(productDTO.getCategories());
        //stocks
        //media
        populateModelWithPriceDetail(productDTO, product);
        return product;
    }

    private void populateModelWithPriceDetail(ProductDTO productDTO, Product product) {
        Price price = new Price();
        price.setValue(productDTO.getPriceValue());
        price.setCurrency(productDTO.getCurrency());
        product.setPrice(price);
    }

    private void populateDataWithPriceDetail(Product product, ProductDTO productDTO) {
        if (product.getPrice() != null) {
            productDTO.setPriceValue(product.getPrice().getValue());

            if (product.getPrice().getCurrency() != null) {
                productDTO.setCurrencySymbol(product.getPrice().getCurrency().getSymbol());
                productDTO.setCurrency(product.getPrice().getCurrency());
            }
        }
    }

    private void populateDataWithStockDetail(Product product, ProductDTO productDTO) {
        List<Stock> stocks = this.stockRepository.findStockByProduct(product);
        productDTO.setStocks(this.stockConverter.getStockDataList(stocks));
    }

}
