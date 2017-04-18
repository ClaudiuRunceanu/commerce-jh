package com.commerce.web.rest;

import com.commerce.CommerceApp;

import com.commerce.domain.ClassificationClassAttribute;
import com.commerce.repository.ClassificationClassAttributeRepository;
import com.commerce.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.commerce.domain.enumeration.ClassificationAttributeType;
/**
 * Test class for the ClassificationClassAttributeResource REST controller.
 *
 * @see ClassificationClassAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommerceApp.class)
public class ClassificationClassAttributeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final ClassificationAttributeType DEFAULT_TYPE = ClassificationAttributeType.STRING;
    private static final ClassificationAttributeType UPDATED_TYPE = ClassificationAttributeType.DOUBLE;

    @Autowired
    private ClassificationClassAttributeRepository classificationClassAttributeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassificationClassAttributeMockMvc;

    private ClassificationClassAttribute classificationClassAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassificationClassAttributeResource classificationClassAttributeResource = new ClassificationClassAttributeResource(classificationClassAttributeRepository);
        this.restClassificationClassAttributeMockMvc = MockMvcBuilders.standaloneSetup(classificationClassAttributeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassificationClassAttribute createEntity(EntityManager em) {
        ClassificationClassAttribute classificationClassAttribute = new ClassificationClassAttribute()
            .description(DEFAULT_DESCRIPTION)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .value(DEFAULT_VALUE)
            .type(DEFAULT_TYPE);
        return classificationClassAttribute;
    }

    @Before
    public void initTest() {
        classificationClassAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassificationClassAttribute() throws Exception {
        int databaseSizeBeforeCreate = classificationClassAttributeRepository.findAll().size();

        // Create the ClassificationClassAttribute
        restClassificationClassAttributeMockMvc.perform(post("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationClassAttribute)))
            .andExpect(status().isCreated());

        // Validate the ClassificationClassAttribute in the database
        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ClassificationClassAttribute testClassificationClassAttribute = classificationClassAttributeList.get(classificationClassAttributeList.size() - 1);
        assertThat(testClassificationClassAttribute.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClassificationClassAttribute.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testClassificationClassAttribute.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testClassificationClassAttribute.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createClassificationClassAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classificationClassAttributeRepository.findAll().size();

        // Create the ClassificationClassAttribute with an existing ID
        classificationClassAttribute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificationClassAttributeMockMvc.perform(post("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationClassAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAttributeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificationClassAttributeRepository.findAll().size();
        // set the field null
        classificationClassAttribute.setAttributeName(null);

        // Create the ClassificationClassAttribute, which fails.

        restClassificationClassAttributeMockMvc.perform(post("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationClassAttribute)))
            .andExpect(status().isBadRequest());

        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificationClassAttributeRepository.findAll().size();
        // set the field null
        classificationClassAttribute.setValue(null);

        // Create the ClassificationClassAttribute, which fails.

        restClassificationClassAttributeMockMvc.perform(post("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationClassAttribute)))
            .andExpect(status().isBadRequest());

        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = classificationClassAttributeRepository.findAll().size();
        // set the field null
        classificationClassAttribute.setType(null);

        // Create the ClassificationClassAttribute, which fails.

        restClassificationClassAttributeMockMvc.perform(post("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationClassAttribute)))
            .andExpect(status().isBadRequest());

        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassificationClassAttributes() throws Exception {
        // Initialize the database
        classificationClassAttributeRepository.saveAndFlush(classificationClassAttribute);

        // Get all the classificationClassAttributeList
        restClassificationClassAttributeMockMvc.perform(get("/api/classification-class-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationClassAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getClassificationClassAttribute() throws Exception {
        // Initialize the database
        classificationClassAttributeRepository.saveAndFlush(classificationClassAttribute);

        // Get the classificationClassAttribute
        restClassificationClassAttributeMockMvc.perform(get("/api/classification-class-attributes/{id}", classificationClassAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classificationClassAttribute.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassificationClassAttribute() throws Exception {
        // Get the classificationClassAttribute
        restClassificationClassAttributeMockMvc.perform(get("/api/classification-class-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassificationClassAttribute() throws Exception {
        // Initialize the database
        classificationClassAttributeRepository.saveAndFlush(classificationClassAttribute);
        int databaseSizeBeforeUpdate = classificationClassAttributeRepository.findAll().size();

        // Update the classificationClassAttribute
        ClassificationClassAttribute updatedClassificationClassAttribute = classificationClassAttributeRepository.findOne(classificationClassAttribute.getId());
        updatedClassificationClassAttribute
            .description(UPDATED_DESCRIPTION)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .value(UPDATED_VALUE)
            .type(UPDATED_TYPE);

        restClassificationClassAttributeMockMvc.perform(put("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassificationClassAttribute)))
            .andExpect(status().isOk());

        // Validate the ClassificationClassAttribute in the database
        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeUpdate);
        ClassificationClassAttribute testClassificationClassAttribute = classificationClassAttributeList.get(classificationClassAttributeList.size() - 1);
        assertThat(testClassificationClassAttribute.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClassificationClassAttribute.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testClassificationClassAttribute.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClassificationClassAttribute.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassificationClassAttribute() throws Exception {
        int databaseSizeBeforeUpdate = classificationClassAttributeRepository.findAll().size();

        // Create the ClassificationClassAttribute

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassificationClassAttributeMockMvc.perform(put("/api/classification-class-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classificationClassAttribute)))
            .andExpect(status().isCreated());

        // Validate the ClassificationClassAttribute in the database
        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassificationClassAttribute() throws Exception {
        // Initialize the database
        classificationClassAttributeRepository.saveAndFlush(classificationClassAttribute);
        int databaseSizeBeforeDelete = classificationClassAttributeRepository.findAll().size();

        // Get the classificationClassAttribute
        restClassificationClassAttributeMockMvc.perform(delete("/api/classification-class-attributes/{id}", classificationClassAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassificationClassAttribute> classificationClassAttributeList = classificationClassAttributeRepository.findAll();
        assertThat(classificationClassAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationClassAttribute.class);
    }
}
