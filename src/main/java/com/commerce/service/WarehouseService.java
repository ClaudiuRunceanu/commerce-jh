package com.commerce.service;

import com.commerce.domain.Warehouse;
import com.commerce.repository.WarehouseRepository;
import com.commerce.service.dto.WarehouseDto;
import com.commerce.service.mapper.WarehouseConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for warehouse.
 */
@Service
@Transactional
public class WarehouseService {
    private WarehouseRepository warehouseRepository;
    private WarehouseConverter warehouseConverter;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseConverter warehouseConverter) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseConverter = warehouseConverter;
    }

    public WarehouseDto createNewWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = this.warehouseRepository.save(this.warehouseConverter.getWarehouseModel(warehouseDto));
        warehouseDto.setId(warehouse.getId());
        return warehouseDto;
    }

    public WarehouseDto updateWarehouse(WarehouseDto warehouseDto) {
        this.warehouseRepository.save(this.warehouseConverter.getWarehouseModel(warehouseDto));
        return warehouseDto;
    }

    public List<WarehouseDto> getAllWarehouse() {
        List<Warehouse> warehouseList = this.warehouseRepository.findAll();
        return this.warehouseConverter.getWarehouseDataList(warehouseList);
    }

    public WarehouseDto findWarehouseById(Long id) {
        return this.warehouseConverter.getWarehouseData(this.warehouseRepository.findOne(id));
    }

    public void deleteWarehouse(Long id) {
        this.warehouseRepository.delete(id);
    }
}
