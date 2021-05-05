package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QuotationExtnesionDetails;
import com.hypercell.axa.quotation.repository.QuotationExtnesionDetailsRepository;
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
 * Integration tests for the {@link QuotationExtnesionDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotationExtnesionDetailsResourceIT {

    private static final Long DEFAULT_QUOTA_EXTENSION_ID = 1L;
    private static final Long UPDATED_QUOTA_EXTENSION_ID = 2L;

    private static final Long DEFAULT_PERCENTAGE_ITEM_ID = 1L;
    private static final Long UPDATED_PERCENTAGE_ITEM_ID = 2L;

    private static final Double DEFAULT_PERCENTAGE_ITEM_VALUE = 1D;
    private static final Double UPDATED_PERCENTAGE_ITEM_VALUE = 2D;

    private static final String ENTITY_API_URL = "/api/quotation-extnesion-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuotationExtnesionDetailsRepository quotationExtnesionDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationExtnesionDetailsMockMvc;

    private QuotationExtnesionDetails quotationExtnesionDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotationExtnesionDetails createEntity(EntityManager em) {
        QuotationExtnesionDetails quotationExtnesionDetails = new QuotationExtnesionDetails()
            .quotaExtensionId(DEFAULT_QUOTA_EXTENSION_ID)
            .percentageItemId(DEFAULT_PERCENTAGE_ITEM_ID)
            .percentageItemValue(DEFAULT_PERCENTAGE_ITEM_VALUE);
        return quotationExtnesionDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuotationExtnesionDetails createUpdatedEntity(EntityManager em) {
        QuotationExtnesionDetails quotationExtnesionDetails = new QuotationExtnesionDetails()
            .quotaExtensionId(UPDATED_QUOTA_EXTENSION_ID)
            .percentageItemId(UPDATED_PERCENTAGE_ITEM_ID)
            .percentageItemValue(UPDATED_PERCENTAGE_ITEM_VALUE);
        return quotationExtnesionDetails;
    }

    @BeforeEach
    public void initTest() {
        quotationExtnesionDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeCreate = quotationExtnesionDetailsRepository.findAll().size();
        // Create the QuotationExtnesionDetails
        restQuotationExtnesionDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isCreated());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        QuotationExtnesionDetails testQuotationExtnesionDetails = quotationExtnesionDetailsList.get(
            quotationExtnesionDetailsList.size() - 1
        );
        assertThat(testQuotationExtnesionDetails.getQuotaExtensionId()).isEqualTo(DEFAULT_QUOTA_EXTENSION_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemId()).isEqualTo(DEFAULT_PERCENTAGE_ITEM_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemValue()).isEqualTo(DEFAULT_PERCENTAGE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void createQuotationExtnesionDetailsWithExistingId() throws Exception {
        // Create the QuotationExtnesionDetails with an existing ID
        quotationExtnesionDetails.setId(1L);

        int databaseSizeBeforeCreate = quotationExtnesionDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationExtnesionDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuotationExtnesionDetails() throws Exception {
        // Initialize the database
        quotationExtnesionDetailsRepository.saveAndFlush(quotationExtnesionDetails);

        // Get all the quotationExtnesionDetailsList
        restQuotationExtnesionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationExtnesionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotaExtensionId").value(hasItem(DEFAULT_QUOTA_EXTENSION_ID.intValue())))
            .andExpect(jsonPath("$.[*].percentageItemId").value(hasItem(DEFAULT_PERCENTAGE_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].percentageItemValue").value(hasItem(DEFAULT_PERCENTAGE_ITEM_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    void getQuotationExtnesionDetails() throws Exception {
        // Initialize the database
        quotationExtnesionDetailsRepository.saveAndFlush(quotationExtnesionDetails);

        // Get the quotationExtnesionDetails
        restQuotationExtnesionDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, quotationExtnesionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotationExtnesionDetails.getId().intValue()))
            .andExpect(jsonPath("$.quotaExtensionId").value(DEFAULT_QUOTA_EXTENSION_ID.intValue()))
            .andExpect(jsonPath("$.percentageItemId").value(DEFAULT_PERCENTAGE_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.percentageItemValue").value(DEFAULT_PERCENTAGE_ITEM_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuotationExtnesionDetails() throws Exception {
        // Get the quotationExtnesionDetails
        restQuotationExtnesionDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuotationExtnesionDetails() throws Exception {
        // Initialize the database
        quotationExtnesionDetailsRepository.saveAndFlush(quotationExtnesionDetails);

        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();

        // Update the quotationExtnesionDetails
        QuotationExtnesionDetails updatedQuotationExtnesionDetails = quotationExtnesionDetailsRepository
            .findById(quotationExtnesionDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedQuotationExtnesionDetails are not directly saved in db
        em.detach(updatedQuotationExtnesionDetails);
        updatedQuotationExtnesionDetails
            .quotaExtensionId(UPDATED_QUOTA_EXTENSION_ID)
            .percentageItemId(UPDATED_PERCENTAGE_ITEM_ID)
            .percentageItemValue(UPDATED_PERCENTAGE_ITEM_VALUE);

        restQuotationExtnesionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotationExtnesionDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuotationExtnesionDetails))
            )
            .andExpect(status().isOk());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuotationExtnesionDetails testQuotationExtnesionDetails = quotationExtnesionDetailsList.get(
            quotationExtnesionDetailsList.size() - 1
        );
        assertThat(testQuotationExtnesionDetails.getQuotaExtensionId()).isEqualTo(UPDATED_QUOTA_EXTENSION_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemId()).isEqualTo(UPDATED_PERCENTAGE_ITEM_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemValue()).isEqualTo(UPDATED_PERCENTAGE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();
        quotationExtnesionDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationExtnesionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotationExtnesionDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();
        quotationExtnesionDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtnesionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();
        quotationExtnesionDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtnesionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationExtnesionDetailsWithPatch() throws Exception {
        // Initialize the database
        quotationExtnesionDetailsRepository.saveAndFlush(quotationExtnesionDetails);

        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();

        // Update the quotationExtnesionDetails using partial update
        QuotationExtnesionDetails partialUpdatedQuotationExtnesionDetails = new QuotationExtnesionDetails();
        partialUpdatedQuotationExtnesionDetails.setId(quotationExtnesionDetails.getId());

        restQuotationExtnesionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationExtnesionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationExtnesionDetails))
            )
            .andExpect(status().isOk());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuotationExtnesionDetails testQuotationExtnesionDetails = quotationExtnesionDetailsList.get(
            quotationExtnesionDetailsList.size() - 1
        );
        assertThat(testQuotationExtnesionDetails.getQuotaExtensionId()).isEqualTo(DEFAULT_QUOTA_EXTENSION_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemId()).isEqualTo(DEFAULT_PERCENTAGE_ITEM_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemValue()).isEqualTo(DEFAULT_PERCENTAGE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateQuotationExtnesionDetailsWithPatch() throws Exception {
        // Initialize the database
        quotationExtnesionDetailsRepository.saveAndFlush(quotationExtnesionDetails);

        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();

        // Update the quotationExtnesionDetails using partial update
        QuotationExtnesionDetails partialUpdatedQuotationExtnesionDetails = new QuotationExtnesionDetails();
        partialUpdatedQuotationExtnesionDetails.setId(quotationExtnesionDetails.getId());

        partialUpdatedQuotationExtnesionDetails
            .quotaExtensionId(UPDATED_QUOTA_EXTENSION_ID)
            .percentageItemId(UPDATED_PERCENTAGE_ITEM_ID)
            .percentageItemValue(UPDATED_PERCENTAGE_ITEM_VALUE);

        restQuotationExtnesionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationExtnesionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationExtnesionDetails))
            )
            .andExpect(status().isOk());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuotationExtnesionDetails testQuotationExtnesionDetails = quotationExtnesionDetailsList.get(
            quotationExtnesionDetailsList.size() - 1
        );
        assertThat(testQuotationExtnesionDetails.getQuotaExtensionId()).isEqualTo(UPDATED_QUOTA_EXTENSION_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemId()).isEqualTo(UPDATED_PERCENTAGE_ITEM_ID);
        assertThat(testQuotationExtnesionDetails.getPercentageItemValue()).isEqualTo(UPDATED_PERCENTAGE_ITEM_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();
        quotationExtnesionDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationExtnesionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotationExtnesionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();
        quotationExtnesionDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtnesionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotationExtnesionDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationExtnesionDetailsRepository.findAll().size();
        quotationExtnesionDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationExtnesionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationExtnesionDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuotationExtnesionDetails in the database
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotationExtnesionDetails() throws Exception {
        // Initialize the database
        quotationExtnesionDetailsRepository.saveAndFlush(quotationExtnesionDetails);

        int databaseSizeBeforeDelete = quotationExtnesionDetailsRepository.findAll().size();

        // Delete the quotationExtnesionDetails
        restQuotationExtnesionDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotationExtnesionDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuotationExtnesionDetails> quotationExtnesionDetailsList = quotationExtnesionDetailsRepository.findAll();
        assertThat(quotationExtnesionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
