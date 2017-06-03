package com.commerce.service.mapper;

import com.commerce.domain.OrderEntry;
import com.commerce.domain.Product;
import com.commerce.repository.CustomerOrderRepository;
import com.commerce.service.ProductService;
import com.commerce.service.dto.OrderEntryDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for order entry.
 */
@Component
public class OrderEntryConverter {
    private ProductConverter productConverter;
    private CustomerOrderRepository orderRepository;
    private ProductService productService;

    public OrderEntryConverter(ProductConverter productConverter, CustomerOrderRepository orderRepository, ProductService productService) {
        this.productConverter = productConverter;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public OrderEntryDto getOrderEntryData(OrderEntry model) {
        OrderEntryDto data = new OrderEntryDto();
        data.setValue(model.getValue());
        data.setId(model.getId());
        data.setQuantity(model.getQuantity());
        data.setProduct(productConverter.getProductData(model.getProduct()));

        if (model.getCustomerOrder() != null) {
            data.setOrderId(model.getCustomerOrder().getId());
        }

        return data;
    }

    public OrderEntry getOrderEntryModel(OrderEntryDto data) {
        OrderEntry model = new OrderEntry();
        if (data.getId() != null) {
            model.setId(data.getId());
        }

        model.setQuantity(data.getQuantity());
        if (data.getProductOrderId() != null) {
            //find product and update stock
            Product product = productService.updateProductStockInvolvedInOrder(data.getProductOrderId(), data.getQuantity());
            model.setProduct(product);
        } else {
            Product product = productConverter.getProductModel(data.getProduct());
            model.setProduct(productService.updateProductStockInvolvedInOrder(product, data.getQuantity()));
        }

        Double value=model.getProduct().getPrice().getValue()*data.getQuantity();
        model.setValue(value);

        if (data.getOrderId() != null) {
            model.setCustomerOrder(orderRepository.findOne(data.getOrderId()));
        }

        return model;
    }

    public List<OrderEntryDto> getOrderEntryDataList(List<OrderEntry> modelList) {
        List<OrderEntryDto> dataList = new ArrayList<>();
        for (OrderEntry model : modelList) {
            OrderEntryDto data = getOrderEntryData(model);
            dataList.add(data);
        }
        return dataList;
    }

    public List<OrderEntry> getOrderEntryModelList(List<OrderEntryDto> dataList) {
        List<OrderEntry> modelList = new ArrayList<>();
        for (OrderEntryDto data : dataList) {
            OrderEntry model = getOrderEntryModel(data);
            modelList.add(model);
        }
        return modelList;
    }
}
