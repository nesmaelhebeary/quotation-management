package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsDeductibles;
import com.hypercell.axa.quotation.repository.RFQProductsDeductiblesRepository;
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
 * Integration tests for the {@link RFQProductsDeductiblesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsDeductiblesResourceIT {

    private static final String DEFAULT_DEDUCTIBLE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DEDUCTIBLE_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final String ENTITY_API_URL = "/api/rfq-products-deductibles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsDeductiblesRepository rFQProductsDeductiblesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsDeductiblesMockMvc;

    private RFQProductsDeductibles rFQProductsDeductibles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsDeductibles createEntity(EntityManager em) {
        RFQProductsDeductibles rFQProductsDeductibles = new RFQProductsDeductibles()
            .deductibleDescription(DEFAULT_DEDUCTIBLE_DESCRIPTION)
            .rfqId(DEFAULT_RFQ_ID);
        return rFQProductsDeductibles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsDeductibles createUpdatedEntity(EntityManager em) {
        RFQProductsDeductibles rFQProductsDeductibles = new RFQProductsDeductibles()
            .deductibleDescription(UPDATED_DEDUCTIBLE_DESCRIPTION)
            .rfqId(UPDATED_RFQ_ID);
        return rFQProductsDeductibles;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsDeductibles = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsDeductiblesRepository.findAll().size();
        // Create the RFQProductsDeductibles
        restRFQProductsDeductiblesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsDeductibles testRFQProductsDeductibles = rFQProductsDeductiblesList.get(rFQProductsDeductiblesList.size() - 1);
        assertThat(testRFQProductsDeductibles.getDeductibleDescription()).isEqualTo(DEFAULT_DEDUCTIBLE_DESCRIPTION);
        assertThat(testRFQProductsDeductibles.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
    }

    @Test
    @Transactional
    void createRFQProductsDeductiblesWithExistingId() throws Exception {
        // Create the RFQProductsDeductibles with an existing ID
        rFQProductsDeductibles.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsDeductiblesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsDeductiblesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsDeductibles() throws Exception {
        // Initialize the database
        rFQProductsDeductiblesRepository.saveAndFlush(rFQProductsDeductibles);

        // Get all the rFQProductsDeductiblesList
        restRFQProductsDeductiblesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsDeductibles.getId().intValue())))
            .andExpect(jsonPath("$.[*].deductibleDescription").value(hasItem(DEFAULT_DEDUCTIBLE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())));
    }

    @Test
    @Transactional
    void getRFQProductsDeductibles() throws Exception {
        // Initialize the database
        rFQProductsDeductiblesRepository.saveAndFlush(rFQProductsDeductibles);

        // Get the rFQProductsDeductibles
        restRFQProductsDeductiblesMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsDeductibles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsDeductibles.getId().intValue()))
            .andExpect(jsonPath("$.deductibleDescription").value(DEFAULT_DEDUCTIBLE_DESCRIPTION))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsDeductibles() throws Exception {
        // Get the rFQProductsDeductibles
        restRFQProductsDeductiblesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsDeductibles() throws Exception {
        // Initialize the database
        rFQProductsDeductiblesRepository.saveAndFlush(rFQProductsDeductibles);

        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();

        // Update the rFQProductsDeductibles
        RFQProductsDeductibles updatedRFQProductsDeductibles = rFQProductsDeductiblesRepository
            .findById(rFQProductsDeductibles.getId())
            .get();
        // Disconnect from session so that the updates on updatedRFQProductsDeductibles are not directly saved in db
        em.detach(updatedRFQProductsDeductibles);
        updatedRFQProductsDeductibles.deductibleDescription(UPDATED_DEDUCTIBLE_DESCRIPTION).rfqId(UPDATED_RFQ_ID);

        restRFQProductsDeductiblesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsDeductibles.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsDeductibles))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsDeductibles testRFQProductsDeductibles = rFQProductsDeductiblesList.get(rFQProductsDeductiblesList.size() - 1);
        assertThat(testRFQProductsDeductibles.getDeductibleDescription()).isEqualTo(UPDATED_DEDUCTIBLE_DESCRIPTION);
        assertThat(testRFQProductsDeductibles.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();
        rFQProductsDeductibles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsDeductiblesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsDeductibles.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();
        rFQProductsDeductibles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsDeductiblesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();
        rFQProductsDeductibles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsDeductiblesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsDeductiblesWithPatch() throws Exception {
        // Initialize the database
        rFQProductsDeductiblesRepository.saveAndFlush(rFQProductsDeductibles);

        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();

        // Update the rFQProductsDeductibles using partial update
        RFQProductsDeductibles partialUpdatedRFQProductsDeductibles = new RFQProductsDeductibles();
        partialUpdatedRFQProductsDeductibles.setId(rFQProductsDeductibles.getId());

        partialUpdatedRFQProductsDeductibles.deductibleDescription(UPDATED_DEDUCTIBLE_DESCRIPTION);

        restRFQProductsDeductiblesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsDeductibles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsDeductibles))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsDeductibles testRFQProductsDeductibles = rFQProductsDeductiblesList.get(rFQProductsDeductiblesList.size() - 1);
        assertThat(testRFQProductsDeductibles.getDeductibleDescription()).isEqualTo(UPDATED_DEDUCTIBLE_DESCRIPTION);
        assertThat(testRFQProductsDeductibles.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsDeductiblesWithPatch() throws Exception {
        // Initialize the database
        rFQProductsDeductiblesRepository.saveAndFlush(rFQProductsDeductibles);

        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();

        // Update the rFQProductsDeductibles using partial update
        RFQProductsDeductibles partialUpdatedRFQProductsDeductibles = new RFQProductsDeductibles();
        partialUpdatedRFQProductsDeductibles.setId(rFQProductsDeductibles.getId());

        partialUpdatedRFQProductsDeductibles.deductibleDescription(UPDATED_DEDUCTIBLE_DESCRIPTION).rfqId(UPDATED_RFQ_ID);

        restRFQProductsDeductiblesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsDeductibles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsDeductibles))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsDeductibles testRFQProductsDeductibles = rFQProductsDeductiblesList.get(rFQProductsDeductiblesList.size() - 1);
        assertThat(testRFQProductsDeductibles.getDeductibleDescription()).isEqualTo(UPDATED_DEDUCTIBLE_DESCRIPTION);
        assertThat(testRFQProductsDeductibles.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();
        rFQProductsDeductibles.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsDeductiblesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsDeductibles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();
        rFQProductsDeductibles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsDeductiblesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsDeductibles() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsDeductiblesRepository.findAll().size();
        rFQProductsDeductibles.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsDeductiblesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsDeductibles))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsDeductibles in the database
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsDeductibles() throws Exception {
        // Initialize the database
        rFQProductsDeductiblesRepository.saveAndFlush(rFQProductsDeductibles);

        int databaseSizeBeforeDelete = rFQProductsDeductiblesRepository.findAll().size();

        // Delete the rFQProductsDeductibles
        restRFQProductsDeductiblesMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsDeductibles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsDeductibles> rFQProductsDeductiblesList = rFQProductsDeductiblesRepository.findAll();
        assertThat(rFQProductsDeductiblesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
