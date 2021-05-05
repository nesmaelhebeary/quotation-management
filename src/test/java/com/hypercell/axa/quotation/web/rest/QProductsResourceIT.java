package com.hypercell.axa.quotation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hypercell.axa.quotation.IntegrationTest;
import com.hypercell.axa.quotation.domain.QProducts;
import com.hypercell.axa.quotation.domain.enumeration.CoverType;
import com.hypercell.axa.quotation.repository.QProductsRepository;
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
 * Integration tests for the {@link QProductsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QProductsResourceIT {

    private static final Long DEFAULT_QUOTATION_ID = 1L;
    private static final Long UPDATED_QUOTATION_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final CoverType DEFAULT_COVER_TYPE = CoverType.OpenCover;
    private static final CoverType UPDATED_COVER_TYPE = CoverType.Closed;

    private static final String ENTITY_API_URL = "/api/q-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QProductsRepository qProductsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQProductsMockMvc;

    private QProducts qProducts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProducts createEntity(EntityManager em) {
        QProducts qProducts = new QProducts().quotationId(DEFAULT_QUOTATION_ID).productId(DEFAULT_PRODUCT_ID).coverType(DEFAULT_COVER_TYPE);
        return qProducts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QProducts createUpdatedEntity(EntityManager em) {
        QProducts qProducts = new QProducts().quotationId(UPDATED_QUOTATION_ID).productId(UPDATED_PRODUCT_ID).coverType(UPDATED_COVER_TYPE);
        return qProducts;
    }

    @BeforeEach
    public void initTest() {
        qProducts = createEntity(em);
    }

    @Test
    @Transactional
    void createQProducts() throws Exception {
        int databaseSizeBeforeCreate = qProductsRepository.findAll().size();
        // Create the QProducts
        restQProductsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProducts)))
            .andExpect(status().isCreated());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeCreate + 1);
        QProducts testQProducts = qProductsList.get(qProductsList.size() - 1);
        assertThat(testQProducts.getQuotationId()).isEqualTo(DEFAULT_QUOTATION_ID);
        assertThat(testQProducts.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testQProducts.getCoverType()).isEqualTo(DEFAULT_COVER_TYPE);
    }

    @Test
    @Transactional
    void createQProductsWithExistingId() throws Exception {
        // Create the QProducts with an existing ID
        qProducts.setId(1L);

        int databaseSizeBeforeCreate = qProductsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQProductsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProducts)))
            .andExpect(status().isBadRequest());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQProducts() throws Exception {
        // Initialize the database
        qProductsRepository.saveAndFlush(qProducts);

        // Get all the qProductsList
        restQProductsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qProducts.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationId").value(hasItem(DEFAULT_QUOTATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].coverType").value(hasItem(DEFAULT_COVER_TYPE.toString())));
    }

    @Test
    @Transactional
    void getQProducts() throws Exception {
        // Initialize the database
        qProductsRepository.saveAndFlush(qProducts);

        // Get the qProducts
        restQProductsMockMvc
            .perform(get(ENTITY_API_URL_ID, qProducts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qProducts.getId().intValue()))
            .andExpect(jsonPath("$.quotationId").value(DEFAULT_QUOTATION_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.coverType").value(DEFAULT_COVER_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingQProducts() throws Exception {
        // Get the qProducts
        restQProductsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQProducts() throws Exception {
        // Initialize the database
        qProductsRepository.saveAndFlush(qProducts);

        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();

        // Update the qProducts
        QProducts updatedQProducts = qProductsRepository.findById(qProducts.getId()).get();
        // Disconnect from session so that the updates on updatedQProducts are not directly saved in db
        em.detach(updatedQProducts);
        updatedQProducts.quotationId(UPDATED_QUOTATION_ID).productId(UPDATED_PRODUCT_ID).coverType(UPDATED_COVER_TYPE);

        restQProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQProducts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQProducts))
            )
            .andExpect(status().isOk());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
        QProducts testQProducts = qProductsList.get(qProductsList.size() - 1);
        assertThat(testQProducts.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProducts.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProducts.getCoverType()).isEqualTo(UPDATED_COVER_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingQProducts() throws Exception {
        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();
        qProducts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, qProducts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQProducts() throws Exception {
        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();
        qProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(qProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQProducts() throws Exception {
        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();
        qProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(qProducts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQProductsWithPatch() throws Exception {
        // Initialize the database
        qProductsRepository.saveAndFlush(qProducts);

        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();

        // Update the qProducts using partial update
        QProducts partialUpdatedQProducts = new QProducts();
        partialUpdatedQProducts.setId(qProducts.getId());

        partialUpdatedQProducts.quotationId(UPDATED_QUOTATION_ID);

        restQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProducts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProducts))
            )
            .andExpect(status().isOk());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
        QProducts testQProducts = qProductsList.get(qProductsList.size() - 1);
        assertThat(testQProducts.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProducts.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testQProducts.getCoverType()).isEqualTo(DEFAULT_COVER_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateQProductsWithPatch() throws Exception {
        // Initialize the database
        qProductsRepository.saveAndFlush(qProducts);

        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();

        // Update the qProducts using partial update
        QProducts partialUpdatedQProducts = new QProducts();
        partialUpdatedQProducts.setId(qProducts.getId());

        partialUpdatedQProducts.quotationId(UPDATED_QUOTATION_ID).productId(UPDATED_PRODUCT_ID).coverType(UPDATED_COVER_TYPE);

        restQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQProducts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQProducts))
            )
            .andExpect(status().isOk());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
        QProducts testQProducts = qProductsList.get(qProductsList.size() - 1);
        assertThat(testQProducts.getQuotationId()).isEqualTo(UPDATED_QUOTATION_ID);
        assertThat(testQProducts.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testQProducts.getCoverType()).isEqualTo(UPDATED_COVER_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingQProducts() throws Exception {
        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();
        qProducts.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, qProducts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQProducts() throws Exception {
        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();
        qProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(qProducts))
            )
            .andExpect(status().isBadRequest());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQProducts() throws Exception {
        int databaseSizeBeforeUpdate = qProductsRepository.findAll().size();
        qProducts.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQProductsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(qProducts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QProducts in the database
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQProducts() throws Exception {
        // Initialize the database
        qProductsRepository.saveAndFlush(qProducts);

        int databaseSizeBeforeDelete = qProductsRepository.findAll().size();

        // Delete the qProducts
        restQProductsMockMvc
            .perform(delete(ENTITY_API_URL_ID, qProducts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QProducts> qProductsList = qProductsRepository.findAll();
        assertThat(qProductsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
