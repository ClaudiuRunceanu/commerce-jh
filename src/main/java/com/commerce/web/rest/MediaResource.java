package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.Media;

import com.commerce.repository.MediaRepository;
import com.commerce.service.MediaService;
import com.commerce.service.dto.MediaDto;
import com.commerce.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

    private final Logger log = LoggerFactory.getLogger(MediaResource.class);

    private static final String ENTITY_NAME = "media";

    private final MediaRepository mediaRepository;

    private final MediaService mediaService;

    public MediaResource(MediaRepository mediaRepository, MediaService mediaService) {
        this.mediaRepository = mediaRepository;
        this.mediaService = mediaService;
    }

    /**
     * POST  /media : Create a new media.
     *
     * @param media the media to create
     * @return the ResponseEntity with status 201 (Created) and with body the new media, or with status 400 (Bad Request) if the media has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media")
    @Timed
    public ResponseEntity<MediaDto> createMedia(@Valid @RequestBody MediaDto media) throws URISyntaxException {
        log.debug("REST request to save Media : {}", media);
        if (media.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new media cannot already have an ID")).body(null);
        }
        //Media result = mediaRepository.save(media);
        MediaDto result = this.mediaService.createMediaForProduct(media);
        return ResponseEntity.created(new URI("/api/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media : Updates an existing media.
     *
     * @param media the media to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated media,
     * or with status 400 (Bad Request) if the media is not valid,
     * or with status 500 (Internal Server Error) if the media couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media")
    @Timed
    public ResponseEntity<Media> updateMedia(@Valid @RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to update Media : {}", media);
        //temporary hack
//        if (media.getId() == null) {
        //  return createMedia(media);

//        }
        Media result = mediaRepository.save(media);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, media.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media : get all the media.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of media in body
     */
    @GetMapping("/media")
    @Timed
    public List<Media> getAllMedia() {
        log.debug("REST request to get all Media");
        List<Media> media = mediaRepository.findAll();
        return media;
    }

    /**
     * GET  /media/:id : get the "id" media.
     *
     * @param id the id of the media to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the media, or with status 404 (Not Found)
     */
    @GetMapping("/media/{id}")
    @Timed
    public ResponseEntity<Media> getMedia(@PathVariable Long id) {
        log.debug("REST request to get Media : {}", id);
        Media media = mediaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(media));
    }

    /**
     * DELETE  /media/:id : delete the "id" media.
     *
     * @param id the id of the media to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        log.debug("REST request to delete Media : {}", id);
        mediaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
