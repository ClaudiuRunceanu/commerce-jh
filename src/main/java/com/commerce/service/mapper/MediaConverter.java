package com.commerce.service.mapper;

import com.commerce.domain.Media;
import com.commerce.repository.ProductRepository;
import com.commerce.service.dto.MediaDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        if (mediaDto.getId() != null) {
            media.setId(mediaDto.getId());
        }
        media.setImage(mediaDto.getImage());
        media.setImageContentType(mediaDto.getImageContentType());
        media.setTitle(mediaDto.getTitle());
        media.setCode(mediaDto.getCode());
        if (mediaDto.getProductId() != null) {
            media.setProduct(this.productRepository.findOne(mediaDto.getProductId()));
        }
        return media;
    }

    public MediaDto getMediaData(Media media) {
        MediaDto dto = new MediaDto();
        dto.setId(media.getId());
        dto.setCode(media.getCode());
        dto.setTitle(media.getTitle());
        dto.setImage(media.getImage());
        dto.setImageContentType(media.getImageContentType());
        if(media.getProduct()!=null) {
            dto.setProductId(media.getProduct().getId());
        }
        return dto;
    }

    public List<MediaDto> getMediaDataList(List<Media> mediaList) {
        List<MediaDto> dataList = new ArrayList<>();
        for (Media media : mediaList) {
            MediaDto data=getMediaData(media);
            dataList.add(data);
        }
        return dataList;
    }
}
