package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsExtensions;
import com.hypercell.axa.quotation.repository.RFQProductsExtensionsRepository;
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
 * Integration tests for the {@link RFQProductsExtensionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsExtensionsResourceIT {

    private static final String DEFAULT_EXTENSION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_RFQ_ID = 1L;
    private static final Long UPDATED_RFQ_ID = 2L;

    private static final Long DEFAULT_EXTENSION_ID = 1L;
    private static final Long UPDATED_EXTENSION_ID = 2L;

    private static final String ENTITY_API_URL = "/api/rfq-products-extensions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsExtensionsRepository rFQProductsExtensionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsExtensionsMockMvc;

    private RFQProductsExtensions rFQProductsExtensions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsExtensions createEntity(EntityManager em) {
        RFQProductsExtensions rFQProductsExtensions = new RFQProductsExtensions()
            .extensionName(DEFAULT_EXTENSION_NAME)
            .rfqId(DEFAULT_RFQ_ID)
            .extensionId(DEFAULT_EXTENSION_ID);
        return rFQProductsExtensions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsExtensions createUpdatedEntity(EntityManager em) {
        RFQProductsExtensions rFQProductsExtensions = new RFQProductsExtensions()
            .extensionName(UPDATED_EXTENSION_NAME)
            .rfqId(UPDATED_RFQ_ID)
            .extensionId(UPDATED_EXTENSION_ID);
        return rFQProductsExtensions;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsExtensions = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsExtensionsRepository.findAll().size();
        // Create the RFQProductsExtensions
        restRFQProductsExtensionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsExtensions testRFQProductsExtensions = rFQProductsExtensionsList.get(rFQProductsExtensionsList.size() - 1);
        assertThat(testRFQProductsExtensions.getExtensionName()).isEqualTo(DEFAULT_EXTENSION_NAME);
        assertThat(testRFQProductsExtensions.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsExtensions.getExtensionId()).isEqualTo(DEFAULT_EXTENSION_ID);
    }

    @Test
    @Transactional
    void createRFQProductsExtensionsWithExistingId() throws Exception {
        // Create the RFQProductsExtensions with an existing ID
        rFQProductsExtensions.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsExtensionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsExtensionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsExtensions() throws Exception {
        // Initialize the database
        rFQProductsExtensionsRepository.saveAndFlush(rFQProductsExtensions);

        // Get all the rFQProductsExtensionsList
        restRFQProductsExtensionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsExtensions.getId().intValue())))
            .andExpect(jsonPath("$.[*].extensionName").value(hasItem(DEFAULT_EXTENSION_NAME)))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID.intValue())))
            .andExpect(jsonPath("$.[*].extensionId").value(hasItem(DEFAULT_EXTENSION_ID.intValue())));
    }

    @Test
    @Transactional
    void getRFQProductsExtensions() throws Exception {
        // Initialize the database
        rFQProductsExtensionsRepository.saveAndFlush(rFQProductsExtensions);

        // Get the rFQProductsExtensions
        restRFQProductsExtensionsMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsExtensions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsExtensions.getId().intValue()))
            .andExpect(jsonPath("$.extensionName").value(DEFAULT_EXTENSION_NAME))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID.intValue()))
            .andExpect(jsonPath("$.extensionId").value(DEFAULT_EXTENSION_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsExtensions() throws Exception {
        // Get the rFQProductsExtensions
        restRFQProductsExtensionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsExtensions() throws Exception {
        // Initialize the database
        rFQProductsExtensionsRepository.saveAndFlush(rFQProductsExtensions);

        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();

        // Update the rFQProductsExtensions
        RFQProductsExtensions updatedRFQProductsExtensions = rFQProductsExtensionsRepository.findById(rFQProductsExtensions.getId()).get();
        // Disconnect from session so that the updates on updatedRFQProductsExtensions are not directly saved in db
        em.detach(updatedRFQProductsExtensions);
        updatedRFQProductsExtensions.extensionName(UPDATED_EXTENSION_NAME).rfqId(UPDATED_RFQ_ID).extensionId(UPDATED_EXTENSION_ID);

        restRFQProductsExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsExtensions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsExtensions))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsExtensions testRFQProductsExtensions = rFQProductsExtensionsList.get(rFQProductsExtensionsList.size() - 1);
        assertThat(testRFQProductsExtensions.getExtensionName()).isEqualTo(UPDATED_EXTENSION_NAME);
        assertThat(testRFQProductsExtensions.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsExtensions.getExtensionId()).isEqualTo(UPDATED_EXTENSION_ID);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();
        rFQProductsExtensions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsExtensions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();
        rFQProductsExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();
        rFQProductsExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsExtensionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsExtensionsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsExtensionsRepository.saveAndFlush(rFQProductsExtensions);

        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();

        // Update the rFQProductsExtensions using partial update
        RFQProductsExtensions partialUpdatedRFQProductsExtensions = new RFQProductsExtensions();
        partialUpdatedRFQProductsExtensions.setId(rFQProductsExtensions.getId());

        partialUpdatedRFQProductsExtensions.extensionName(UPDATED_EXTENSION_NAME);

        restRFQProductsExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsExtensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsExtensions))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsExtensions testRFQProductsExtensions = rFQProductsExtensionsList.get(rFQProductsExtensionsList.size() - 1);
        assertThat(testRFQProductsExtensions.getExtensionName()).isEqualTo(UPDATED_EXTENSION_NAME);
        assertThat(testRFQProductsExtensions.getRfqId()).isEqualTo(DEFAULT_RFQ_ID);
        assertThat(testRFQProductsExtensions.getExtensionId()).isEqualTo(DEFAULT_EXTENSION_ID);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsExtensionsWithPatch() throws Exception {
        // Initialize the database
        rFQProductsExtensionsRepository.saveAndFlush(rFQProductsExtensions);

        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();

        // Update the rFQProductsExtensions using partial update
        RFQProductsExtensions partialUpdatedRFQProductsExtensions = new RFQProductsExtensions();
        partialUpdatedRFQProductsExtensions.setId(rFQProductsExtensions.getId());

        partialUpdatedRFQProductsExtensions.extensionName(UPDATED_EXTENSION_NAME).rfqId(UPDATED_RFQ_ID).extensionId(UPDATED_EXTENSION_ID);

        restRFQProductsExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsExtensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsExtensions))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsExtensions testRFQProductsExtensions = rFQProductsExtensionsList.get(rFQProductsExtensionsList.size() - 1);
        assertThat(testRFQProductsExtensions.getExtensionName()).isEqualTo(UPDATED_EXTENSION_NAME);
        assertThat(testRFQProductsExtensions.getRfqId()).isEqualTo(UPDATED_RFQ_ID);
        assertThat(testRFQProductsExtensions.getExtensionId()).isEqualTo(UPDATED_EXTENSION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();
        rFQProductsExtensions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsExtensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();
        rFQProductsExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsExtensions() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsExtensionsRepository.findAll().size();
        rFQProductsExtensions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsExtensionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsExtensions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsExtensions in the database
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsExtensions() throws Exception {
        // Initialize the database
        rFQProductsExtensionsRepository.saveAndFlush(rFQProductsExtensions);

        int databaseSizeBeforeDelete = rFQProductsExtensionsRepository.findAll().size();

        // Delete the rFQProductsExtensions
        restRFQProductsExtensionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsExtensions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsExtensions> rFQProductsExtensionsList = rFQProductsExtensionsRepository.findAll();
        assertThat(rFQProductsExtensionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
