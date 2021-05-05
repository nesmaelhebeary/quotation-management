package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.RFQProductsItemsValues;
import com.hypercell.axa.quotation.repository.RFQProductsItemsValuesRepository;
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
 * Integration tests for the {@link RFQProductsItemsValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RFQProductsItemsValuesResourceIT {

    private static final Long DEFAULT_PRODUCT_ITEM_ID = 1L;
    private static final Long UPDATED_PRODUCT_ITEM_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-products-items-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RFQProductsItemsValuesRepository rFQProductsItemsValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRFQProductsItemsValuesMockMvc;

    private RFQProductsItemsValues rFQProductsItemsValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItemsValues createEntity(EntityManager em) {
        RFQProductsItemsValues rFQProductsItemsValues = new RFQProductsItemsValues()
            .productItemId(DEFAULT_PRODUCT_ITEM_ID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return rFQProductsItemsValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RFQProductsItemsValues createUpdatedEntity(EntityManager em) {
        RFQProductsItemsValues rFQProductsItemsValues = new RFQProductsItemsValues()
            .productItemId(UPDATED_PRODUCT_ITEM_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return rFQProductsItemsValues;
    }

    @BeforeEach
    public void initTest() {
        rFQProductsItemsValues = createEntity(em);
    }

    @Test
    @Transactional
    void createRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeCreate = rFQProductsItemsValuesRepository.findAll().size();
        // Create the RFQProductsItemsValues
        restRFQProductsItemsValuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isCreated());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeCreate + 1);
        RFQProductsItemsValues testRFQProductsItemsValues = rFQProductsItemsValuesList.get(rFQProductsItemsValuesList.size() - 1);
        assertThat(testRFQProductsItemsValues.getProductItemId()).isEqualTo(DEFAULT_PRODUCT_ITEM_ID);
        assertThat(testRFQProductsItemsValues.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testRFQProductsItemsValues.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createRFQProductsItemsValuesWithExistingId() throws Exception {
        // Create the RFQProductsItemsValues with an existing ID
        rFQProductsItemsValues.setId(1L);

        int databaseSizeBeforeCreate = rFQProductsItemsValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRFQProductsItemsValuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRFQProductsItemsValues() throws Exception {
        // Initialize the database
        rFQProductsItemsValuesRepository.saveAndFlush(rFQProductsItemsValues);

        // Get all the rFQProductsItemsValuesList
        restRFQProductsItemsValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rFQProductsItemsValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].productItemId").value(hasItem(DEFAULT_PRODUCT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getRFQProductsItemsValues() throws Exception {
        // Initialize the database
        rFQProductsItemsValuesRepository.saveAndFlush(rFQProductsItemsValues);

        // Get the rFQProductsItemsValues
        restRFQProductsItemsValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, rFQProductsItemsValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rFQProductsItemsValues.getId().intValue()))
            .andExpect(jsonPath("$.productItemId").value(DEFAULT_PRODUCT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingRFQProductsItemsValues() throws Exception {
        // Get the rFQProductsItemsValues
        restRFQProductsItemsValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRFQProductsItemsValues() throws Exception {
        // Initialize the database
        rFQProductsItemsValuesRepository.saveAndFlush(rFQProductsItemsValues);

        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();

        // Update the rFQProductsItemsValues
        RFQProductsItemsValues updatedRFQProductsItemsValues = rFQProductsItemsValuesRepository
            .findById(rFQProductsItemsValues.getId())
            .get();
        // Disconnect from session so that the updates on updatedRFQProductsItemsValues are not directly saved in db
        em.detach(updatedRFQProductsItemsValues);
        updatedRFQProductsItemsValues
            .productItemId(UPDATED_PRODUCT_ITEM_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRFQProductsItemsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRFQProductsItemsValues))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsValues testRFQProductsItemsValues = rFQProductsItemsValuesList.get(rFQProductsItemsValuesList.size() - 1);
        assertThat(testRFQProductsItemsValues.getProductItemId()).isEqualTo(UPDATED_PRODUCT_ITEM_ID);
        assertThat(testRFQProductsItemsValues.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsItemsValues.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();
        rFQProductsItemsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rFQProductsItemsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();
        rFQProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();
        rFQProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRFQProductsItemsValuesWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsValuesRepository.saveAndFlush(rFQProductsItemsValues);

        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();

        // Update the rFQProductsItemsValues using partial update
        RFQProductsItemsValues partialUpdatedRFQProductsItemsValues = new RFQProductsItemsValues();
        partialUpdatedRFQProductsItemsValues.setId(rFQProductsItemsValues.getId());

        partialUpdatedRFQProductsItemsValues.attributeName(UPDATED_ATTRIBUTE_NAME);

        restRFQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItemsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItemsValues))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsValues testRFQProductsItemsValues = rFQProductsItemsValuesList.get(rFQProductsItemsValuesList.size() - 1);
        assertThat(testRFQProductsItemsValues.getProductItemId()).isEqualTo(DEFAULT_PRODUCT_ITEM_ID);
        assertThat(testRFQProductsItemsValues.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsItemsValues.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateRFQProductsItemsValuesWithPatch() throws Exception {
        // Initialize the database
        rFQProductsItemsValuesRepository.saveAndFlush(rFQProductsItemsValues);

        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();

        // Update the rFQProductsItemsValues using partial update
        RFQProductsItemsValues partialUpdatedRFQProductsItemsValues = new RFQProductsItemsValues();
        partialUpdatedRFQProductsItemsValues.setId(rFQProductsItemsValues.getId());

        partialUpdatedRFQProductsItemsValues
            .productItemId(UPDATED_PRODUCT_ITEM_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restRFQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRFQProductsItemsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRFQProductsItemsValues))
            )
            .andExpect(status().isOk());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
        RFQProductsItemsValues testRFQProductsItemsValues = rFQProductsItemsValuesList.get(rFQProductsItemsValuesList.size() - 1);
        assertThat(testRFQProductsItemsValues.getProductItemId()).isEqualTo(UPDATED_PRODUCT_ITEM_ID);
        assertThat(testRFQProductsItemsValues.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testRFQProductsItemsValues.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();
        rFQProductsItemsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRFQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rFQProductsItemsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();
        rFQProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRFQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = rFQProductsItemsValuesRepository.findAll().size();
        rFQProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRFQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rFQProductsItemsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RFQProductsItemsValues in the database
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRFQProductsItemsValues() throws Exception {
        // Initialize the database
        rFQProductsItemsValuesRepository.saveAndFlush(rFQProductsItemsValues);

        int databaseSizeBeforeDelete = rFQProductsItemsValuesRepository.findAll().size();

        // Delete the rFQProductsItemsValues
        restRFQProductsItemsValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, rFQProductsItemsValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RFQProductsItemsValues> rFQProductsItemsValuesList = rFQProductsItemsValuesRepository.findAll();
        assertThat(rFQProductsItemsValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
