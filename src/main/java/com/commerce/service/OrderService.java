package com.commerce.service;

import com.commerce.domain.CustomerOrder;
import com.commerce.repository.CustomerOrderRepository;
import com.commerce.service.dto.OrderDto;
import com.commerce.service.mapper.OrderConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for customer order.
 */
@Service
@Transactional
public class OrderService {
    private CustomerOrderRepository orderRepository;
    private OrderConverter orderConverter;

    public OrderService(CustomerOrderRepository orderRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    public OrderDto createNewOrder(OrderDto data) {
        CustomerOrder model = orderRepository.save(orderConverter.getOrderModel(data));
        data.setId(model.getId());
        return data;
    }

    public OrderDto updateOrder(OrderDto data) {
        orderRepository.save(orderConverter.getOrderModel(data));
        return data;
    }

    public OrderDto findById(Long id) {
        CustomerOrder model = orderRepository.findOne(id);
        return orderConverter.getOrderData(model);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }

    public List<OrderDto> getAllOrders() {
        List<CustomerOrder> modelList = orderRepository.findAll();
        return orderConverter.getOrderDataList(modelList);
    }
}
