package com.commerce.service.mapper;

import com.commerce.domain.Cart;
import com.commerce.service.dto.CartDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for cart.
 */
@Component
public class CartConverter {
    private OrderEntryConverter orderEntryConverter;

    public CartConverter(OrderEntryConverter orderEntryConverter) {
        this.orderEntryConverter = orderEntryConverter;
    }

    public CartDto getCartData(Cart model) {
        CartDto data = new CartDto();
        data.setId(model.getId());
        data.setCode(model.getCode());
        data.setUser(model.getUser());
        data.setEntries(orderEntryConverter.getOrderEntryDataList(model.getEntries()));
        return data;
    }

    public Cart getCartModel(CartDto data) {
        Cart model = new Cart();
        if (data.getId() != null) {
            model.setId(data.getId());
        }
        model.setCode(data.getCode());
        model.setUser(data.getUser());
        model.setEntries(orderEntryConverter.getOrderEntryModelList(data.getEntries()));
        return model;
    }

    public List<CartDto> getCartDataList(List<Cart> modelList) {
        List<CartDto> dataList = new ArrayList<>();

        for (Cart model : modelList) {
            CartDto data = getCartData(model);
            dataList.add(data);
        }

        return dataList;
    }
}
