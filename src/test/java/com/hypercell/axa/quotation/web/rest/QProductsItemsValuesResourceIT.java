package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProductsItemsValues;
import com.hypercell.axa.quotation.repository.QProductsItemsValuesRepository;
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
 * Integration tests for the {@link QProductsItemsValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsItemsValuesResourceIT {

    private static final Long DEFAULT_PRODUCT_ITEM_ID = 1L;
    private static final Long UPDATED_PRODUCT_ITEM_ID = 2L;

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/q-products-items-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsItemsValuesRepository qProductsItemsValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsItemsValuesMockMvc;

    private QProductsItemsValues qProductsItemsValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsItemsValues createEntity(EntityManager em) {
        QProductsItemsValues qProductsItemsValues = new QProductsItemsValues()
            .productItemId(DEFAULT_PRODUCT_ITEM_ID)
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE);
        return qProductsItemsValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProductsItemsValues createUpdatedEntity(EntityManager em) {
        QProductsItemsValues qProductsItemsValues = new QProductsItemsValues()
            .productItemId(UPDATED_PRODUCT_ITEM_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);
        return qProductsItemsValues;
    }

    @BeforeEach
    public void initTest() {
        qProductsItemsValues = createEntity(em);
    }

    @Test
    @Transactional
    void createQProductsItemsValues() throws Exception {
        int databaseSizeBeforeCreate = qProductsItemsValuesRepository.findAll().size();
        // Create the QProductsItemsValues
        restQProductsItemsValuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isCreated());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeCreate + 1);
        QProductsItemsValues testQProductsItemsValues = qProductsItemsValuesList.get(qProductsItemsValuesList.size() - 1);
        assertThat(testQProductsItemsValues.getProductItemId()).isEqualTo(DEFAULT_PRODUCT_ITEM_ID);
        assertThat(testQProductsItemsValues.getAttributeName()).isEqualTo(DEFAULT_ATTRIBUTE_NAME);
        assertThat(testQProductsItemsValues.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void createQProductsItemsValuesWithExistingId() throws Exception {
        // Create the QProductsItemsValues with an existing ID
        qProductsItemsValues.setId(1L);

        int databaseSizeBeforeCreate = qProductsItemsValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsItemsValuesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProductsItemsValues() throws Exception {
        // Initialize the database
        qProductsItemsValuesRepository.saveAndFlush(qProductsItemsValues);

        // Get all the qProductsItemsValuesList
        restQProductsItemsValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProductsItemsValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].productItemId").value(hasItem(DEFAULT_PRODUCT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)));
    }

    @Test
    @Transactional
    void getQProductsItemsValues() throws Exception {
        // Initialize the database
        qProductsItemsValuesRepository.saveAndFlush(qProductsItemsValues);

        // Get the qProductsItemsValues
        restQProductsItemsValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, qProductsItemsValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProductsItemsValues.getId().intValue()))
            .andExpect(jsonPath("$.productItemId").value(DEFAULT_PRODUCT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingQProductsItemsValues() throws Exception {
        // Get the qProductsItemsValues
        restQProductsItemsValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProductsItemsValues() throws Exception {
        // Initialize the database
        qProductsItemsValuesRepository.saveAndFlush(qProductsItemsValues);

        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();

        // Update the qProductsItemsValues
        QProductsItemsValues updatedQProductsItemsValues = qProductsItemsValuesRepository.findById(qProductsItemsValues.getId()).get();
        // Disconnect from session so that the updates on updatedQProductsItemsValues are not directly saved in db
        em.detach(updatedQProductsItemsValues);
        updatedQProductsItemsValues
            .productItemId(UPDATED_PRODUCT_ITEM_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProductsItemsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProductsItemsValues))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
        QProductsItemsValues testQProductsItemsValues = qProductsItemsValuesList.get(qProductsItemsValuesList.size() - 1);
        assertThat(testQProductsItemsValues.getProductItemId()).isEqualTo(UPDATED_PRODUCT_ITEM_ID);
        assertThat(testQProductsItemsValues.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testQProductsItemsValues.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();
        qProductsItemsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProductsItemsValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();
        qProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();
        qProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsValuesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsItemsValuesWithPatch() throws Exception {
        // Initialize the database
        qProductsItemsValuesRepository.saveAndFlush(qProductsItemsValues);

        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();

        // Update the qProductsItemsValues using partial update
        QProductsItemsValues partialUpdatedQProductsItemsValues = new QProductsItemsValues();
        partialUpdatedQProductsItemsValues.setId(qProductsItemsValues.getId());

        partialUpdatedQProductsItemsValues.productItemId(UPDATED_PRODUCT_ITEM_ID).attributeName(UPDATED_ATTRIBUTE_NAME);

        restQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsItemsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsItemsValues))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
        QProductsItemsValues testQProductsItemsValues = qProductsItemsValuesList.get(qProductsItemsValuesList.size() - 1);
        assertThat(testQProductsItemsValues.getProductItemId()).isEqualTo(UPDATED_PRODUCT_ITEM_ID);
        assertThat(testQProductsItemsValues.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testQProductsItemsValues.getAttributeValue()).isEqualTo(DEFAULT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateQProductsItemsValuesWithPatch() throws Exception {
        // Initialize the database
        qProductsItemsValuesRepository.saveAndFlush(qProductsItemsValues);

        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();

        // Update the qProductsItemsValues using partial update
        QProductsItemsValues partialUpdatedQProductsItemsValues = new QProductsItemsValues();
        partialUpdatedQProductsItemsValues.setId(qProductsItemsValues.getId());

        partialUpdatedQProductsItemsValues
            .productItemId(UPDATED_PRODUCT_ITEM_ID)
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE);

        restQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProductsItemsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProductsItemsValues))
            )
            .andExpect(status().isOk());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
        QProductsItemsValues testQProductsItemsValues = qProductsItemsValuesList.get(qProductsItemsValuesList.size() - 1);
        assertThat(testQProductsItemsValues.getProductItemId()).isEqualTo(UPDATED_PRODUCT_ITEM_ID);
        assertThat(testQProductsItemsValues.getAttributeName()).isEqualTo(UPDATED_ATTRIBUTE_NAME);
        assertThat(testQProductsItemsValues.getAttributeValue()).isEqualTo(UPDATED_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();
        qProductsItemsValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProductsItemsValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();
        qProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProductsItemsValues() throws Exception {
        int databaseSizeBeforeUpdate = qProductsItemsValuesRepository.findAll().size();
        qProductsItemsValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsItemsValuesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProductsItemsValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProductsItemsValues in the database
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProductsItemsValues() throws Exception {
        // Initialize the database
        qProductsItemsValuesRepository.saveAndFlush(qProductsItemsValues);

        int databaseSizeBeforeDelete = qProductsItemsValuesRepository.findAll().size();

        // Delete the qProductsItemsValues
        restQProductsItemsValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProductsItemsValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProductsItemsValues> qProductsItemsValuesList = qProductsItemsValuesRepository.findAll();
        assertThat(qProductsItemsValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
