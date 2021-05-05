package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsItemsDetails;
import com.hypercell.axa.quotation.repository.RFQProductsItemsDetailsRepository;
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
 * Integration tests for the {@link RFQProductsItemsDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsItemsDetailsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-items-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsItemsDetailsRepository rFQProductsItemsDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsItemsDetailsMockMvc;

    private RFQProductsItemsDetails rFQProductsItemsDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItemsDetails createEntity(EntityManager em) {
        RFQProductsItemsDetails rFQProductsItemsDetails = new RFQProductsItemsDetails()
            .productId(DEFAULT_PRODUCT_ID)
            .rfqId(DEFAULT_RFQ_ID)
            .itemName(DEFAULT_ITEM_NAME)
            .sumInsured(DEFAULT_SUM_INSURED)
            .currency(DEFAULT_CURRENCY);
        return rFQProductsItemsDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItemsDetails createUpdatedEntity(EntityManager em) {
        RFQProductsItemsDetails rFQProductsItemsDetails = new RFQProductsItemsDetails()
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .itemName(UPDATED_ITEM_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);
        return rFQProductsItemsDetails;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsItemsDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsItemsDetailsRepository.findAll().size();
        // Create the RFQProductsItemsDetails
        restRFQProductsItemsDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsItemsDetails testRFQProductsItemsDetails = rFQProductsItemsDetailsList.get(rFQProductsItemsDetailsList.size() - 1);
        assertThat(testRFQProductsItemsDetails.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProductsItemsDetails.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsItemsDetails.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testRFQProductsItemsDetails.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testRFQProductsItemsDetails.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createRFQProductsItemsDetailsWithExistingId() throws Exception {
        // Create the RFQProductsItemsDetails with an existing ID
        rFQProductsItemsDetails.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsItemsDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsItemsDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsItemsDetails() throws Exception {
        // Initialize the database
        rFQProductsItemsDetailsRepository.saveAndFlush(rFQProductsItemsDetails);

        // Get all the rFQProductsItemsDetailsList
        restRFQProductsItemsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsItemsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }

    @Test
    @Transactional
    void getRFQProductsItemsDetails() throws Exception {
        // Initialize the database
        rFQProductsItemsDetailsRepository.saveAndFlush(rFQProductsItemsDetails);

        // Get the rFQProductsItemsDetails
        restRFQProductsItemsDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsItemsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsItemsDetails.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsItemsDetails() throws Exception {
        // Get the rFQProductsItemsDetails
        restRFQProductsItemsDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsItemsDetails() throws Exception {
        // Initialize the database
        rFQProductsItemsDetailsRepository.saveAndFlush(rFQProductsItemsDetails);

        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();

        // Update the rFQProductsItemsDetails
        RFQProductsItemsDetails updatedRFQProductsItemsDetails = rFQProductsItemsDetailsRepository
            .findById(rFQProductsItemsDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedRFQProductsItemsDetails are not directly saved in db
        em.detach(updatedRFQProductsItemsDetails);
        updatedRFQProductsItemsDetails
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .itemName(UPDATED_ITEM_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restRFQProductsItemsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsItemsDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsItemsDetails))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsDetails testRFQProductsItemsDetails = rFQProductsItemsDetailsList.get(rFQProductsItemsDetailsList.size() - 1);
        assertThat(testRFQProductsItemsDetails.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItemsDetails.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItemsDetails.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testRFQProductsItemsDetails.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItemsDetails.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();
        rFQProductsItemsDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsItemsDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();
        rFQProductsItemsDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();
        rFQProductsItemsDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsItemsDetailsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsDetailsRepository.saveAndFlush(rFQProductsItemsDetails);

        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();

        // Update the rFQProductsItemsDetails using partial update
        RFQProductsItemsDetails partialUpdatedRFQProductsItemsDetails = new RFQProductsItemsDetails();
        partialUpdatedRFQProductsItemsDetails.setId(rFQProductsItemsDetails.getId());

        partialUpdatedRFQProductsItemsDetails.rfqId(UPDATED_RFQ_ID).sumInsured(UPDATED_SUM_INSURED);

        restRFQProductsItemsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItemsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItemsDetails))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsDetails testRFQProductsItemsDetails = rFQProductsItemsDetailsList.get(rFQProductsItemsDetailsList.size() - 1);
        assertThat(testRFQProductsItemsDetails.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProductsItemsDetails.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItemsDetails.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testRFQProductsItemsDetails.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItemsDetails.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsItemsDetailsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsDetailsRepository.saveAndFlush(rFQProductsItemsDetails);

        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();

        // Update the rFQProductsItemsDetails using partial update
        RFQProductsItemsDetails partialUpdatedRFQProductsItemsDetails = new RFQProductsItemsDetails();
        partialUpdatedRFQProductsItemsDetails.setId(rFQProductsItemsDetails.getId());

        partialUpdatedRFQProductsItemsDetails
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .itemName(UPDATED_ITEM_NAME)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restRFQProductsItemsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItemsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItemsDetails))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsDetails testRFQProductsItemsDetails = rFQProductsItemsDetailsList.get(rFQProductsItemsDetailsList.size() - 1);
        assertThat(testRFQProductsItemsDetails.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItemsDetails.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItemsDetails.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testRFQProductsItemsDetails.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItemsDetails.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();
        rFQProductsItemsDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsItemsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();
        rFQProductsItemsDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsItemsDetails() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsDetailsRepository.findAll().size();
        rFQProductsItemsDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItemsDetails in the database
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsItemsDetails() throws Exception {
        // Initialize the database
        rFQProductsItemsDetailsRepository.saveAndFlush(rFQProductsItemsDetails);

        int databaseSizeBeforeDelete = rFQProductsItemsDetailsRepository.findAll().size();

        // Delete the rFQProductsItemsDetails
        restRFQProductsItemsDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsItemsDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsItemsDetails> rFQProductsItemsDetailsList = rFQProductsItemsDetailsRepository.findAll();
        assertThat(rFQProductsItemsDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
