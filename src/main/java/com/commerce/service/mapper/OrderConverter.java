package com.commerce.service.mapper;

import com.commerce.domain.CustomerOrder;
import com.commerce.service.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for order.
 */
@Component
public class OrderConverter {
    private OrderEntryConverter orderEntryConverter;

    public OrderConverter(OrderEntryConverter orderEntryConverter) {
        this.orderEntryConverter = orderEntryConverter;
    }

    public OrderDto getOrderData(CustomerOrder model) {
        OrderDto data = new OrderDto();
        data.setId(model.getId());
        data.setCode(model.getCode());
        data.setDate(model.getDate());
        data.setDiscountPercentage(model.getDiscountPercentage());
        data.setDiscountValue(model.getDiscountValue());
        data.setPaymentCost(model.getPaymentCost());
        data.setStatus(model.getStatus());
        data.setDeliveryCost(model.getDeliveryCost());
        data.setTaxCost(model.getTaxCost());
        data.setTotalCost(model.getTotalCost());
        data.setUser(model.getUser());
        data.setEntries(orderEntryConverter.getOrderEntryDataList(model.getEntries()));
        return data;
    }

    public CustomerOrder getOrderModel(OrderDto data) {
        CustomerOrder model = new CustomerOrder();
        if (data.getId() != null) {
            model.setId(data.getId());
        }
        model.setCode(data.getCode());
        model.setDate(data.getDate());
        model.setDiscountPercentage(data.getDiscountPercentage());
        model.setDiscountValue(data.getDiscountValue());
        model.setPaymentCost(data.getPaymentCost());
        model.setStatus(data.getStatus());
        model.setDeliveryCost(data.getDeliveryCost());
        model.setTaxCost(data.getTaxCost());
        model.setTotalCost(data.getTotalCost());
        model.setUser(data.getUser());
        model.setEntries(orderEntryConverter.getOrderEntryModelList(data.getEntries()));
        return model;
    }

    public List<OrderDto> getOrderDataList(List<CustomerOrder> modelList) {
        List<OrderDto> dataList = new ArrayList<>();
        for (CustomerOrder model : modelList) {
            OrderDto data = getOrderData(model);
            dataList.add(data);
        }
        return dataList;
    }


}
