package com.commerce.service.mapper;

import com.commerce.domain.Catalog;
import com.commerce.service.dto.CatalogDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter class for catalog
 */
@Component
public class CatalogConverter {

    public Catalog getCatalogModel(CatalogDto catalogDto){
        Catalog catalog=new Catalog();
        if(catalogDto.getId()!=null){
            catalog.setId(catalogDto.getId());
        }
        catalog.setIsDefault(catalogDto.isIsDefault());
        catalog.setName(catalogDto.getName());
        catalog.setVersion(catalogDto.getVersion());
        return catalog;
    }

    public CatalogDto getCatalogData(Catalog catalog){
        CatalogDto catalogDto=new CatalogDto();
        catalogDto.setId(catalog.getId());
        catalogDto.setName(catalog.getName());
        catalogDto.setVersion(catalog.getVersion());
        catalogDto.setIsDefault(catalog.isIsDefault());
        return catalogDto;
    }

    public List<CatalogDto> getCatalogDataList(List<Catalog> catalogList){
        List<CatalogDto> dtoList=new ArrayList<>();
        for(Catalog catalog:catalogList){
            CatalogDto dto=getCatalogData(catalog);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
