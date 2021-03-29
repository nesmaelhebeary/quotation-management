package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsSections;
import com.hypercell.axa.quotation.repository.RFQProductsSectionsRepository;
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
 * Integration tests for the {@link RFQProductsSectionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsSectionsResourceIT {

    private static final String DEFAULT_SECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_SUM_INSURED = 1D;
    private static final Double UPDATED_SUM_INSURED = 2D;

    private static final String ENTITY_API_URL = "/api/rfq-products-sections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsSectionsRepository rFQProductsSectionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsSectionsMockMvc;

    private RFQProductsSections rFQProductsSections;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsSections createEntity(EntityManager em) {
        RFQProductsSections rFQProductsSections = new RFQProductsSections()
            .sectionName(DEFAULT_SECTION_NAME)
            .sumInsured(DEFAULT_SUM_INSURED);
        return rFQProductsSections;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsSections createUpdatedEntity(EntityManager em) {
        RFQProductsSections rFQProductsSections = new RFQProductsSections()
            .sectionName(UPDATED_SECTION_NAME)
            .sumInsured(UPDATED_SUM_INSURED);
        return rFQProductsSections;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsSections = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsSections() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsSectionsRepository.findAll().size();
        // Create the RFQProductsSections
        restRFQProductsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsSections testRFQProductsSections = rFQProductsSectionsList.get(rFQProductsSectionsList.size() - 1);
        assertThat(testRFQProductsSections.getSectionName()).isEqualTo(DEFAULT_SECTION_NAME);
        assertThat(testRFQProductsSections.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
    }

    @Test
    @Transactional
    void createRFQProductsSectionsWithExistingId() throws Exception {
        // Create the RFQProductsSections with an existing ID
        rFQProductsSections.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsSectionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsSectionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsSections() throws Exception {
        // Initialize the database
        rFQProductsSectionsRepository.saveAndFlush(rFQProductsSections);

        // Get all the rFQProductsSectionsList
        restRFQProductsSectionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsSections.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionName").value(hasItem(DEFAULT_SECTION_NAME)))
            .andExpect(jsonPath("$.[*].sumInsured").value(hasItem(DEFAULT_SUM_INSURED.doubleValue())));
    }

    @Test
    @Transactional
    void getRFQProductsSections() throws Exception {
        // Initialize the database
        rFQProductsSectionsRepository.saveAndFlush(rFQProductsSections);

        // Get the rFQProductsSections
        restRFQProductsSectionsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsSections.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsSections.getId().intValue()))
            .andExpect(jsonPath("$.sectionName").value(DEFAULT_SECTION_NAME))
            .andExpect(jsonPath("$.sumInsured").value(DEFAULT_SUM_INSURED.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsSections() throws Exception {
        // Get the rFQProductsSections
        restRFQProductsSectionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsSections() throws Exception {
        // Initialize the database
        rFQProductsSectionsRepository.saveAndFlush(rFQProductsSections);

        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();

        // Update the rFQProductsSections
        RFQProductsSections updatedRFQProductsSections = rFQProductsSectionsRepository.findById(rFQProductsSections.getId()).get();
        // Disconnect from session so that the updates on updatedRFQProductsSections are not directly saved in db
        em.detach(updatedRFQProductsSections);
        updatedRFQProductsSections.sectionName(UPDATED_SECTION_NAME).sumInsured(UPDATED_SUM_INSURED);

        restRFQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsSections))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsSections testRFQProductsSections = rFQProductsSectionsList.get(rFQProductsSectionsList.size() - 1);
        assertThat(testRFQProductsSections.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testRFQProductsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();
        rFQProductsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsSections.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();
        rFQProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();
        rFQProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsSectionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsSectionsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsSectionsRepository.saveAndFlush(rFQProductsSections);

        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();

        // Update the rFQProductsSections using partial update
        RFQProductsSections partialUpdatedRFQProductsSections = new RFQProductsSections();
        partialUpdatedRFQProductsSections.setId(rFQProductsSections.getId());

        partialUpdatedRFQProductsSections.sectionName(UPDATED_SECTION_NAME);

        restRFQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsSections))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsSections testRFQProductsSections = rFQProductsSectionsList.get(rFQProductsSectionsList.size() - 1);
        assertThat(testRFQProductsSections.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testRFQProductsSections.getSumInsured()).isEqualTo(DEFAULT_SUM_INSURED);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsSectionsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsSectionsRepository.saveAndFlush(rFQProductsSections);

        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();

        // Update the rFQProductsSections using partial update
        RFQProductsSections partialUpdatedRFQProductsSections = new RFQProductsSections();
        partialUpdatedRFQProductsSections.setId(rFQProductsSections.getId());

        partialUpdatedRFQProductsSections.sectionName(UPDATED_SECTION_NAME).sumInsured(UPDATED_SUM_INSURED);

        restRFQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsSections))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsSections testRFQProductsSections = rFQProductsSectionsList.get(rFQProductsSectionsList.size() - 1);
        assertThat(testRFQProductsSections.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testRFQProductsSections.getSumInsured()).isEqualTo(UPDATED_SUM_INSURED);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();
        rFQProductsSections.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsSections.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();
        rFQProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsSections() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsSectionsRepository.findAll().size();
        rFQProductsSections.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsSectionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsSections))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsSections in the database
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsSections() throws Exception {
        // Initialize the database
        rFQProductsSectionsRepository.saveAndFlush(rFQProductsSections);

        int databaseSizeBeforeDelete = rFQProductsSectionsRepository.findAll().size();

        // Delete the rFQProductsSections
        restRFQProductsSectionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsSections.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsSections> rFQProductsSectionsList = rFQProductsSectionsRepository.findAll();
        assertThat(rFQProductsSectionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
