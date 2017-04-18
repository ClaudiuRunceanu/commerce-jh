package com.commerce.web.rest;

import com.commerce.CommerceApp;

import com.commerce.domain.Catalog;
import com.commerce.repository.CatalogRepository;
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

import com.commerce.domain.enumeration.CatalogVersion;
/**
 * Test class for the CatalogResource REST controller.
 *
 * @see CatalogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommerceApp.class)
public class CatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final CatalogVersion DEFAULT_VERSION = CatalogVersion.ONLINE;
    private static final CatalogVersion UPDATED_VERSION = CatalogVersion.STAGE;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCatalogMockMvc;

    private Catalog catalog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CatalogResource catalogResource = new CatalogResource(catalogRepository);
        this.restCatalogMockMvc = MockMvcBuilders.standaloneSetup(catalogResource)
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
    public static Catalog createEntity(EntityManager em) {
        Catalog catalog = new Catalog()
            .name(DEFAULT_NAME)
            .isDefault(DEFAULT_IS_DEFAULT)
            .version(DEFAULT_VERSION);
        return catalog;
    }

    @Before
    public void initTest() {
        catalog = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalog() throws Exception {
        int databaseSizeBeforeCreate = catalogRepository.findAll().size();

        // Create the Catalog
        restCatalogMockMvc.perform(post("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isCreated());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeCreate + 1);
        Catalog testCatalog = catalogList.get(catalogList.size() - 1);
        assertThat(testCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCatalog.isIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testCatalog.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createCatalogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogRepository.findAll().size();

        // Create the Catalog with an existing ID
        catalog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogMockMvc.perform(post("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogRepository.findAll().size();
        // set the field null
        catalog.setName(null);

        // Create the Catalog, which fails.

        restCatalogMockMvc.perform(post("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isBadRequest());

        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = catalogRepository.findAll().size();
        // set the field null
        catalog.setVersion(null);

        // Create the Catalog, which fails.

        restCatalogMockMvc.perform(post("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isBadRequest());

        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCatalogs() throws Exception {
        // Initialize the database
        catalogRepository.saveAndFlush(catalog);

        // Get all the catalogList
        restCatalogMockMvc.perform(get("/api/catalogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getCatalog() throws Exception {
        // Initialize the database
        catalogRepository.saveAndFlush(catalog);

        // Get the catalog
        restCatalogMockMvc.perform(get("/api/catalogs/{id}", catalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(catalog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCatalog() throws Exception {
        // Get the catalog
        restCatalogMockMvc.perform(get("/api/catalogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalog() throws Exception {
        // Initialize the database
        catalogRepository.saveAndFlush(catalog);
        int databaseSizeBeforeUpdate = catalogRepository.findAll().size();

        // Update the catalog
        Catalog updatedCatalog = catalogRepository.findOne(catalog.getId());
        updatedCatalog
            .name(UPDATED_NAME)
            .isDefault(UPDATED_IS_DEFAULT)
            .version(UPDATED_VERSION);

        restCatalogMockMvc.perform(put("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatalog)))
            .andExpect(status().isOk());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeUpdate);
        Catalog testCatalog = catalogList.get(catalogList.size() - 1);
        assertThat(testCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCatalog.isIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testCatalog.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalog() throws Exception {
        int databaseSizeBeforeUpdate = catalogRepository.findAll().size();

        // Create the Catalog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCatalogMockMvc.perform(put("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isCreated());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCatalog() throws Exception {
        // Initialize the database
        catalogRepository.saveAndFlush(catalog);
        int databaseSizeBeforeDelete = catalogRepository.findAll().size();

        // Get the catalog
        restCatalogMockMvc.perform(delete("/api/catalogs/{id}", catalog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Catalog.class);
    }
}
