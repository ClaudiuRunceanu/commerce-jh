package com.commerce.service;

import com.commerce.domain.Catalog;
import com.commerce.repository.CatalogRepository;
import com.commerce.service.dto.CatalogDto;
import com.commerce.service.mapper.CatalogConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for catalog.
 */
@Service
@Transactional
public class CatalogService {
    private CatalogRepository catalogRepository;
    private CatalogConverter catalogConverter;

    public CatalogService(CatalogRepository catalogRepository, CatalogConverter catalogConverter) {
        this.catalogRepository = catalogRepository;
        this.catalogConverter = catalogConverter;
    }

    public CatalogDto createNewCatalog(CatalogDto catalogDto) {
        Catalog catalog = catalogRepository.save(catalogConverter.getCatalogModel(catalogDto));
        catalogDto.setId(catalog.getId());
        return catalogDto;
    }

    public CatalogDto updateCatalog(CatalogDto catalogDto) {
        this.catalogRepository.save(catalogConverter.getCatalogModel(catalogDto));
        return catalogDto;
    }

    public List<CatalogDto> getAllCatalogs() {
        List<Catalog> catalogs = this.catalogRepository.findAll();
        return catalogConverter.getCatalogDataList(catalogs);
    }

    public void deleteCatalog(Long id) {
        this.catalogRepository.delete(id);
    }

    public CatalogDto findCatalogById(Long id) {
        Catalog catalog = catalogRepository.findOne(id);
        return catalogConverter.getCatalogData(catalog);
    }

}
