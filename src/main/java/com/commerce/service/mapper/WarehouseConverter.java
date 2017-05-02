package com.commerce.service.mapper;

import com.commerce.domain.Warehouse;
import com.commerce.service.dto.WarehouseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for warehouse.
 */
@Component
public class WarehouseConverter {

    public WarehouseDto getWarehouseData(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(warehouse.getId());
        warehouseDto.setName(warehouse.getName());
        warehouseDto.setAddress(warehouse.getAddress());
        warehouseDto.setConsignments(warehouse.getConsignments());
        return warehouseDto;
    }

    public Warehouse getWarehouseModel(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        if (warehouseDto.getId() != null) {
            warehouse.setId(warehouseDto.getId());
        }
        warehouse.setName(warehouseDto.getName());
        warehouse.setConsignments(warehouseDto.getConsignments());
        warehouse.setAddress(warehouseDto.getAddress());
        return warehouse;
    }

    public List<WarehouseDto> getWarehouseDataList(List<Warehouse> warehouses) {
        List<WarehouseDto> dtoList = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            WarehouseDto dto = getWarehouseData(warehouse);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
