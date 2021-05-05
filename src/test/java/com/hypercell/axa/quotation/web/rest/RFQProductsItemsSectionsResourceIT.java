package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsItemsSections;
import com.hypercell.axa.quotation.repository.RFQProductsItemsSectionsRepository;
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
 * Integration tests for the {@link RFQProductsItemsSectionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsItemsSectionsResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final Long DEFAULT_SECTION_ID = 1L;
    private static final Long UPDATED_SECTION_ID = 2L;

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-items-sections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsItemsSectionsRepository rFQProductsItemsSectionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsItemsSectionsMockMvc;

    private RFQProductsItemsSections rFQProductsItemsSections;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItemsSections createEntity(EntityManager em) {
        RFQProductsItemsSections rFQProductsItemsSections = new RFQProductsItemsSections()
            .productId(DEFAULT_PRODUCT_ID)
            .rfqId(DEFAULT_RFQ_ID)
            .sectionId(DEFAULT_SECTION_ID)
            .sumInsured(DEFAULT_SUM_INSURED)
            .currency(DEFAULT_CURRENCY);
        return rFQProductsItemsSections;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItemsSections createUpdatedEntity(EntityManager em) {
        RFQProductsItemsSections rFQProductsItemsSections = new RFQProductsItemsSections()
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .sectionId(UPDATED_SECTION_ID)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);
        return rFQProductsItemsSections;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsItemsSections = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsItemsSectionsRepository.findAll().size();
        // Create the RFQProductsItemsSections
        restRFQProductsItemsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsItemsSections testRFQProductsItemsSections = rFQProductsItemsSectionsList.get(rFQProductsItemsSectionsList.size() - 1);
        assertThat(testRFQProductsItemsSections.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProductsItemsSections.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsItemsSections.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testRFQProductsItemsSections.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
        assertThat(testRFQProductsItemsSections.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createRFQProductsItemsSectionsWithExistingId() throws Exception {
        // Create the RFQProductsItemsSections with an existing ID
        rFQProductsItemsSections.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsItemsSectionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsItemsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsItemsSections() throws Exception {
        // Initialize the database
        rFQProductsItemsSectionsRepository.saveAndFlush(rFQProductsItemsSections);

        // Get all the rFQProductsItemsSectionsList
        restRFQProductsItemsSectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsItemsSections.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)));
    }

    @Test
    @Transactional
    void getRFQProductsItemsSections() throws Exception {
        // Initialize the database
        rFQProductsItemsSectionsRepository.saveAndFlush(rFQProductsItemsSections);

        // Get the rFQProductsItemsSections
        restRFQProductsItemsSectionsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsItemsSections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsItemsSections.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.sectionId").value(DEFAULT_SECTION_ID.intValue()))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsItemsSections() throws Exception {
        // Get the rFQProductsItemsSections
        restRFQProductsItemsSectionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsItemsSections() throws Exception {
        // Initialize the database
        rFQProductsItemsSectionsRepository.saveAndFlush(rFQProductsItemsSections);

        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();

        // Update the rFQProductsItemsSections
        RFQProductsItemsSections updatedRFQProductsItemsSections = rFQProductsItemsSectionsRepository
            .findById(rFQProductsItemsSections.getId())
            .get();
        // Disconnect from session so that the updates on updatedRFQProductsItemsSections are not directly saved in db
        em.detach(updatedRFQProductsItemsSections);
        updatedRFQProductsItemsSections
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .sectionId(UPDATED_SECTION_ID)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restRFQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsItemsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsItemsSections))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsSections testRFQProductsItemsSections = rFQProductsItemsSectionsList.get(rFQProductsItemsSectionsList.size() - 1);
        assertThat(testRFQProductsItemsSections.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItemsSections.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItemsSections.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testRFQProductsItemsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItemsSections.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();
        rFQProductsItemsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsItemsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();
        rFQProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();
        rFQProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsItemsSectionsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsSectionsRepository.saveAndFlush(rFQProductsItemsSections);

        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();

        // Update the rFQProductsItemsSections using partial update
        RFQProductsItemsSections partialUpdatedRFQProductsItemsSections = new RFQProductsItemsSections();
        partialUpdatedRFQProductsItemsSections.setId(rFQProductsItemsSections.getId());

        partialUpdatedRFQProductsItemsSections.sumInsured(UPDATED_SUM_INSURED);

        restRFQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItemsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItemsSections))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsSections testRFQProductsItemsSections = rFQProductsItemsSectionsList.get(rFQProductsItemsSectionsList.size() - 1);
        assertThat(testRFQProductsItemsSections.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testRFQProductsItemsSections.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsItemsSections.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testRFQProductsItemsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItemsSections.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsItemsSectionsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsSectionsRepository.saveAndFlush(rFQProductsItemsSections);

        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();

        // Update the rFQProductsItemsSections using partial update
        RFQProductsItemsSections partialUpdatedRFQProductsItemsSections = new RFQProductsItemsSections();
        partialUpdatedRFQProductsItemsSections.setId(rFQProductsItemsSections.getId());

        partialUpdatedRFQProductsItemsSections
            .productId(UPDATED_PRODUCT_ID)
            .rfqId(UPDATED_RFQ_ID)
            .sectionId(UPDATED_SECTION_ID)
            .sumInsured(UPDATED_SUM_INSURED)
            .currency(UPDATED_CURRENCY);

        restRFQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItemsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItemsSections))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsSections testRFQProductsItemsSections = rFQProductsItemsSectionsList.get(rFQProductsItemsSectionsList.size() - 1);
        assertThat(testRFQProductsItemsSections.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testRFQProductsItemsSections.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsItemsSections.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testRFQProductsItemsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
        assertThat(testRFQProductsItemsSections.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();
        rFQProductsItemsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsItemsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();
        rFQProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsItemsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsSectionsRepository.findAll().size();
        rFQProductsItemsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItemsSections in the database
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsItemsSections() throws Exception {
        // Initialize the database
        rFQProductsItemsSectionsRepository.saveAndFlush(rFQProductsItemsSections);

        int databaseSizeBeforeDelete = rFQProductsItemsSectionsRepository.findAll().size();

        // Delete the rFQProductsItemsSections
        restRFQProductsItemsSectionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsItemsSections.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsItemsSections> rFQProductsItemsSectionsList = rFQProductsItemsSectionsRepository.findAll();
        assertThat(rFQProductsItemsSectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
