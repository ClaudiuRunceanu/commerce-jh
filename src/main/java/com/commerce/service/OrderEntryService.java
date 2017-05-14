package com.commerce.service;

import com.commerce.domain.OrderEntry;
import com.commerce.repository.OrderEntryRepository;
import com.commerce.service.dto.OrderEntryDto;
import com.commerce.service.mapper.OrderEntryConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for order entry.
 */
@Service
@Transactional
public class OrderEntryService {
    private OrderEntryRepository orderEntryRepository;
    private OrderEntryConverter orderEntryConverter;

    public OrderEntryService(OrderEntryRepository orderEntryRepository, OrderEntryConverter orderEntryConverter) {
        this.orderEntryRepository = orderEntryRepository;
        this.orderEntryConverter = orderEntryConverter;
    }

    public OrderEntryDto createNewOrderEntry(OrderEntryDto data){
        OrderEntry model= orderEntryRepository.save(orderEntryConverter.getOrderEntryModel(data));
        data.setId(model.getId());
        return data;
    }


    public OrderEntryDto updateOrderEntry(OrderEntryDto data){
        orderEntryRepository.save(orderEntryConverter.getOrderEntryModel(data));
        return data;
    }

    public OrderEntryDto findOrderEntryById(Long id){
      OrderEntry model=orderEntryRepository.findOne(id);
      return orderEntryConverter.getOrderEntryData(model);
    }

    public void deleteOrderEntry(Long id){
        orderEntryRepository.delete(id);
    }

    public List<OrderEntryDto> getAllOrderEntries(){
        List<OrderEntry> modelList=orderEntryRepository.findAll();
        return orderEntryConverter.getOrderEntryDataList(modelList);
    }
}


