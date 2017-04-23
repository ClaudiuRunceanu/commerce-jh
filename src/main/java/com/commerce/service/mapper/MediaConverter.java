package com.commerce.service.mapper;

import com.commerce.domain.Media;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.MediaDto;
import org.springframework.stereotype.Component;

/**
 * Converter class for media.
 */
@Component
public class MediaConverter {
    private final ProductRepository productRepository;

    public MediaConverter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Media getMediaModel(MediaDto mediaDto) {
        Media media = new Media();
        media.setImage(mediaDto.getImage());
        media.setImageContentType(mediaDto.getImageContentType());
        media.setTitle(mediaDto.getTitle());
        media.setCode(mediaDto.getCode());
        if (mediaDto.getProductId() != null) {
            media.setProduct(this.productRepository.findOne(mediaDto.getProductId()));
        }
        return media;
    }
}
