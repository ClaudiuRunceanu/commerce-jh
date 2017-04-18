package com.commerce.service.mapper;

import com.commerce.domain.Product;
import com.commerce.service.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Claudiu on 4/15/2017.
 */
public class ProductConverter {

    public  static ProductDTO getProductData(Product product){
        ProductDTO productDTO=new ProductDTO();

        productDTO.setCatalog(product.getCatalog());
        productDTO.setCode(product.getCode());
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setStocks(product.getStocks());
        productDTO.setMedia(product.getMedia());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategories(product.getCategories());

        productDTO.setPriceValue(product.getPrice().getValue());
        productDTO.setCurrencySymbol(product.getPrice().getCurrency().getSymbol());

        return productDTO;
    }

    public static List<ProductDTO> getProductDataList(List<Product> productModels){
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(Product product: productModels){
            ProductDTO productDTO= ProductConverter.getProductData(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

}
