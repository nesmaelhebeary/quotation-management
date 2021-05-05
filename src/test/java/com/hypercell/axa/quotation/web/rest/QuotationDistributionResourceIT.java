package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QuotationDistribution;
import com.hypercell.axa.quotation.domain.enumeration.DistributionType;
import com.hypercell.axa.quotation.repository.QuotationDistributionRepository;
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
 * Integration tests for the {@link QuotationDistributionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotationDistributionResourceIT {

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final DistributionType DEFAULT_DISTRIBUTION_TYPE = DistributionType.Retension;
    private static final DistributionType UPDATED_DISTRIBUTION_TYPE = DistributionType.QuotaShared;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final Double DEFAULT_PREMIUMS = 1D;
    private static final Double UPDATED_PREMIUMS = 2D;

    private static final Double DEFAULT_COMMISSION_PERCENTAGE = 1D;
    private static final Double UPDATED_COMMISSION_PERCENTAGE = 2D;

    private static final Double DEFAULT_TAX_PERCENTAGE = 1D;
    private static final Double UPDATED_TAX_PERCENTAGE = 2D;

    private static final Double DEFAULT_NET_PREMIUMS = 1D;
    private static final Double UPDATED_NET_PREMIUMS = 2D;

    private static final String ENTITY_API_URL = "/api/quotation-distributions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuotationDistributionRepository quotationDistributionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationDistributionMockMvc;

    private QuotationDistribution quotationDistribution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotationDistribution createEntity(EntityManager em) {
        QuotationDistribution quotationDistribution = new QuotationDistribution()
            .quotationId(DEFAULT_QUOTATION_ID)
            .distributionType(DEFAULT_DISTRIBUTION_TYPE)
            .value(DEFAULT_VALUE)
            .currency(DEFAULT_CURRENCY)
            .percentage(DEFAULT_PERCENTAGE)
            .rate(DEFAULT_RATE)
            .premiums(DEFAULT_PREMIUMS)
            .commissionPercentage(DEFAULT_COMMISSION_PERCENTAGE)
            .taxPercentage(DEFAULT_TAX_PERCENTAGE)
            .netPremiums(DEFAULT_NET_PREMIUMS);
        return quotationDistribution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotationDistribution createUpdatedEntity(EntityManager em) {
        QuotationDistribution quotationDistribution = new QuotationDistribution()
            .quotationId(UPDATED_QUOTATION_ID)
            .distributionType(UPDATED_DISTRIBUTION_TYPE)
            .value(UPDATED_VALUE)
            .currency(UPDATED_CURRENCY)
            .percentage(UPDATED_PERCENTAGE)
            .rate(UPDATED_RATE)
            .premiums(UPDATED_PREMIUMS)
            .commissionPercentage(UPDATED_COMMISSION_PERCENTAGE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .netPremiums(UPDATED_NET_PREMIUMS);
        return quotationDistribution;
    }

    @BeforeEach
    public void initTest() {
        quotationDistribution = createEntity(em);
    }

    @Test
    @Transactional
    void createQuotationDistribution() throws Exception {
        int databaseSizeBeforeCreate = quotationDistributionRepository.findAll().size();
        // Create the QuotationDistribution
        restQuotationDistributionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isCreated());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeCreate + 1);
        QuotationDistribution testQuotationDistribution = quotationDistributionList.get(quotationDistributionList.size() - 1);
        assertThat(testQuotationDistribution.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQuotationDistribution.getDistributionType()).isEqualTo(DEFAULT_DISTRIBUTION_TYPE);
        assertThat(testQuotationDistribution.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testQuotationDistribution.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testQuotationDistribution.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testQuotationDistribution.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testQuotationDistribution.getPremiums()).isEqualTo(DEFAULT_PREMIUMS);
        assertThat(testQuotationDistribution.getCommissionPercentage()).isEqualTo(DEFAULT_COMMISSION_PERCENTAGE);
        assertThat(testQuotationDistribution.getTaxPercentage()).isEqualTo(DEFAULT_TAX_PERCENTAGE);
        assertThat(testQuotationDistribution.getNetPremiums()).isEqualTo(DEFAULT_NET_PREMIUMS);
    }

    @Test
    @Transactional
    void createQuotationDistributionWithExistingId() throws Exception {
        // Create the QuotationDistribution with an existing ID
        quotationDistribution.setId(1L);

        int databaseSizeBeforeCreate = quotationDistributionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationDistributionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuotationDistributions() throws Exception {
        // Initialize the database
        quotationDistributionRepository.saveAndFlush(quotationDistribution);

        // Get all the quotationDistributionList
        restQuotationDistributionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationDistribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].distributionType").value(hasItem(DEFAULT_DISTRIBUTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].premiums").value(hasItem(DEFAULT_PREMIUMS.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionPercentage").value(hasItem(DEFAULT_COMMISSION_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxPercentage").value(hasItem(DEFAULT_TAX_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].netPremiums").value(hasItem(DEFAULT_NET_PREMIUMS.doubleValue())));
    }

    @Test
    @Transactional
    void getQuotationDistribution() throws Exception {
        // Initialize the database
        quotationDistributionRepository.saveAndFlush(quotationDistribution);

        // Get the quotationDistribution
        restQuotationDistributionMockMvc
            .perform(get(ENTITY_API_URL_ID, quotationDistribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotationDistribution.getId().intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.distributionType").value(DEFAULT_DISTRIBUTION_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.premiums").value(DEFAULT_PREMIUMS.doubleValue()))
            .andExpect(jsonPath("$.commissionPercentage").value(DEFAULT_COMMISSION_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.taxPercentage").value(DEFAULT_TAX_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.netPremiums").value(DEFAULT_NET_PREMIUMS.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuotationDistribution() throws Exception {
        // Get the quotationDistribution
        restQuotationDistributionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuotationDistribution() throws Exception {
        // Initialize the database
        quotationDistributionRepository.saveAndFlush(quotationDistribution);

        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();

        // Update the quotationDistribution
        QuotationDistribution updatedQuotationDistribution = quotationDistributionRepository.findById(quotationDistribution.getId()).get();
        // Disconnect from session so that the updates on updatedQuotationDistribution are not directly saved in db
        em.detach(updatedQuotationDistribution);
        updatedQuotationDistribution
            .quotationId(UPDATED_QUOTATION_ID)
            .distributionType(UPDATED_DISTRIBUTION_TYPE)
            .value(UPDATED_VALUE)
            .currency(UPDATED_CURRENCY)
            .percentage(UPDATED_PERCENTAGE)
            .rate(UPDATED_RATE)
            .premiums(UPDATED_PREMIUMS)
            .commissionPercentage(UPDATED_COMMISSION_PERCENTAGE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .netPremiums(UPDATED_NET_PREMIUMS);

        restQuotationDistributionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotationDistribution.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuotationDistribution))
            )
            .andExpect(status().isOk());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
        QuotationDistribution testQuotationDistribution = quotationDistributionList.get(quotationDistributionList.size() - 1);
        assertThat(testQuotationDistribution.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQuotationDistribution.getDistributionType()).isEqualTo(UPDATED_DISTRIBUTION_TYPE);
        assertThat(testQuotationDistribution.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testQuotationDistribution.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testQuotationDistribution.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testQuotationDistribution.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testQuotationDistribution.getPremiums()).isEqualTo(UPDATED_PREMIUMS);
        assertThat(testQuotationDistribution.getCommissionPercentage()).isEqualTo(UPDATED_COMMISSION_PERCENTAGE);
        assertThat(testQuotationDistribution.getTaxPercentage()).isEqualTo(UPDATED_TAX_PERCENTAGE);
        assertThat(testQuotationDistribution.getNetPremiums()).isEqualTo(UPDATED_NET_PREMIUMS);
    }

    @Test
    @Transactional
    void putNonExistingQuotationDistribution() throws Exception {
        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();
        quotationDistribution.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationDistributionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotationDistribution.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotationDistribution() throws Exception {
        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();
        quotationDistribution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationDistributionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotationDistribution() throws Exception {
        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();
        quotationDistribution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationDistributionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationDistributionWithPatch() throws Exception {
        // Initialize the database
        quotationDistributionRepository.saveAndFlush(quotationDistribution);

        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();

        // Update the quotationDistribution using partial update
        QuotationDistribution partialUpdatedQuotationDistribution = new QuotationDistribution();
        partialUpdatedQuotationDistribution.setId(quotationDistribution.getId());

        partialUpdatedQuotationDistribution
            .value(UPDATED_VALUE)
            .percentage(UPDATED_PERCENTAGE)
            .rate(UPDATED_RATE)
            .commissionPercentage(UPDATED_COMMISSION_PERCENTAGE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .netPremiums(UPDATED_NET_PREMIUMS);

        restQuotationDistributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationDistribution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationDistribution))
            )
            .andExpect(status().isOk());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
        QuotationDistribution testQuotationDistribution = quotationDistributionList.get(quotationDistributionList.size() - 1);
        assertThat(testQuotationDistribution.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQuotationDistribution.getDistributionType()).isEqualTo(DEFAULT_DISTRIBUTION_TYPE);
        assertThat(testQuotationDistribution.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testQuotationDistribution.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testQuotationDistribution.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testQuotationDistribution.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testQuotationDistribution.getPremiums()).isEqualTo(DEFAULT_PREMIUMS);
        assertThat(testQuotationDistribution.getCommissionPercentage()).isEqualTo(UPDATED_COMMISSION_PERCENTAGE);
        assertThat(testQuotationDistribution.getTaxPercentage()).isEqualTo(UPDATED_TAX_PERCENTAGE);
        assertThat(testQuotationDistribution.getNetPremiums()).isEqualTo(UPDATED_NET_PREMIUMS);
    }

    @Test
    @Transactional
    void fullUpdateQuotationDistributionWithPatch() throws Exception {
        // Initialize the database
        quotationDistributionRepository.saveAndFlush(quotationDistribution);

        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();

        // Update the quotationDistribution using partial update
        QuotationDistribution partialUpdatedQuotationDistribution = new QuotationDistribution();
        partialUpdatedQuotationDistribution.setId(quotationDistribution.getId());

        partialUpdatedQuotationDistribution
            .quotationId(UPDATED_QUOTATION_ID)
            .distributionType(UPDATED_DISTRIBUTION_TYPE)
            .value(UPDATED_VALUE)
            .currency(UPDATED_CURRENCY)
            .percentage(UPDATED_PERCENTAGE)
            .rate(UPDATED_RATE)
            .premiums(UPDATED_PREMIUMS)
            .commissionPercentage(UPDATED_COMMISSION_PERCENTAGE)
            .taxPercentage(UPDATED_TAX_PERCENTAGE)
            .netPremiums(UPDATED_NET_PREMIUMS);

        restQuotationDistributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationDistribution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationDistribution))
            )
            .andExpect(status().isOk());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
        QuotationDistribution testQuotationDistribution = quotationDistributionList.get(quotationDistributionList.size() - 1);
        assertThat(testQuotationDistribution.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQuotationDistribution.getDistributionType()).isEqualTo(UPDATED_DISTRIBUTION_TYPE);
        assertThat(testQuotationDistribution.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testQuotationDistribution.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testQuotationDistribution.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testQuotationDistribution.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testQuotationDistribution.getPremiums()).isEqualTo(UPDATED_PREMIUMS);
        assertThat(testQuotationDistribution.getCommissionPercentage()).isEqualTo(UPDATED_COMMISSION_PERCENTAGE);
        assertThat(testQuotationDistribution.getTaxPercentage()).isEqualTo(UPDATED_TAX_PERCENTAGE);
        assertThat(testQuotationDistribution.getNetPremiums()).isEqualTo(UPDATED_NET_PREMIUMS);
    }

    @Test
    @Transactional
    void patchNonExistingQuotationDistribution() throws Exception {
        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();
        quotationDistribution.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationDistributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotationDistribution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotationDistribution() throws Exception {
        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();
        quotationDistribution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationDistributionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotationDistribution() throws Exception {
        int databaseSizeBeforeUpdate = quotationDistributionRepository.findAll().size();
        quotationDistribution.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationDistributionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationDistribution))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuotationDistribution in the database
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotationDistribution() throws Exception {
        // Initialize the database
        quotationDistributionRepository.saveAndFlush(quotationDistribution);

        int databaseSizeBeforeDelete = quotationDistributionRepository.findAll().size();

        // Delete the quotationDistribution
        restQuotationDistributionMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotationDistribution.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuotationDistribution> quotationDistributionList = quotationDistributionRepository.findAll();
        assertThat(quotationDistributionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
