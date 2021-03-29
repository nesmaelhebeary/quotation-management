package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQClaims;
import com.hypercell.axa.quotation.repository.RFQClaimsRepository;
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
 * Integration tests for the {@link RFQClaimsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQClaimsResourceIT {

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final Long DEFAULT_CLAIM_YEAR = 1L;
    private static final Long UPDATED_CLAIM_YEAR = 2L;

    private static final Double DEFAULT_PAID_AMOUNT = 1D;
    private static final Double UPDATED_PAID_AMOUNT = 2D;

    private static final Double DEFAULT_OUTSTANDING_AMOUNT = 1D;
    private static final Double UPDATED_OUTSTANDING_AMOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/rfq-claims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQClaimsRepository rFQClaimsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQClaimsMockMvc;

    private RFQClaims rFQClaims;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQClaims createEntity(EntityManager em) {
        RFQClaims rFQClaims = new RFQClaims()
            .rfqId(DEFAULT_RFQ_ID)
            .claimYear(DEFAULT_CLAIM_YEAR)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .outstandingAmount(DEFAULT_OUTSTANDING_AMOUNT);
        return rFQClaims;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQClaims createUpdatedEntity(EntityManager em) {
        RFQClaims rFQClaims = new RFQClaims()
            .rfqId(UPDATED_RFQ_ID)
            .claimYear(UPDATED_CLAIM_YEAR)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT);
        return rFQClaims;
    }

    @BeforeEach
    public void initTest() {
        rFQClaims = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQClaims() throws Exception {
        int databaseSizeBeforeCreate = rFQClaimsRepository.findAll().size();
        // Create the RFQClaims
        restRFQClaimsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQClaims)))
            .andExpect(status().isCreated());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQClaims testRFQClaims = rFQClaimsList.get(rFQClaimsList.size() - 1);
        assertThat(testRFQClaims.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQClaims.getClaimYear()).isEqualTo(DEFAULT_CLAIM_YEAR);
        assertThat(testRFQClaims.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testRFQClaims.getOutstandingAmount()).isEqualTo(DEFAULT_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    void createRFQClaimsWithExistingId() throws Exception {
        // Create the RFQClaims with an existing ID
        rFQClaims.setId(1L);

        int databaseSizeBeforeCreate = rFQClaimsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQClaimsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQClaims)))
            .andExpect(status().isBadRequest());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQClaims() throws Exception {
        // Initialize the database
        rFQClaimsRepository.saveAndFlush(rFQClaims);

        // Get all the rFQClaimsList
        restRFQClaimsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQClaims.getId().intValue())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].claimYear").value(hasItem(DEFAULT_CLAIM_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].outstandingAmount").value(hasItem(DEFAULT_OUTSTANDING_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getRFQClaims() throws Exception {
        // Initialize the database
        rFQClaimsRepository.saveAndFlush(rFQClaims);

        // Get the rFQClaims
        restRFQClaimsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQClaims.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQClaims.getId().intValue()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.claimYear").value(DEFAULT_CLAIM_YEAR.intValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.outstandingAmount").value(DEFAULT_OUTSTANDING_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRFQClaims() throws Exception {
        // Get the rFQClaims
        restRFQClaimsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQClaims() throws Exception {
        // Initialize the database
        rFQClaimsRepository.saveAndFlush(rFQClaims);

        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();

        // Update the rFQClaims
        RFQClaims updatedRFQClaims = rFQClaimsRepository.findById(rFQClaims.getId()).get();
        // Disconnect from session so that the updates on updatedRFQClaims are not directly saved in db
        em.detach(updatedRFQClaims);
        updatedRFQClaims
            .rfqId(UPDATED_RFQ_ID)
            .claimYear(UPDATED_CLAIM_YEAR)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT);

        restRFQClaimsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQClaims.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQClaims))
            )
            .andExpect(status().isOk());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
        RFQClaims testRFQClaims = rFQClaimsList.get(rFQClaimsList.size() - 1);
        assertThat(testRFQClaims.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQClaims.getClaimYear()).isEqualTo(UPDATED_CLAIM_YEAR);
        assertThat(testRFQClaims.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testRFQClaims.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingRFQClaims() throws Exception {
        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();
        rFQClaims.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQClaimsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQClaims.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQClaims))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQClaims() throws Exception {
        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();
        rFQClaims.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQClaimsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQClaims))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQClaims() throws Exception {
        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();
        rFQClaims.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQClaimsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQClaims)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQClaimsWithPatch() throws Exception {
        // Initialize the database
        rFQClaimsRepository.saveAndFlush(rFQClaims);

        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();

        // Update the rFQClaims using partial update
        RFQClaims partialUpdatedRFQClaims = new RFQClaims();
        partialUpdatedRFQClaims.setId(rFQClaims.getId());

        partialUpdatedRFQClaims.rfqId(UPDATED_RFQ_ID);

        restRFQClaimsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQClaims.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQClaims))
            )
            .andExpect(status().isOk());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
        RFQClaims testRFQClaims = rFQClaimsList.get(rFQClaimsList.size() - 1);
        assertThat(testRFQClaims.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQClaims.getClaimYear()).isEqualTo(DEFAULT_CLAIM_YEAR);
        assertThat(testRFQClaims.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testRFQClaims.getOutstandingAmount()).isEqualTo(DEFAULT_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateRFQClaimsWithPatch() throws Exception {
        // Initialize the database
        rFQClaimsRepository.saveAndFlush(rFQClaims);

        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();

        // Update the rFQClaims using partial update
        RFQClaims partialUpdatedRFQClaims = new RFQClaims();
        partialUpdatedRFQClaims.setId(rFQClaims.getId());

        partialUpdatedRFQClaims
            .rfqId(UPDATED_RFQ_ID)
            .claimYear(UPDATED_CLAIM_YEAR)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT);

        restRFQClaimsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQClaims.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQClaims))
            )
            .andExpect(status().isOk());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
        RFQClaims testRFQClaims = rFQClaimsList.get(rFQClaimsList.size() - 1);
        assertThat(testRFQClaims.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQClaims.getClaimYear()).isEqualTo(UPDATED_CLAIM_YEAR);
        assertThat(testRFQClaims.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testRFQClaims.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingRFQClaims() throws Exception {
        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();
        rFQClaims.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQClaimsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQClaims.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQClaims))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQClaims() throws Exception {
        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();
        rFQClaims.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQClaimsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQClaims))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQClaims() throws Exception {
        int databaseSizeBeforeUpdate = rFQClaimsRepository.findAll().size();
        rFQClaims.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQClaimsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rFQClaims))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQClaims in the database
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQClaims() throws Exception {
        // Initialize the database
        rFQClaimsRepository.saveAndFlush(rFQClaims);

        int databaseSizeBeforeDelete = rFQClaimsRepository.findAll().size();

        // Delete the rFQClaims
        restRFQClaimsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQClaims.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQClaims> rFQClaimsList = rFQClaimsRepository.findAll();
        assertThat(rFQClaimsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
