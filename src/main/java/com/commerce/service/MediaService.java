package com.commerce.service;

import com.commerce.domain.Media;
import com.commerce.repository.MediaRepository;
import com.commerce.service.dto.MediaDto;
import com.commerce.service.mapper.MediaConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for media.
 */
@Service
@Transactional
public class MediaService {
    private final MediaConverter mediaConverter;
    private final MediaRepository mediaRepository;

    public MediaService(MediaConverter mediaConverter, MediaRepository mediaRepository) {
        this.mediaConverter = mediaConverter;
        this.mediaRepository = mediaRepository;
    }

    public MediaDto createMediaForProduct(MediaDto mediaDto) {
        Media media = this.mediaRepository.save(this.mediaConverter.getMediaModel(mediaDto));
        mediaDto.setId(media.getId());
        return mediaDto;
    }
}
