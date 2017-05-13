package com.commerce.web.rest;

import com.commerce.CommerceApp;

import com.commerce.domain.CustomFilter;
import com.commerce.repository.CustomFilterRepository;
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

/**
 * Test class for the CustomFilterResource REST controller.
 *
 * @see CustomFilterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommerceApp.class)
public class CustomFilterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomFilterRepository customFilterRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomFilterMockMvc;

    private CustomFilter customFilter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomFilterResource customFilterResource = new CustomFilterResource(customFilterRepository);
        this.restCustomFilterMockMvc = MockMvcBuilders.standaloneSetup(customFilterResource)
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
    public static CustomFilter createEntity(EntityManager em) {
        CustomFilter customFilter = new CustomFilter()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .displayValue(DEFAULT_DISPLAY_VALUE)
            .active(DEFAULT_ACTIVE);
        return customFilter;
    }

    @Before
    public void initTest() {
        customFilter = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomFilter() throws Exception {
        int databaseSizeBeforeCreate = customFilterRepository.findAll().size();

        // Create the CustomFilter
        restCustomFilterMockMvc.perform(post("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customFilter)))
            .andExpect(status().isCreated());

        // Validate the CustomFilter in the database
        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeCreate + 1);
        CustomFilter testCustomFilter = customFilterList.get(customFilterList.size() - 1);
        assertThat(testCustomFilter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomFilter.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustomFilter.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testCustomFilter.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomFilterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customFilterRepository.findAll().size();

        // Create the CustomFilter with an existing ID
        customFilter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomFilterMockMvc.perform(post("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customFilter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customFilterRepository.findAll().size();
        // set the field null
        customFilter.setName(null);

        // Create the CustomFilter, which fails.

        restCustomFilterMockMvc.perform(post("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customFilter)))
            .andExpect(status().isBadRequest());

        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = customFilterRepository.findAll().size();
        // set the field null
        customFilter.setValue(null);

        // Create the CustomFilter, which fails.

        restCustomFilterMockMvc.perform(post("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customFilter)))
            .andExpect(status().isBadRequest());

        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = customFilterRepository.findAll().size();
        // set the field null
        customFilter.setDisplayValue(null);

        // Create the CustomFilter, which fails.

        restCustomFilterMockMvc.perform(post("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customFilter)))
            .andExpect(status().isBadRequest());

        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomFilters() throws Exception {
        // Initialize the database
        customFilterRepository.saveAndFlush(customFilter);

        // Get all the customFilterList
        restCustomFilterMockMvc.perform(get("/api/custom-filters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customFilter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCustomFilter() throws Exception {
        // Initialize the database
        customFilterRepository.saveAndFlush(customFilter);

        // Get the customFilter
        restCustomFilterMockMvc.perform(get("/api/custom-filters/{id}", customFilter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customFilter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.displayValue").value(DEFAULT_DISPLAY_VALUE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomFilter() throws Exception {
        // Get the customFilter
        restCustomFilterMockMvc.perform(get("/api/custom-filters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomFilter() throws Exception {
        // Initialize the database
        customFilterRepository.saveAndFlush(customFilter);
        int databaseSizeBeforeUpdate = customFilterRepository.findAll().size();

        // Update the customFilter
        CustomFilter updatedCustomFilter = customFilterRepository.findOne(customFilter.getId());
        updatedCustomFilter
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .active(UPDATED_ACTIVE);

        restCustomFilterMockMvc.perform(put("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomFilter)))
            .andExpect(status().isOk());

        // Validate the CustomFilter in the database
        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeUpdate);
        CustomFilter testCustomFilter = customFilterList.get(customFilterList.size() - 1);
        assertThat(testCustomFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomFilter.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustomFilter.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testCustomFilter.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomFilter() throws Exception {
        int databaseSizeBeforeUpdate = customFilterRepository.findAll().size();

        // Create the CustomFilter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomFilterMockMvc.perform(put("/api/custom-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customFilter)))
            .andExpect(status().isCreated());

        // Validate the CustomFilter in the database
        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomFilter() throws Exception {
        // Initialize the database
        customFilterRepository.saveAndFlush(customFilter);
        int databaseSizeBeforeDelete = customFilterRepository.findAll().size();

        // Get the customFilter
        restCustomFilterMockMvc.perform(delete("/api/custom-filters/{id}", customFilter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomFilter> customFilterList = customFilterRepository.findAll();
        assertThat(customFilterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomFilter.class);
    }
}
