package com.commerce.web.rest;

import com.commerce.CommerceApp;

import com.commerce.domain.CustomerOrder;
import com.commerce.repository.CustomerOrderRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.commerce.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.commerce.domain.enumeration.OrderStatus;
/**
 * Test class for the CustomerOrderResource REST controller.
 *
 * @see CustomerOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommerceApp.class)
public class CustomerOrderResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_TOTAL_COST = 0D;
    private static final Double UPDATED_TOTAL_COST = 1D;

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.OPEN;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.SUCCESS;

    private static final Double DEFAULT_TAX_COST = 0D;
    private static final Double UPDATED_TAX_COST = 1D;

    private static final Double DEFAULT_PAYMENT_COST = 0D;
    private static final Double UPDATED_PAYMENT_COST = 1D;

    private static final Double DEFAULT_DELIVERY_COST = 0D;
    private static final Double UPDATED_DELIVERY_COST = 1D;

    private static final Double DEFAULT_DISCOUNT_VALUE = 0D;
    private static final Double UPDATED_DISCOUNT_VALUE = 1D;

    private static final Integer DEFAULT_DISCOUNT_PERCENTAGE = 0;
    private static final Integer UPDATED_DISCOUNT_PERCENTAGE = 1;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOrderMockMvc;

    private CustomerOrder customerOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOrderResource customerOrderResource = new CustomerOrderResource(customerOrderRepository);
        this.restCustomerOrderMockMvc = MockMvcBuilders.standaloneSetup(customerOrderResource)
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
    public static CustomerOrder createEntity(EntityManager em) {
        CustomerOrder customerOrder = new CustomerOrder()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE)
            .totalCost(DEFAULT_TOTAL_COST)
            .status(DEFAULT_STATUS)
            .taxCost(DEFAULT_TAX_COST)
            .paymentCost(DEFAULT_PAYMENT_COST)
            .deliveryCost(DEFAULT_DELIVERY_COST)
            .discountValue(DEFAULT_DISCOUNT_VALUE)
            .discountPercentage(DEFAULT_DISCOUNT_PERCENTAGE);
        return customerOrder;
    }

    @Before
    public void initTest() {
        customerOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOrder() throws Exception {
        int databaseSizeBeforeCreate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder
        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isCreated());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOrder testCustomerOrder = customerOrderList.get(customerOrderList.size() - 1);
        assertThat(testCustomerOrder.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerOrder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCustomerOrder.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testCustomerOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerOrder.getTaxCost()).isEqualTo(DEFAULT_TAX_COST);
        assertThat(testCustomerOrder.getPaymentCost()).isEqualTo(DEFAULT_PAYMENT_COST);
        assertThat(testCustomerOrder.getDeliveryCost()).isEqualTo(DEFAULT_DELIVERY_COST);
        assertThat(testCustomerOrder.getDiscountValue()).isEqualTo(DEFAULT_DISCOUNT_VALUE);
        assertThat(testCustomerOrder.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createCustomerOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder with an existing ID
        customerOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setCode(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setDate(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setTotalCost(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOrderRepository.findAll().size();
        // set the field null
        customerOrder.setStatus(null);

        // Create the CustomerOrder, which fails.

        restCustomerOrderMockMvc.perform(post("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isBadRequest());

        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOrders() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        // Get all the customerOrderList
        restCustomerOrderMockMvc.perform(get("/api/customer-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].taxCost").value(hasItem(DEFAULT_TAX_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentCost").value(hasItem(DEFAULT_PAYMENT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryCost").value(hasItem(DEFAULT_DELIVERY_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].discountValue").value(hasItem(DEFAULT_DISCOUNT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE)));
    }

    @Test
    @Transactional
    public void getCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);

        // Get the customerOrder
        restCustomerOrderMockMvc.perform(get("/api/customer-orders/{id}", customerOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOrder.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.taxCost").value(DEFAULT_TAX_COST.doubleValue()))
            .andExpect(jsonPath("$.paymentCost").value(DEFAULT_PAYMENT_COST.doubleValue()))
            .andExpect(jsonPath("$.deliveryCost").value(DEFAULT_DELIVERY_COST.doubleValue()))
            .andExpect(jsonPath("$.discountValue").value(DEFAULT_DISCOUNT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOrder() throws Exception {
        // Get the customerOrder
        restCustomerOrderMockMvc.perform(get("/api/customer-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);
        int databaseSizeBeforeUpdate = customerOrderRepository.findAll().size();

        // Update the customerOrder
        CustomerOrder updatedCustomerOrder = customerOrderRepository.findOne(customerOrder.getId());
        updatedCustomerOrder
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .totalCost(UPDATED_TOTAL_COST)
            .status(UPDATED_STATUS)
            .taxCost(UPDATED_TAX_COST)
            .paymentCost(UPDATED_PAYMENT_COST)
            .deliveryCost(UPDATED_DELIVERY_COST)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE);

        restCustomerOrderMockMvc.perform(put("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOrder)))
            .andExpect(status().isOk());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeUpdate);
        CustomerOrder testCustomerOrder = customerOrderList.get(customerOrderList.size() - 1);
        assertThat(testCustomerOrder.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerOrder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCustomerOrder.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testCustomerOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerOrder.getTaxCost()).isEqualTo(UPDATED_TAX_COST);
        assertThat(testCustomerOrder.getPaymentCost()).isEqualTo(UPDATED_PAYMENT_COST);
        assertThat(testCustomerOrder.getDeliveryCost()).isEqualTo(UPDATED_DELIVERY_COST);
        assertThat(testCustomerOrder.getDiscountValue()).isEqualTo(UPDATED_DISCOUNT_VALUE);
        assertThat(testCustomerOrder.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOrder() throws Exception {
        int databaseSizeBeforeUpdate = customerOrderRepository.findAll().size();

        // Create the CustomerOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOrderMockMvc.perform(put("/api/customer-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOrder)))
            .andExpect(status().isCreated());

        // Validate the CustomerOrder in the database
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOrder() throws Exception {
        // Initialize the database
        customerOrderRepository.saveAndFlush(customerOrder);
        int databaseSizeBeforeDelete = customerOrderRepository.findAll().size();

        // Get the customerOrder
        restCustomerOrderMockMvc.perform(delete("/api/customer-orders/{id}", customerOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
        assertThat(customerOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOrder.class);
    }
}
