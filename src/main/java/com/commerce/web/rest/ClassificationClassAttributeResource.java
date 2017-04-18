package com.commerce.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.commerce.domain.ClassificationClassAttribute;

import com.commerce.repository.ClassificationClassAttributeRepository;
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
 * REST controller for managing ClassificationClassAttribute.
 */
@RestController
@RequestMapping("/api")
public class ClassificationClassAttributeResource {

    private final Logger log = LoggerFactory.getLogger(ClassificationClassAttributeResource.class);

    private static final String ENTITY_NAME = "classificationClassAttribute";
        
    private final ClassificationClassAttributeRepository classificationClassAttributeRepository;

    public ClassificationClassAttributeResource(ClassificationClassAttributeRepository classificationClassAttributeRepository) {
        this.classificationClassAttributeRepository = classificationClassAttributeRepository;
    }

    /**
     * POST  /classification-class-attributes : Create a new classificationClassAttribute.
     *
     * @param classificationClassAttribute the classificationClassAttribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classificationClassAttribute, or with status 400 (Bad Request) if the classificationClassAttribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classification-class-attributes")
    @Timed
    public ResponseEntity<ClassificationClassAttribute> createClassificationClassAttribute(@Valid @RequestBody ClassificationClassAttribute classificationClassAttribute) throws URISyntaxException {
        log.debug("REST request to save ClassificationClassAttribute : {}", classificationClassAttribute);
        if (classificationClassAttribute.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classificationClassAttribute cannot already have an ID")).body(null);
        }
        ClassificationClassAttribute result = classificationClassAttributeRepository.save(classificationClassAttribute);
        return ResponseEntity.created(new URI("/api/classification-class-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classification-class-attributes : Updates an existing classificationClassAttribute.
     *
     * @param classificationClassAttribute the classificationClassAttribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classificationClassAttribute,
     * or with status 400 (Bad Request) if the classificationClassAttribute is not valid,
     * or with status 500 (Internal Server Error) if the classificationClassAttribute couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classification-class-attributes")
    @Timed
    public ResponseEntity<ClassificationClassAttribute> updateClassificationClassAttribute(@Valid @RequestBody ClassificationClassAttribute classificationClassAttribute) throws URISyntaxException {
        log.debug("REST request to update ClassificationClassAttribute : {}", classificationClassAttribute);
        if (classificationClassAttribute.getId() == null) {
            return createClassificationClassAttribute(classificationClassAttribute);
        }
        ClassificationClassAttribute result = classificationClassAttributeRepository.save(classificationClassAttribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classificationClassAttribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classification-class-attributes : get all the classificationClassAttributes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classificationClassAttributes in body
     */
    @GetMapping("/classification-class-attributes")
    @Timed
    public List<ClassificationClassAttribute> getAllClassificationClassAttributes() {
        log.debug("REST request to get all ClassificationClassAttributes");
        List<ClassificationClassAttribute> classificationClassAttributes = classificationClassAttributeRepository.findAll();
        return classificationClassAttributes;
    }

    /**
     * GET  /classification-class-attributes/:id : get the "id" classificationClassAttribute.
     *
     * @param id the id of the classificationClassAttribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classificationClassAttribute, or with status 404 (Not Found)
     */
    @GetMapping("/classification-class-attributes/{id}")
    @Timed
    public ResponseEntity<ClassificationClassAttribute> getClassificationClassAttribute(@PathVariable Long id) {
        log.debug("REST request to get ClassificationClassAttribute : {}", id);
        ClassificationClassAttribute classificationClassAttribute = classificationClassAttributeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classificationClassAttribute));
    }

    /**
     * DELETE  /classification-class-attributes/:id : delete the "id" classificationClassAttribute.
     *
     * @param id the id of the classificationClassAttribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classification-class-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassificationClassAttribute(@PathVariable Long id) {
        log.debug("REST request to delete ClassificationClassAttribute : {}", id);
        classificationClassAttributeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
