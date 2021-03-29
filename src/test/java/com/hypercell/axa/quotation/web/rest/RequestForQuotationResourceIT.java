package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RequestForQuotation;
import com.hypercell.axa.quotation.domain.enumeration.RFQStatus;
import com.hypercell.axa.quotation.repository.RequestForQuotationRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RequestForQuotationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestForQuotationResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_BROKER_ID = 1L;
    private static final Long UPDATED_BROKER_ID = 2L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final RFQStatus DEFAULT_STATUS = RFQStatus.New;
    private static final RFQStatus UPDATED_STATUS = RFQStatus.Rejected;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_INSURED_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_INSURED_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INSURED = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INSURED = "BBBBBBBBBB";

    private static final String DEFAULT_BENIFICIARY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BENIFICIARY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POLICY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_NUMBER_OF_MONTH = 1;
    private static final Integer UPDATED_NUMBER_OF_MONTH = 2;

    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/request-for-quotations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestForQuotationRepository requestForQuotationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestForQuotationMockMvc;

    private RequestForQuotation requestForQuotation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestForQuotation createEntity(EntityManager em) {
        RequestForQuotation requestForQuotation = new RequestForQuotation()
            .number(DEFAULT_NUMBER)
            .brokerId(DEFAULT_BROKER_ID)
            .customerId(DEFAULT_CUSTOMER_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .insuredNames(DEFAULT_INSURED_NAMES)
            .additionalInsured(DEFAULT_ADDITIONAL_INSURED)
            .benificiaryName(DEFAULT_BENIFICIARY_NAME)
            .policyType(DEFAULT_POLICY_TYPE)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .duration(DEFAULT_DURATION)
            .numberOfMonth(DEFAULT_NUMBER_OF_MONTH)
            .additionalInfo(DEFAULT_ADDITIONAL_INFO);
        return requestForQuotation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestForQuotation createUpdatedEntity(EntityManager em) {
        RequestForQuotation requestForQuotation = new RequestForQuotation()
            .number(UPDATED_NUMBER)
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .benificiaryName(UPDATED_BENIFICIARY_NAME)
            .policyType(UPDATED_POLICY_TYPE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .duration(UPDATED_DURATION)
            .numberOfMonth(UPDATED_NUMBER_OF_MONTH)
            .additionalInfo(UPDATED_ADDITIONAL_INFO);
        return requestForQuotation;
    }

    @BeforeEach
    public void initTest() {
        requestForQuotation = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestForQuotation() throws Exception {
        int databaseSizeBeforeCreate = requestForQuotationRepository.findAll().size();
        // Create the RequestForQuotation
        restRequestForQuotationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isCreated());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeCreate + 1);
        RequestForQuotation testRequestForQuotation = requestForQuotationList.get(requestForQuotationList.size() - 1);
        assertThat(testRequestForQuotation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testRequestForQuotation.getBrokerId()).isEqualTo(DEFAULT_BROKER_ID);
        assertThat(testRequestForQuotation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testRequestForQuotation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRequestForQuotation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequestForQuotation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRequestForQuotation.getInsuredNames()).isEqualTo(DEFAULT_INSURED_NAMES);
        assertThat(testRequestForQuotation.getAdditionalInsured()).isEqualTo(DEFAULT_ADDITIONAL_INSURED);
        assertThat(testRequestForQuotation.getBenificiaryName()).isEqualTo(DEFAULT_BENIFICIARY_NAME);
        assertThat(testRequestForQuotation.getPolicyType()).isEqualTo(DEFAULT_POLICY_TYPE);
        assertThat(testRequestForQuotation.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testRequestForQuotation.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testRequestForQuotation.getNumberOfMonth()).isEqualTo(DEFAULT_NUMBER_OF_MONTH);
        assertThat(testRequestForQuotation.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void createRequestForQuotationWithExistingId() throws Exception {
        // Create the RequestForQuotation with an existing ID
        requestForQuotation.setId(1L);

        int databaseSizeBeforeCreate = requestForQuotationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestForQuotationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestForQuotations() throws Exception {
        // Initialize the database
        requestForQuotationRepository.saveAndFlush(requestForQuotation);

        // Get all the requestForQuotationList
        restRequestForQuotationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestForQuotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].brokerId").value(hasItem(DEFAULT_BROKER_ID.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].insuredNames").value(hasItem(DEFAULT_INSURED_NAMES)))
            .andExpect(jsonPath("$.[*].additionalInsured").value(hasItem(DEFAULT_ADDITIONAL_INSURED)))
            .andExpect(jsonPath("$.[*].benificiaryName").value(hasItem(DEFAULT_BENIFICIARY_NAME)))
            .andExpect(jsonPath("$.[*].policyType").value(hasItem(DEFAULT_POLICY_TYPE)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].numberOfMonth").value(hasItem(DEFAULT_NUMBER_OF_MONTH)))
            .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO)));
    }

    @Test
    @Transactional
    void getRequestForQuotation() throws Exception {
        // Initialize the database
        requestForQuotationRepository.saveAndFlush(requestForQuotation);

        // Get the requestForQuotation
        restRequestForQuotationMockMvc
            .perform(get(ENTITY_API_URL_ID, requestForQuotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestForQuotation.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.brokerId").value(DEFAULT_BROKER_ID.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.insuredNames").value(DEFAULT_INSURED_NAMES))
            .andExpect(jsonPath("$.additionalInsured").value(DEFAULT_ADDITIONAL_INSURED))
            .andExpect(jsonPath("$.benificiaryName").value(DEFAULT_BENIFICIARY_NAME))
            .andExpect(jsonPath("$.policyType").value(DEFAULT_POLICY_TYPE))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.numberOfMonth").value(DEFAULT_NUMBER_OF_MONTH))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO));
    }

    @Test
    @Transactional
    void getNonExistingRequestForQuotation() throws Exception {
        // Get the requestForQuotation
        restRequestForQuotationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequestForQuotation() throws Exception {
        // Initialize the database
        requestForQuotationRepository.saveAndFlush(requestForQuotation);

        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();

        // Update the requestForQuotation
        RequestForQuotation updatedRequestForQuotation = requestForQuotationRepository.findById(requestForQuotation.getId()).get();
        // Disconnect from session so that the updates on updatedRequestForQuotation are not directly saved in db
        em.detach(updatedRequestForQuotation);
        updatedRequestForQuotation
            .number(UPDATED_NUMBER)
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .benificiaryName(UPDATED_BENIFICIARY_NAME)
            .policyType(UPDATED_POLICY_TYPE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .duration(UPDATED_DURATION)
            .numberOfMonth(UPDATED_NUMBER_OF_MONTH)
            .additionalInfo(UPDATED_ADDITIONAL_INFO);

        restRequestForQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequestForQuotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRequestForQuotation))
            )
            .andExpect(status().isOk());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
        RequestForQuotation testRequestForQuotation = requestForQuotationList.get(requestForQuotationList.size() - 1);
        assertThat(testRequestForQuotation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testRequestForQuotation.getBrokerId()).isEqualTo(UPDATED_BROKER_ID);
        assertThat(testRequestForQuotation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testRequestForQuotation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRequestForQuotation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequestForQuotation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRequestForQuotation.getInsuredNames()).isEqualTo(UPDATED_INSURED_NAMES);
        assertThat(testRequestForQuotation.getAdditionalInsured()).isEqualTo(UPDATED_ADDITIONAL_INSURED);
        assertThat(testRequestForQuotation.getBenificiaryName()).isEqualTo(UPDATED_BENIFICIARY_NAME);
        assertThat(testRequestForQuotation.getPolicyType()).isEqualTo(UPDATED_POLICY_TYPE);
        assertThat(testRequestForQuotation.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testRequestForQuotation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRequestForQuotation.getNumberOfMonth()).isEqualTo(UPDATED_NUMBER_OF_MONTH);
        assertThat(testRequestForQuotation.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void putNonExistingRequestForQuotation() throws Exception {
        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();
        requestForQuotation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestForQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestForQuotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestForQuotation() throws Exception {
        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();
        requestForQuotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestForQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestForQuotation() throws Exception {
        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();
        requestForQuotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestForQuotationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestForQuotationWithPatch() throws Exception {
        // Initialize the database
        requestForQuotationRepository.saveAndFlush(requestForQuotation);

        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();

        // Update the requestForQuotation using partial update
        RequestForQuotation partialUpdatedRequestForQuotation = new RequestForQuotation();
        partialUpdatedRequestForQuotation.setId(requestForQuotation.getId());

        partialUpdatedRequestForQuotation
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .policyType(UPDATED_POLICY_TYPE)
            .duration(UPDATED_DURATION);

        restRequestForQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestForQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestForQuotation))
            )
            .andExpect(status().isOk());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
        RequestForQuotation testRequestForQuotation = requestForQuotationList.get(requestForQuotationList.size() - 1);
        assertThat(testRequestForQuotation.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testRequestForQuotation.getBrokerId()).isEqualTo(UPDATED_BROKER_ID);
        assertThat(testRequestForQuotation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testRequestForQuotation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRequestForQuotation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequestForQuotation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRequestForQuotation.getInsuredNames()).isEqualTo(DEFAULT_INSURED_NAMES);
        assertThat(testRequestForQuotation.getAdditionalInsured()).isEqualTo(DEFAULT_ADDITIONAL_INSURED);
        assertThat(testRequestForQuotation.getBenificiaryName()).isEqualTo(DEFAULT_BENIFICIARY_NAME);
        assertThat(testRequestForQuotation.getPolicyType()).isEqualTo(UPDATED_POLICY_TYPE);
        assertThat(testRequestForQuotation.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testRequestForQuotation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRequestForQuotation.getNumberOfMonth()).isEqualTo(DEFAULT_NUMBER_OF_MONTH);
        assertThat(testRequestForQuotation.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void fullUpdateRequestForQuotationWithPatch() throws Exception {
        // Initialize the database
        requestForQuotationRepository.saveAndFlush(requestForQuotation);

        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();

        // Update the requestForQuotation using partial update
        RequestForQuotation partialUpdatedRequestForQuotation = new RequestForQuotation();
        partialUpdatedRequestForQuotation.setId(requestForQuotation.getId());

        partialUpdatedRequestForQuotation
            .number(UPDATED_NUMBER)
            .brokerId(UPDATED_BROKER_ID)
            .customerId(UPDATED_CUSTOMER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .insuredNames(UPDATED_INSURED_NAMES)
            .additionalInsured(UPDATED_ADDITIONAL_INSURED)
            .benificiaryName(UPDATED_BENIFICIARY_NAME)
            .policyType(UPDATED_POLICY_TYPE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .duration(UPDATED_DURATION)
            .numberOfMonth(UPDATED_NUMBER_OF_MONTH)
            .additionalInfo(UPDATED_ADDITIONAL_INFO);

        restRequestForQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestForQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestForQuotation))
            )
            .andExpect(status().isOk());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
        RequestForQuotation testRequestForQuotation = requestForQuotationList.get(requestForQuotationList.size() - 1);
        assertThat(testRequestForQuotation.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testRequestForQuotation.getBrokerId()).isEqualTo(UPDATED_BROKER_ID);
        assertThat(testRequestForQuotation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testRequestForQuotation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRequestForQuotation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequestForQuotation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRequestForQuotation.getInsuredNames()).isEqualTo(UPDATED_INSURED_NAMES);
        assertThat(testRequestForQuotation.getAdditionalInsured()).isEqualTo(UPDATED_ADDITIONAL_INSURED);
        assertThat(testRequestForQuotation.getBenificiaryName()).isEqualTo(UPDATED_BENIFICIARY_NAME);
        assertThat(testRequestForQuotation.getPolicyType()).isEqualTo(UPDATED_POLICY_TYPE);
        assertThat(testRequestForQuotation.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testRequestForQuotation.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRequestForQuotation.getNumberOfMonth()).isEqualTo(UPDATED_NUMBER_OF_MONTH);
        assertThat(testRequestForQuotation.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingRequestForQuotation() throws Exception {
        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();
        requestForQuotation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestForQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestForQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestForQuotation() throws Exception {
        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();
        requestForQuotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestForQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestForQuotation() throws Exception {
        int databaseSizeBeforeUpdate = requestForQuotationRepository.findAll().size();
        requestForQuotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestForQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestForQuotation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestForQuotation in the database
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestForQuotation() throws Exception {
        // Initialize the database
        requestForQuotationRepository.saveAndFlush(requestForQuotation);

        int databaseSizeBeforeDelete = requestForQuotationRepository.findAll().size();

        // Delete the requestForQuotation
        restRequestForQuotationMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestForQuotation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestForQuotation> requestForQuotationList = requestForQuotationRepository.findAll();
        assertThat(requestForQuotationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
