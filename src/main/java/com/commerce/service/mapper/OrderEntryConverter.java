package com.commerce.service.mapper;

import com.commerce.domain.OrderEntry;
import com.commerce.repository.CustomerOrderRepository;
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

    public OrderEntryConverter(ProductConverter productConverter, CustomerOrderRepository orderRepository) {
        this.productConverter = productConverter;
        this.orderRepository = orderRepository;
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
        model.setValue(data.getValue());
        model.setQuantity(data.getQuantity());
        model.setProduct(productConverter.getProductModel(data.getProduct()));

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
